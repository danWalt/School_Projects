import java.util.Random;

/**
 * The DrunkardShip class represents a drunk ship of the SpaceWars game. It extends the SpaceShip
 * class and implements it's functions.
 */

public class DrunkardShip extends SpaceShip {


    /**
     * Drunkard ship round action process.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        Random booleanGenerator = new Random();
        boolean accelerate = booleanGenerator.nextBoolean();
        getPhysics().move(accelerate, TURN_RIGHT);
        if(!accelerate){
            fire(game);
        }
        currentEnergy++;
        roundBeforeNextShot--;
    }
}
