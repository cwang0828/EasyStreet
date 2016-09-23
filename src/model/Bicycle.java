/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
* This Bicycle child class extends from  
* the AbstractVehicle class for the easystreet program. 
* @author Hsin-Jung Wang (Cindy)
* @version 6.0
*/

public class Bicycle extends AbstractVehicle {
    
    /**
     * The number of moves that a bicycle would stay dead. 
     */
    private static final int DEATHCOUNT = 25;
    
    /**
     * Initialize the constructor for the bicycle class. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDirection sets the initial direction of the vehicle bicycle. 
     */
    public Bicycle(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATHCOUNT);
    }

    /**
     * Check that the bicycle travels through trails, street, and green light.
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return (theTerrain == Terrain.LIGHT && theLight == Light.GREEN)
                        || theTerrain == Terrain.TRAIL
                        || theTerrain == Terrain.STREET;
    }
    
    /**
     * Check to see if the front, left, or right is a trail.
     * if not applicable, then the preferable directions are as follow: 
     * straight, left, right, reverse. 
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction currentDirection = getDirection();
        Direction result = this.getDirection().reverse(); 
        if (theNeighbors.get(currentDirection) == Terrain.TRAIL) {
            result = currentDirection;
        } else if (theNeighbors.get(currentDirection.right()) == Terrain.TRAIL) {
            result = currentDirection.right();
        } else if (theNeighbors.get(currentDirection.left()) == Terrain.TRAIL) {
            result = currentDirection.left();
        } else if (theNeighbors.get(currentDirection) == Terrain.STREET
                        || theNeighbors.get(currentDirection) == Terrain.LIGHT) {
            result = currentDirection;
        } else if (canPass(theNeighbors.get(currentDirection.left()), Light.GREEN)) {
            result = currentDirection.left();
        } else if (canPass(theNeighbors.get(currentDirection.right()), Light.GREEN)) {
            result = currentDirection.right();
        }
        return result;
    }
}
