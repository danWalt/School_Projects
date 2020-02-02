/**
 * The BasherShip class represents a Basher ship of the SpaceWars game. It extends the SpaceShip
 * class and implements it's functions
 */

public class BasherShip extends SpaceShip {

      /**
     * Basher ship round action process
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shield = false;
        if (getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics()) < 0.19) {
            shieldOn();
        }
        if (getPhysics().angleTo(game.getClosestShipTo(this).getPhysics()) < 0) {
            getPhysics().move(true, TURN_RIGHT);
        } else {
            getPhysics().move(true, TURN_LEFT);
        }
        currentEnergy++;
    }
}

