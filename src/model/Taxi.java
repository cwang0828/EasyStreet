/**
 * TCSS 305 – Winter 2016. 
 * Assignment 3 - EasyStreet
 */

package model;

/**
* This Taxi child class extends from the AbstractVehicle class 
* for the easystreet program. 
* @author Hsin-Jung Wang (Cindy)
* @version 6.0
*/
public class Taxi extends Car {
    
    /**
     * The number of cycles that the taxi has to wait before it moves pass the light. 
     * The variable is implicitly set to 0.
     */
    private int myClockCycle; 
    
    /**
     * Initializing the constructor for Taxi. 
     * @param theX sets the initial X-coordinate.
     * @param theY sets the initial Y-coordinate.
     * @param theDirection sets the initial direction of the vehicle Taxi.  
     */
    public Taxi(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection);
    }
    /**
     * Checks whether the vehicle taxi only travels on street 
     * and through green and yellow lights. 
     * If a red light is immediately ahead, then the taxi stays still for 
     * 3 clock wise or until the light turns green (whichever occurs first).  
     * @see model.Vehicle#canPass(model.Terrain, model.Light)
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean pass = false; 
        final int maxCycle = 3;
        if (theTerrain == Terrain.STREET) {
            pass = true;
        } else if (theTerrain == Terrain.LIGHT 
                        && (theLight == Light.GREEN || theLight == Light.YELLOW)) {
            pass = true;
        } else if (theTerrain == Terrain.LIGHT  && theLight == Light.RED) {
            if (myClockCycle == maxCycle) {
                pass = true;
                myClockCycle = 0;
            } else {
                myClockCycle++;
            }
        } 
        return pass;
    }

}
