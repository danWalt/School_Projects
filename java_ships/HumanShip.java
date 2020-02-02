import oop.ex2.GameGUI;

import java.awt.*;

/**
 * The HumanShip class represents a human ship of the SpaceWars game. It extends the SpaceShip class and
 * implements it's functions
 */

public class HumanShip extends SpaceShip {
    /*Default turn value*/
    private static final int STARTING_TURN_VALUE = 0;
    /*turning direction*/
    private int turn = STARTING_TURN_VALUE;
    /*default acceleration value*/
    private boolean ACCELERATE = false;

    /**
     * Human Ship round action process.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shield = false;
        turn = STARTING_TURN_VALUE;
        if (game.getGUI().isTeleportPressed()) {
            teleport();
        }
        calculateMove(game);
        accelerateAndTurn();
        if (game.getGUI().isShieldsPressed()) {
            shieldOn();
        }
        if (game.getGUI().isShotPressed()) {
            fire(game);
        }
        currentEnergy++;
        roundBeforeNextShot--;
    }

    /**
     * Gets the human ship image. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of human ship.
     */
    public Image getImage() {
        if (shield) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }

    /*moves the human ship based on user input*/
    private void accelerateAndTurn() {
        getPhysics().move(ACCELERATE, turn);
    }

    /*updates human ship moving parameters based on user input*/
    private void calculateMove(SpaceWars game) {
        if (game.getGUI().isRightPressed()) {
            turn += TURN_RIGHT;
        }
        if (game.getGUI().isLeftPressed()) {
            turn += TURN_LEFT;
        }
        if (game.getGUI().isUpPressed()) {
            ACCELERATE = true;
        }
    }
}

