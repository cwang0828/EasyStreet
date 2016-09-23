/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
* This Car child class extends from the AbstractVehicle class 
* for the easystreet program. 
* @author Hsin-Jung Wang (Cindy)
* @version 6.0
*/

public class Car extends AbstractVehicle {

    /**
     * The number of moves that a car would stay dead. 
     */
    private static final int DEATHCOUNT = 5;
    
    /**
     * Initializing the constructor for Car. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDirection sets the initial direction of the vehicle Taxi. 
     */
    public Car(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATHCOUNT);
    }

    /**
     * Checks whether the vehicle car only travels on street 
     * and through green and yellow lights. 
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean pass = false; 
        if (theTerrain == Terrain.STREET) {
            pass = true;
        } else if (theTerrain == Terrain.LIGHT 
                        && (theLight == Light.GREEN || theLight == Light.YELLOW)) {
            pass = true;
        }
        return pass;
    }
    
    /**
     * Check that the vehicle car has the following direction preference:
     * straight, left, right. If none of the three directions is legal, then reverse.
     * @see model.Vehicle#chooseDirection(java.util.Map)
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction currentDirection = getDirection();
        final Direction result;
        if (theNeighbors.get(getDirection()) == Terrain.STREET 
                        || theNeighbors.get(getDirection()) == Terrain.LIGHT) {
            result = currentDirection;
        } else if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                        || theNeighbors.get(getDirection().left()) == Terrain.LIGHT) {
            result = currentDirection.left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.STREET
                        || theNeighbors.get(getDirection().right()) == Terrain.LIGHT) {
            result = currentDirection.right();
        } else {
            result = currentDirection.reverse();
        }
        return result;
    }
}
