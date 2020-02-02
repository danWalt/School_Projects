import oop.ex2.*;

public class SpaceShipFactory {

    /*represents the Human Ship parameter*/
    private static final String HUMAN_SHIP = "h";
    /*represents the Runner Ship parameter*/
    private static final String RUNNER_SHIP = "r";
    /*represents the Basher Ship parameter*/
    private static final String BASHER_SHIP = "b";
    /*represents the Aggressive Ship parameter*/
    private static final String AGGRESSIVE_SHIP = "a";
    /*represents the Drunkard Ship parameter*/
    private static final String DRUNKARD_SHIP = "d";
    /*represents the Special Ship parameter*/
    private static final String SPECIAL_SHIP = "s";

    /**
     * This method receives a list of strings via user input and creates a spaceship object array with
     * spaceships
     * matching the user input.
     * @param args user input spaceships letter representations
     * @return a spaceship array with spaceships matching the user input. returns null if user input didn't
     * match any spaceship type
     */

    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] ships = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            SpaceShip newShip = null;
            switch (args[i]) {
                case HUMAN_SHIP:
                    newShip = new HumanShip();
                    break;
                case RUNNER_SHIP:
                    newShip = new RunnerShip();
                    break;
                case BASHER_SHIP:
                    newShip = new BasherShip();
                    break;
                case AGGRESSIVE_SHIP:
                    newShip = new AggresiveShip();
                    break;
                case DRUNKARD_SHIP:
                    newShip = new DrunkardShip();
                    break;
                case SPECIAL_SHIP:
                    newShip = new SpecialShip();
                    break;
                default:
                    newShip = null;
            }
            ships[i] = newShip;
        }
        return ships;
    }
}
