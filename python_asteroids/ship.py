from screen import Screen
from random import randint
import math

X_AXIS = 0
Y_AXIS = 1

STARTING_SPEED = 0
START_DIRECTION = 0
STARTING_LIFE = 3
MIN_LIFE = 1
HIT_DAMAGE = 1

SHIP_RADIUS = 1


class Ship:
    def __init__(self):
        """ a constructor for a ship object"""
        self.__ship_health = STARTING_LIFE
        self.__current_speed = (STARTING_SPEED, STARTING_SPEED)
        self.__location = (randint(Screen.SCREEN_MIN_X, Screen.SCREEN_MAX_X)), \
                          (randint(Screen.SCREEN_MIN_Y, Screen.SCREEN_MAX_Y))
        self.__direction = START_DIRECTION
        self.__size = SHIP_RADIUS

    def get_direction(self):
        """ returns the ship's direction """
        return self.__direction

    def get_radius(self):
        """ returns the ship's radius"""
        return self.__size

    def get_life(self):
        """ return the ship's life """
        return self.__ship_health

    def reduce_health(self):
        """ updates ship's health """
        self.__ship_health -= HIT_DAMAGE
        return self.__ship_health

    def get_speed(self):
        """ returns ship's speed """
        return self.__current_speed

    def get_location(self):
        """ returns ship's location """
        return self.__location

    def rotate(self, direction):
        """ rotates ship's direction """
        self.__direction += direction

    def accelerate(self):
        """ accelerates the ship """
        new_x_speed = self.__current_speed[X_AXIS] + math.cos(math.radians(
            self.__direction))
        new_y_speed = self.__current_speed[Y_AXIS] + math.sin(math.radians(
            self.__direction))
        self.__current_speed = (new_x_speed, new_y_speed)

    def set_location(self, x_location, y_location):
        """ updates ship"s location """
        self.__location = (x_location, y_location)
