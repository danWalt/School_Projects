/**
 * The AggressiveShip class represents an Aggressive ship of the SpaceWars game. It extends the SpaceShip
 * class and implements it's functions
 */
public class AggresiveShip extends SpaceShip {

    /**
     * Aggressive ship round action process
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        if (getPhysics().angleTo(game.getClosestShipTo(this).getPhysics()) < Math.abs(0.21)){
            fire(game);
        }
        if (getPhysics().angleTo(game.getClosestShipTo(this).getPhysics()) < 0){
            getPhysics().move(true, TURN_RIGHT);
        }else {
            getPhysics().move(true, TURN_LEFT);
        }
        roundBeforeNextShot--;
        currentEnergy++;
    }
}
