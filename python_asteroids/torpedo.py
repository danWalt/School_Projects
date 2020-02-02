
X_AXIS = 0
Y_AXIS = 1
TORPEDO_RADIUS = 4
TORPEDO_LIFE = 200
REDUCE_TORPEDO_LIFE = -1


class Torpedo:
    """ creates a torpedo object"""

    def __init__(self, location, direction, speed):
        self.__location = location
        self.__direction = direction
        self.__speed = speed
        self.__radius = TORPEDO_RADIUS
        self.__torpedo_life_time = TORPEDO_LIFE

    def get_speed(self):
        """returns torpedo's speed"""
        return self.__speed

    def get_location(self):
        """returns torpedo's location"""
        return self.__location

    def get_direction(self):
        """returns torpedo's direction"""
        return self.__direction

    def set_location(self, x_location, y_location):
        """updates torpedo's location"""
        self.__location = (x_location, y_location)

    def get_radius(self):
        """returns torpedo's radius"""
        return self.__radius

    def get_life_time(self):
        """returns torpedo's remaining life time"""
        return self.__torpedo_life_time

    def reduce_life_time(self):
        """reduces torpedo's life time"""
        self.__torpedo_life_time += REDUCE_TORPEDO_LIFE
