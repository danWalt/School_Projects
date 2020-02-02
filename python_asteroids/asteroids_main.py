from screen import Screen
import sys
from ship import Ship
from asteroid import Asteroid
from torpedo import Torpedo
import math
from random import randint

X_AXIS = 0
Y_AXIS = 1

DEFAULT_ASTEROIDS_NUM = 5
ASTEROIDS_MIN_SPEED = 1
ASTEROIDS_MAX_SPEED = 5
TURN_RIGHT = -7
TURN_LEFT = 7
DELTA_Y = Screen.SCREEN_MAX_Y - Screen.SCREEN_MIN_Y
DELTA_X = Screen.SCREEN_MAX_X - Screen.SCREEN_MIN_X

ACCELERATION_FACTOR = 2
MAX_POSSIBLE_TORPEDO = 15
END_OF_TORPEDO_LIFE = 0

STARTING_POINTS = 0
BIG_ASTEROID_POINTS = 20
MEDIUM_ASTEROID_POINTS = 50
SMALL_ASTEROID_POINTS = 100

BIG_ASTEROID_SIZE = 3
MEDIUM_ASTEROID_SIZE = 2
SMALL_ASTEROID_SIZE = 1

OPPOSITE_DIRECTION_ASTEROID_MOVEMENT = -1

SHIP_END_GAME_LIFE = 0
ALL_ASTEROIDS_DESTROYED = 0
ASTEROID_DESTROYED = 0

HIT_TITLE = "Oh No! a Collision!"
HIT_MESSAGE = "Your ship hit an asteroid!"
WIN_TITLE = "Congratulation! you won!"
WIN_MESSAGE = "You have managed to destroy all of the asteroids without " \
              "dieing!"
LOSE_TITLE = "You lost!"
LOSE_MESSAGE = "You have been hit by the asteroids too many times."
QUIT_GAME_TITLE = "You quit the game"
QUIT_GAME_MESSAGE = "You decided to stop the game early and quit. Your " \
                    "current points count is "


