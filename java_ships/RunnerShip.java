import java.lang.Math;


/**
 * The RunnerShip class represents a Runner ship of the SpaceWars game. It extends the SpaceShip
 * class and implements it's functions
 */

public class RunnerShip extends SpaceShip {


    /**
     * Runner ship round action process
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        if (getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics()) < 0.25 && getPhysics()
                .angleTo(game.getClosestShipTo(this).getPhysics()) < Math.abs(0.23)){
            teleport();
        }
        if (getPhysics().angleTo(game.getClosestShipTo(this).getPhysics()) < 0){
            getPhysics().move(true, TURN_LEFT);
        }else {
            getPhysics().move(true, TURN_RIGHT);
        }
        currentEnergy++;

    }


    }
