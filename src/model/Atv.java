/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
* This All-Terrain Vehicle (ATV) child class extends from  
* the AbstractVehicle class for the easystreet program. 
* @author Hsin-Jung Wang (Cindy)
* @version 6.0
*/

public class Atv extends AbstractVehicle {

    /**
     * The number of moves that an ATV would stay dead. 
     */
    private static final int DEATHCOUNT = 15;
    
    /**
     * Initialize the constructor for the Atv class. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDir sets the initial direction of the vehicle ATV. 
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATHCOUNT);
        
    }

    /**
     * Checks that the Atv travels through any terrain except walls. 
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain != Terrain.WALL || theTerrain == Terrain.LIGHT;
    }
    
    /**
     * The ATV would randomly select to go straight, left, or right as long as 
     * the terrain traveling to is not a wall. 
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction currentDirection = Direction.random();
        if (checkRightLeftFront(theNeighbors)) {
            while (currentDirection == getDirection().reverse()
                            || theNeighbors.get(currentDirection) == Terrain.WALL) {
                currentDirection = Direction.random();
            }
        }
        return currentDirection;
    }
    
    /**
     * Check whether either of the directions (right, left and front) is legal.
     * @param theNeighbors stores information about the direction 
     * and the terrain of the Truck.
     * @return true or false on whether there is at least one of the three directions
     * (right, left, and front) is not a wall. 
     */
    private boolean checkRightLeftFront(final Map<Direction, Terrain> theNeighbors) {
        return theNeighbors.get(getDirection()) != Terrain.WALL 
                        || theNeighbors.get(getDirection().left()) != Terrain.WALL
                        || theNeighbors.get(getDirection().right()) != Terrain.WALL;
    }
}
