
/**
 * The SpecialShip class represents a Special ship of the SpaceWars game. It extends the SpaceShip
 * class and implements it's functions
 */

public class SpecialShip extends SpaceShip {

    /*increases by one every game round*/
    private int roundCounter = 0;

    /**
     * Special ship round action
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

        if (roundCounter % 35 == 0) {
            teleport(); //teleports every 35 rounds
        }

        if (getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics()) < 0.25 && getPhysics()
                .angleTo(game.getClosestShipTo(this).getPhysics()) < Math.abs(0.23)) {
            fire(game);
            shieldOn();
        }
        if (getPhysics().angleTo(game.getClosestShipTo(this).getPhysics()) < 0) {
            getPhysics().move(true, TURN_RIGHT);

        } else {
            getPhysics().move(true, TURN_LEFT);

        }
        currentEnergy = MAX_ENERGY;
        roundCounter++;
        roundBeforeNextShot -= 2; //instead of firing every 7 rounds, the special ship can fire every four.
    }
}
