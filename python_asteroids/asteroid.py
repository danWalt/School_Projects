
import math

START_DIRECTION = 0
X_AXIS = 0
Y_AXIS = 1
STARTING_SIZE = 3
SIZE_COEFFICIENT = 10
NORMALIZING_FACTOR = 5


class Asteroid:
    """ asteroid object constructor"""

    def __init__(self, location, speed, size=STARTING_SIZE):
        self.__location = location
        self.__current_speed = speed
        self.__size = size

    def get_size(self):
        """Returns asteroid's size"""
        return self.__size

    def get_speed(self):
        """returns asteroid's speed"""
        return self.__current_speed

    def get_location(self):
        """returns asteroid's location"""
        return self.__location

    def get_radius(self):
        """returns asteroid's radius"""
        return self.__size * SIZE_COEFFICIENT - NORMALIZING_FACTOR

    def set_location(self, x_location, y_location):
        """updates asteroid's location"""
        self.__location = (x_location, y_location)

    def has_intersection(self, obj):
        "checks if asteroid has been hit by a different game object"
        distance = math.sqrt((obj.get_location()[X_AXIS] - self.__location[
            X_AXIS]) ** 2 + (obj.get_location()[Y_AXIS] - self.__location[
            Y_AXIS]) ** 2)
        if distance <= (self.get_radius() + obj.get_radius()):
            return True
        else:
            return False