class GameRunner:
    """This class defines a ship qualities.It contains the properties: speed
       and location once for xis and once for yis, and direction in degrees."""

    def __init__(self, asteroids_amnt):
        """The constructor for asteroids game object."""
        self._screen = Screen()
        self.screen_max_x = Screen.SCREEN_MAX_X
        self.screen_max_y = Screen.SCREEN_MAX_Y
        self.screen_min_x = Screen.SCREEN_MIN_X
        self.screen_min_y = Screen.SCREEN_MIN_Y
        self.__ship = Ship()
        self.__amount_of_asteroid = asteroids_amnt
        self.__asteroids_list = self.add_asteroids_to_game()
        self.__torpedo_list = list()
        self.__points = STARTING_POINTS

    def _ship_action(self):
        """This function draws the ship object."""
        self._move_object(self.__ship)
        x_ship, y_ship = self.__ship.get_location()
        self._screen.draw_ship(x_ship, y_ship, self.__ship.get_direction())

    def _asteroid_action(self):
        """This function activates all relevant functions for object
        asteroid."""
        for each_asteroid in self.__asteroids_list:
            self._move_object(each_asteroid)
            x_asteroid, y_asteroid = each_asteroid.get_location()
            self._screen.draw_asteroid(each_asteroid, x_asteroid,
                                       y_asteroid)
            self.ship_hits_asteroid()

    def _torpedo_action(self):
        """This function activates all relevant functions for object
        torpedo."""
        for each_torpedo in self.__torpedo_list:
            self._move_object(each_torpedo)
            x_torpedo, y_torpedo = each_torpedo.get_location()
            self._screen.draw_torpedo(each_torpedo, x_torpedo, y_torpedo,
                                      each_torpedo.get_direction())
            self.torpedo_hit_asteroid(each_torpedo)
            each_torpedo.reduce_life_time()
            if each_torpedo.get_life_time() == END_OF_TORPEDO_LIFE:
                self.delete_torpedo(each_torpedo)

    def asteroid_position(self):
        """this function determines a starting position for each asteroid.
        it has to make sure there is no collision with the ship object"""
        x_position = randint(Screen.SCREEN_MIN_X, Screen.SCREEN_MAX_X)
        while x_position == self.__ship.get_location()[X_AXIS]:
            x_position = randint(Screen.SCREEN_MIN_X, Screen.SCREEN_MAX_X)
        y_position = randint(Screen.SCREEN_MIN_Y, Screen.SCREEN_MAX_Y)
        while y_position == self.__ship.get_location()[Y_AXIS]:
            y_position = randint(Screen.SCREEN_MIN_Y, Screen.SCREEN_MAX_Y)
        return x_position, y_position

    def add_asteroids_to_game(self):
        """This function adds asteroids to the game."""
        asteroids_list = list()

        for aster in range(self.__amount_of_asteroid):
            aster_location = self.asteroid_position()
            aster_speed = (randint(ASTEROIDS_MIN_SPEED, ASTEROIDS_MAX_SPEED),
                           randint(ASTEROIDS_MIN_SPEED, ASTEROIDS_MAX_SPEED))
            asteroids_list.append(Asteroid(aster_location, aster_speed))
        for asteroid in asteroids_list:
            self._screen.register_asteroid(asteroid, asteroid.get_size())
        return asteroids_list

    def add_torpedo_to_game(self):
        """This function adds torpedoes to the game."""
        if len(self.__torpedo_list) < MAX_POSSIBLE_TORPEDO:
            ship_direction = self.__ship.get_direction()
            ship_location = self.__ship.get_location()
            torpedo_speed = self.calculate_torpedo_speed()
            torpedo = (Torpedo(ship_location, ship_direction, torpedo_speed))
            self._screen.register_torpedo(torpedo)
            self.__torpedo_list.append(torpedo)

    def calculate_torpedo_speed(self):
        """This function calculates the torpedo's speed"""
        x_speed = self.__ship.get_speed()[X_AXIS] + \
                  ACCELERATION_FACTOR * math.cos(math.radians(
                      self.__ship.get_direction()))
        y_speed = self.__ship.get_speed()[Y_AXIS] + \
                  ACCELERATION_FACTOR * math.sin(math.radians(
                      self.__ship.get_direction()))

        return x_speed, y_speed

    def ship_hits_asteroid(self):
        """This function is activated when the ship and an asteroid hit each
                other. It pops an alert , removes a ship life and removes the
                hitting asteroid."""
        for each_asteroid in self.__asteroids_list:
            if each_asteroid.has_intersection(self.__ship):
                self._screen.show_message(HIT_TITLE, HIT_MESSAGE)
                self.__ship.reduce_health()
                self._screen.remove_life()
                self._screen.unregister_asteroid(each_asteroid)
                self.__asteroids_list.remove(each_asteroid)

    def torpedo_hit_asteroid(self, torpedo):
        """This function is activated when a torpedo and an astroid hit
                each other.The result: point are added, the hitting asteroid split
                and the original one is deleted, and the torpedo is deleted at the
                end."""
        for each_asteroid in self.__asteroids_list:
            if each_asteroid.has_intersection(torpedo):
                self.add_points(each_asteroid)
                self.delete_torpedo(torpedo)
                if each_asteroid.get_size() == SMALL_ASTEROID_SIZE:
                    self.delete_asteroid(each_asteroid)
                else:
                    self.delete_asteroid(each_asteroid)
                    self.split_asteroid(each_asteroid,
                                        each_asteroid.get_speed(),
                                        torpedo.get_speed(),
                                        each_asteroid.get_location())

    def add_points(self, asteroid):
        """This function adds points when a torpedo hits an asteroid."""
        asteroid_size = asteroid.get_size()
        if asteroid_size == BIG_ASTEROID_SIZE:
            self.__points += BIG_ASTEROID_POINTS
        elif asteroid_size == MEDIUM_ASTEROID_SIZE:
            self.__points += MEDIUM_ASTEROID_POINTS
        else:
            self.__points += SMALL_ASTEROID_POINTS
        self._screen.set_score(self.__points)

    def split_asteroid(self, asteroid, asteroid_speed, torpedo_speed,
                       asteroid_location):
        """This function splits an asteroid when a torpedo hits it."""
        smaller_asteroid_speed_x = self.new_asteroid_x_speed(asteroid_speed,
                                                             torpedo_speed)
        smaller_asteroid_speed_y = self.new_asteroid_y_speed(asteroid_speed,
                                                             torpedo_speed)

        if asteroid.get_size() == BIG_ASTEROID_SIZE:
            new_asteroid_size = MEDIUM_ASTEROID_SIZE
        elif asteroid.get_size() == MEDIUM_ASTEROID_SIZE:
            new_asteroid_size = SMALL_ASTEROID_SIZE
        else:
            new_asteroid_size = ASTEROID_DESTROYED
            self.delete_asteroid(asteroid)
        if new_asteroid_size != ASTEROID_DESTROYED:
            new_asteroid_1 = Asteroid(asteroid_location,
                                      (smaller_asteroid_speed_x *
                                       OPPOSITE_DIRECTION_ASTEROID_MOVEMENT,
                                       smaller_asteroid_speed_y *
                                       OPPOSITE_DIRECTION_ASTEROID_MOVEMENT),
                                      new_asteroid_size)
            new_asteroid_2 = Asteroid(asteroid_location,
                                      (smaller_asteroid_speed_x,
                                       smaller_asteroid_speed_y),
                                      new_asteroid_size)
            self.__asteroids_list.extend([new_asteroid_1, new_asteroid_2])
            self._screen.register_asteroid(new_asteroid_1, new_asteroid_size)
            self._screen.register_asteroid(new_asteroid_2, new_asteroid_size)

    def new_asteroid_x_speed(self, asteroid_speed, torpedo_speed):
        """calculate the new x speed of an asteroid after it is hit by a
        torpedo and splits into 2 smaller asteroids."""
        new_x_speed = (torpedo_speed[X_AXIS] + asteroid_speed[X_AXIS]) / (
            math.sqrt((asteroid_speed[X_AXIS]) ** 2 + (asteroid_speed[
                                                           Y_AXIS]) ** 2))
        return new_x_speed

    def new_asteroid_y_speed(self, asteroid_speed, torpedo_speed):
        """calculate the new Y speed of an asteroid after it is hit by a
        torpedo and splits into 2 smaller asteroids."""
        new_y_speed = (torpedo_speed[Y_AXIS] + asteroid_speed[X_AXIS]) / (
            math.sqrt((asteroid_speed[X_AXIS]) ** 2 + (asteroid_speed[
                                                           Y_AXIS]) ** 2))
        return new_y_speed

    def delete_torpedo(self, torpedo):
        """the function removes torpedoes from the torpedoes list and
        unregisters is from the "screen" class."""
        self.__torpedo_list.remove(torpedo)
        self._screen.unregister_torpedo(torpedo)

    def delete_asteroid(self, asteroid):
        """the function removes asteroids from the asteroids list and
        unregisters is from the "screen" class."""
        self.__asteroids_list.remove(asteroid)
        self._screen.unregister_asteroid(asteroid)

    def _keyboard_action(self):
        """this function checks for different keyboard inputs from the user
        and calls the function that does the work if a specific key was
        pressed. """
        if self._screen.is_left_pressed():
            self.__ship.rotate(TURN_LEFT)
        elif self._screen.is_right_pressed():
            self.__ship.rotate(TURN_RIGHT)
        elif self._screen.is_up_pressed():
            self.__ship.accelerate()
        elif self._screen.is_space_pressed():
            self.add_torpedo_to_game()

    def _move_object(self, game_object):
        """this function moves the different objects on the screen.
        It affects the ship, asteroids and torpedoes."""
        new_x_axis = (game_object.get_speed()[X_AXIS] +
                      game_object.get_location()[X_AXIS] -
                      Screen.SCREEN_MIN_X) % DELTA_X + Screen.SCREEN_MIN_X
        new_y_axis = (game_object.get_speed()[Y_AXIS] +
                      game_object.get_location()[Y_AXIS] -
                      Screen.SCREEN_MIN_Y) % DELTA_Y + Screen.SCREEN_MIN_Y
        game_object.set_location(new_x_axis, new_y_axis)

    def _check_if_game_won(self):
        """the function checks if the user won the game. This happens if
        asteroids list is empty."""
        if len(self.__asteroids_list) == ALL_ASTEROIDS_DESTROYED:
            self._screen.show_message(WIN_TITLE, WIN_MESSAGE)
            self._screen.end_game()
            sys.exit()

    def _check_if_game_lost(self):
        """the function checks if the user lost the game. this happens if
        ship's health is 0 due to collisions with asteroids."""
        if self.__ship.get_life() == SHIP_END_GAME_LIFE:
            self._screen.show_message(LOSE_TITLE, LOSE_MESSAGE)
            self._screen.end_game()
            sys.exit()

    def _check_if_q_pressed(self):
        """ checks if the user decided to quit the game and press "q".
        if he did, the game closes."""
        if self._screen.should_end() == True:
            self._screen.show_message(QUIT_GAME_TITLE, QUIT_GAME_MESSAGE)
            self._screen.end_game()
            sys.exit()

    def run(self):
        self._do_loop()
        self._screen.start_screen()

    def _do_loop(self):
        # You don't need to change this method!
        self._game_loop()

        # Set the timer to go off again
        self._screen.update()
        self._screen.ontimer(self._do_loop, 5)

    def _game_loop(self):
        '''
        the function runs a loop of the game, each loop checks for changes
        in a given sequence and updates the occurrences. This function
        checks if the game has ended with a victory or a lose.
        '''
        self._keyboard_action()
        self._ship_action()
        self._asteroid_action()
        self._torpedo_action()
        self._check_if_game_won()
        self._check_if_game_lost()
        self._check_if_q_pressed()


def main(amnt):
    runner = GameRunner(amnt)
    runner.run()


if __name__ == "__main__":
    if len(sys.argv) > 1:
        main(int(sys.argv[1]))
    else:
        main(DEFAULT_ASTEROIDS_NUM)
