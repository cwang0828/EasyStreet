/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;


/**
 * The abstract parent class implements the Vehicle interface and 
 * has six child classes (Truck, Car, Taxi, ATV, Bicycle, Human). 
 * @author Hsin-Jung Wang (Cindy)
 * @version 6.0
 */
public abstract class AbstractVehicle implements Vehicle {

    /**
     * The X-coordinate of the vehicle object. 
     */
    private int myX;
    
    /**
     * The Y-coordinate of the vehicle object. 
     */
    private int myY;
    
    /**
     * The direction of the vehicle object. 
     */
    private Direction myDirection;
    
    /**
     * The starting X-coordinate of the vehicle object. 
     */
    private final int myStartingX; 
    
    /**
     * The starting Y-coordinate of the vehicle object. 
     */
    private final int myStartingY;
    
    /**
     * The starting direction of the vehicle object. 
     */
    private final Direction myStartingDirection; 
     
    /**
     * The number of times the vehicle should stay dead for. 
     */
    private final int myMaxDeathCount; 
    
    /**
     * Count how many moves has the vehicle stays dead.
     * The count is 0 when the vehicle is alive. 
     */
    private int myDeathCount;

    /**
     * @param theX initializes the vehicle's X-coordinate.
     * @param theY initializes the vehicle's Y-coordinate.
     * @param theDir initializes the vehicle's starting direction.
     * @param theMaxDeathCount initializes the number of moves for which
     * the vehicle should stay dead.
     */
    public AbstractVehicle(final int theX, final int theY,
                           final Direction theDir, final int theMaxDeathCount) {
        myStartingX = theX;
        myStartingY = theY;
        myStartingDirection = theDir; 
        
        myX = theX; 
        myY = theY;
        myDirection = theDir;
        
        myMaxDeathCount = theMaxDeathCount;
        myDeathCount = theMaxDeathCount; 
    }
    
    /**
     * The name of the vehicle type. 
     */
    @Override
    public String toString() {
        return getImageFileName();
    }
    
    /**
     * A query that returns whether the vehicle can pass through the given
     * type of terrain. Since different vehicles in the child classes 
     * have different restrictions on when the vehicle can or cannot pass,
     * see the child classes for specific implementations. 
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public abstract boolean canPass(final Terrain theTerrain, final Light theLight); 

    /**
     * Provide a query that returns the direction that the vehicle would 
     * like to move. Since different vehicles in the child classes 
     * have different preferences for the direction, see the child classes 
     * for specific implementations. 
     * @see model.Vehicle#chooseDirection(java.util.Map)
     */
    @Override
     public abstract Direction chooseDirection(final Map<Direction, Terrain> theNeighbors);
    
    /**
     * Check whether a vehicle has collided with another vehicle.
     * The vehicle will only die if it has a greater maximum death counts. 
     * If the vehicle is dead, then set the count for the dead vehicle to 0. 
     * @param otherVehicle
     * @see model.Vehicle#collide(model.Vehicle)
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (this.getX() == theOther.getX() && this.getY() == theOther.getY()
                        && this.getDeathTime() > theOther.getDeathTime()
                        && isAlive() && theOther.isAlive()) {
            myDeathCount = 0;
        }
    }
    
    /**
     * Return the maximum number of moves for which
     * the vehicle should stay dead.
     * @see model.Vehicle#getDeathTime()
     */
    @Override
    public int getDeathTime() {
        return myMaxDeathCount;
    }
    
    /**
     * Return a string representation of the vehicle's name, 
     * followed by its current state (dead or alive)
     * and a .gif toward the end. 
     * @see model.Vehicle#getImageFileName()
     */
    @Override
    public String getImageFileName() {
        final StringBuilder imageStatus = new StringBuilder(); 
        imageStatus.append(getClass().getSimpleName().toLowerCase());
        if (!isAlive()) {
            imageStatus.append("_dead");
        }
        imageStatus.append(".gif");
        return imageStatus.toString();
    }
    
    
    /**
     * Return the direction in which the vehicle is moving toward. 
     * @see model.Vehicle#getDirection()
     */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /**
     * Return the X-coordinate of the vehicle. 
     * @see model.Vehicle#getX()
     */
    @Override
    public int getX() {
        return myX; 
    }

    /**
     * Return the Y-coordinate of the vehicle. 
     * @see model.Vehicle#getY()
     */
    @Override
    public int getY() {
        return myY;
    }
    
    /**
     * Return the current state of the vehicle
     * (whether it is dead or alive). 
     * The vehicle is alive when the count is equal to the the maximum 
     * number of moves that the given vehicle should be dead for. 
     * @see model.Vehicle#isAlive()
     */
    @Override
    public boolean isAlive() {
        return myDeathCount == myMaxDeathCount;
    }
    
    /**
     * Count the number of moves for which the vehicle has stayed dead.
     * The number of moves that the vehicle has been staying dead 
     * would continue to add up until it reaches the maximum number of moves
     * that the given vehicle should be dead for. 
     * @see model.Vehicle#poke()
     */
    @Override
    public void poke() {
        if (myDeathCount < myMaxDeathCount) {
            myDeathCount++;
        } else {
            myDirection = Direction.random();
        }
    }
    
    /**
     * Reset the vehicle to its initial starting point and starting direction. 
     * Reset the current count to the maximum number of moves
     * that the given vehicle should be dead for. 
     * @see model.Vehicle#reset()
     */
    @Override
    public void reset() {
        myX = myStartingX;
        myY = myStartingY;
        myDirection = myStartingDirection; 
        myDeathCount = myMaxDeathCount;
       
    }
    
    /**
     * Set the vehicle's direction after it is revived (reset). 
     * @see model.Vehicle#setDirection(model.Direction)
     */
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;

    }

    /**
     * Set the vehicle's X-coordination.
     * @see model.Vehicle#setX(int)
     */
    @Override
    public void setX(final int theX) {
        myX = theX;

    }

    /**
     * Set the vehicle's Y-coordination. 
     * @see model.Vehicle#setY(int)
     */
    @Override
    public void setY(final int theY) {
        myY = theY;

    }
}
