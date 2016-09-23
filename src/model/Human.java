/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
* This Human child class extends from  
* the AbstractVehicle class for the easystreet program. 
* @author Hsin-Jung Wang (Cindy)
* @version 6.0
*/
public class Human extends AbstractVehicle {

    /**
     * The number of moves that a human would stay dead. 
     */
    private static final int DEATHCOUNT = 45;
    
    /**
     * Store my initial terrain.
     */
    private final Terrain myTerrain; 
    
    /**
     * Initializing the constructor for the Human class. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDirection sets the initial direction of the vehicle human. 
     * @param theTerrain sets the initial terrain where the vehicle human begins.
     */
    public Human(final int theX, final int theY, 
                 final Direction theDirection, final Terrain theTerrain) {
        super(theX, theY, theDirection, DEATHCOUNT);
        myTerrain = theTerrain;
        
    }

    /**
     * Check that the terrain that a human is walking on is the same as 
     * its initial terrain. 
     * @param theTerrain is the terrain during which a human is walking on. 
     * @param theLight is the color of the light during which a human encounters. 
     * @return whether human can pass through the given type of terrain. 
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
  public boolean canPass(final Terrain theTerrain, final Light theLight) {
        final Terrain initialTerrain = myTerrain;
        boolean sameTerrain = false;
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT) {
            if (initialTerrain.equals(Terrain.STREET) 
                            || initialTerrain.equals(Terrain.LIGHT)) {
                sameTerrain = true; 
            }
        } else {
            if (initialTerrain.equals(theTerrain)) {
                sameTerrain = true;
            }
        }
        return sameTerrain;
    }
    
    
    /**
     * Check that the direction that is chosen by a human is random 
     * and that a human ignores the color of traffic lights. 
     * @param theNeighbors contains the types of terrain that neighbors a human. 
     * @return the direction that the human moves for the next step. 
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction currentDirection = Direction.random();
        while (!canPass(theNeighbors.get(currentDirection), Light.GREEN)) {
            currentDirection = Direction.random();
        }
        return currentDirection;
    }
}
