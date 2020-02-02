import java.awt.Image;
import java.util.Random;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {

    /*Ships starting health */
    private static final int SHIP_STARTING_HEALTH = 22;
    /*Ships starting max energy */
    protected static final int MAX_ENERGY = 210;
    /*Ships starting energy */
    private static final int STARTING_ENERGY = 190;
    /*Number of rounds a ship has to wait between shots */
    private static final int ROUND_BETWEEN_SHOTS = 7;

    /*When bashing a ship gets an extra 18 energy units*/
    private static final int BASHING_BONUS = 18;
    /*When being hit the ship loses 10 energy units*/
    private static final int HIT_ENERGY_REDUCTION = 10;
    /*Getting hit by a shot takes 1 damage*/
    private static final int SHOT_HIT_DAMAGE = 1;
    /*Colliding with another ship without shield costs 1 health point*/
    private static final int COLLISION_DAMAGE = 1;
    /*Shooting costs 19 energy units*/
    private static final int SHOT_COST = 19;
    /*Teleporting costs 140 energy units*/
    private static final int TELEPORT_COST = 140;
    /*Activating shield costs 3 energy units*/
    private static final int SHIELD_COST = 3;

    /**
     * Turning left physics value
     */
    protected static final int TURN_LEFT = 1;
    /**
     * Turning right physics value
     */
    protected static final int TURN_RIGHT = -1;

    /*Represents the current ship max energy level*/
    private int maxEnergy;
    /*Represents the current ship health level*/
    private int currentHealth;
    /*Represents the current ship physics object*/
    private SpaceShipPhysics location;
    /**
     * Tracks each ship number of rounds before it can shoot again
     */
    protected int roundBeforeNextShot;
    /**
     * updates every round and if the shield is turned on
     */
    protected boolean shield;
    /**
     * Updates each ship current energy after each round
     */
    protected int currentEnergy;

    /**
     * A generic constructor for a spaceship.
     */
    public SpaceShip() {
        reset();
    }


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (shield) {
            updateBashingEnergy();
        } else {
            this.currentHealth -= COLLISION_DAMAGE;
            this.maxEnergy -= HIT_ENERGY_REDUCTION;
            if (this.maxEnergy < this.currentEnergy) {
                this.currentEnergy = this.maxEnergy;
            }
        }


    }

    /*Updates the ships energy level if it bashed another ship*/
    private void updateBashingEnergy() {
        this.maxEnergy += BASHING_BONUS;
        this.currentEnergy += BASHING_BONUS;

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        maxEnergy = MAX_ENERGY;
        currentHealth = SHIP_STARTING_HEALTH;
        currentEnergy = STARTING_ENERGY;
        roundBeforeNextShot = 0;
        location = new SpaceShipPhysics();
        shield = false;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.currentHealth <= 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.location;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!this.shield) {
            this.currentHealth -= SHOT_HIT_DAMAGE;
            this.maxEnergy -= HIT_ENERGY_REDUCTION;
            if (this.maxEnergy < this.currentEnergy) {
                this.currentEnergy = this.maxEnergy;
            }
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (shield) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if ((this.roundBeforeNextShot <= 0) && (this.currentEnergy >= SHOT_COST)) {
            game.addShot(this.location);
            this.roundBeforeNextShot = ROUND_BETWEEN_SHOTS;
            this.currentEnergy -= SHOT_COST;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (this.currentEnergy >= SHIELD_COST) {
            this.currentEnergy -= SHIELD_COST;
            this.shield = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.currentEnergy >= TELEPORT_COST) {
            this.currentEnergy -= TELEPORT_COST;
            this.location = new SpaceShipPhysics();
        }
    }
}
