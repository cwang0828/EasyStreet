/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
 * This Truck child class extends from the AbstractVehicle class 
 * for the easystreet program. 
 * @author Hsin-Jung Wang (Cindy)
 * @version 6.0
 */
public class Truck extends AbstractVehicle {
    
    /**
     * Initializing the constructor for Truck. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDirection sets the initial direction of the vehicle truck. 
     */
    public Truck(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, 0);
    }

    /**
     * To return a true or false on whether the truck can pass through the given 
     * terrain. The vehicle truck can only travel on the street and through 
     * the light. 
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT;
    }
    
    /**
     * The vehicle truck randomly selects to go straight, left, or right. 
     * If none of the three directions is legal, then reverse. 
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction currentDirection = Direction.random();
        Direction result = this.getDirection().reverse();
        if (checkRightLeftFront(theNeighbors)) {
            while (currentDirection == this.getDirection().reverse()
                            || (theNeighbors.get(currentDirection) != Terrain.STREET
                            && theNeighbors.get(currentDirection) != Terrain.LIGHT)) {
                currentDirection = Direction.random();
            }
            result = currentDirection; 
        }
        return result; 
    } 
  
    /**
     * Check whether either of the directions (right, left and front) is legal. 
     * @param theNeighbors stores information about the direction 
     * and the terrain of the Truck. 
     * @return true or false on whether at least one of the three directions 
     * (right, left, and front) is legal. 
     */
    private boolean checkRightLeftFront(final Map<Direction, Terrain> theNeighbors) {
        return theNeighbors.get(getDirection()) == Terrain.STREET 
                      || theNeighbors.get(getDirection().left()) == Terrain.STREET
                      || theNeighbors.get(getDirection().right()) == Terrain.STREET
                      || theNeighbors.get(getDirection()) == Terrain.LIGHT 
                      || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                      || theNeighbors.get(getDirection().right()) == Terrain.LIGHT; 
    } 
}