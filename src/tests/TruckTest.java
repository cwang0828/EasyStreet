/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Truck. 
 * 
 * @author Hsin-Jung Wang (Cindy)
 * @version 6.0
 */
public class TruckTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 500;
    
    /**
     * Instance variable for the truck. 
     */
    private Truck myTruck;
    
    /**
     * Initialize the test fixture before each test.  
     */
    @Before 
    public void setUp() {
        myTruck = new Truck(10, 11, Direction.NORTH);
    }
    
    /** 
     * Test method for Truck constructor. 
     */
    @Test
    public void testTruckConstructor() {
        assertEquals("Truck x coordinate not initialized correctly!", 10, myTruck.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 11, myTruck.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, myTruck.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, myTruck.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", myTruck.isAlive());
    }
    
    /** 
     * Test method for Truck setters. 
     */
    @Test
    public void testTruckSetters() {
        myTruck.setX(12);
        assertEquals("Human setX failed!", 12, myTruck.getX());
        myTruck.setY(13);
        assertEquals("Human setY failed!", 13, myTruck.getY());
        myTruck.setDirection(Direction.NORTH);
        assertEquals("Human setDirection failed!", Direction.NORTH, myTruck.getDirection());
    }
    
    /**
     * Test whether the truck can only move in street or light. 
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public void testCanPass() {
        assertFalse(myTruck.canPass(Terrain.GRASS, Light.GREEN));
        assertFalse(myTruck.canPass(Terrain.WALL, Light.GREEN));
        assertFalse(myTruck.canPass(Terrain.TRAIL, Light.GREEN));
        assertTrue(myTruck.canPass(Terrain.LIGHT, Light.GREEN));
        assertTrue(myTruck.canPass(Terrain.STREET, Light.GREEN));
    }
    
    /**
     * Test whether the truck that is surrounded by three walls (left, right, front)
     * is a false condition. 
     * Test method for {@link model.Truck#checkRightLeftFront(java.util.Map)}.
     */
    @Test
    public void testCheckRightLeftFrontWall() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(myTruck.getDirection().left(), Terrain.WALL);
        neighbors.put(myTruck.getDirection().right(), Terrain.WALL);
        neighbors.put(myTruck.getDirection(), Terrain.WALL);
        int tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection().reverse());
        }
        
    }
    
    /**
     * Test whether having the left side of the terrain to be street or light
     * would produce a true condition. 
     * Test method for {@link model.Truck#checkRightLeftFront(java.util.Map)}.
     */
    @Test
    public void testCheckLeftWhenRightFrontTrail() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(myTruck.getDirection().left(), Terrain.STREET);
        neighbors.put(myTruck.getDirection().right(), Terrain.TRAIL);
        neighbors.put(myTruck.getDirection(), Terrain.TRAIL);
        int tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection().left());
        }
        neighbors.put(myTruck.getDirection().left(), Terrain.LIGHT);
        tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection().left());
        }
    }
    
    /**
     * Test whether having the front of the terrain to be street or street
     * would produce a true condition.
     * Test method for {@link model.Truck#checkRightLeftFront(java.util.Map)}.
     */
    @Test
    public void testCheckFrontWhenRightLeftGrass() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(myTruck.getDirection().left(), Terrain.GRASS);
        neighbors.put(myTruck.getDirection().right(), Terrain.GRASS);
        neighbors.put(myTruck.getDirection(), Terrain.STREET);
        int tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection());
        }
        
        neighbors.put(myTruck.getDirection(), Terrain.LIGHT);
        tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection());
        }
    }
    
    /**
     * Test whether having the right side of the terrain to be street or light 
     * would produce a true condition.
     * Test method for {@link model.Truck#checkRightLeftFront(java.util.Map)}.
     */
    @Test
    public void testCheckRightWhenLeftTrailFrontGrass() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(myTruck.getDirection().left(), Terrain.TRAIL);
        neighbors.put(myTruck.getDirection().right(), Terrain.STREET);
        neighbors.put(myTruck.getDirection(), Terrain.GRASS);
        int tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection().right());
        }
        neighbors.put(myTruck.getDirection().right(), Terrain.LIGHT);
        tries = 0;
        while (tries < TRIES_FOR_RANDOMNESS) {
            tries++;
            final Direction dir = myTruck.chooseDirection(neighbors);
            assertEquals(dir, myTruck.getDirection().right());
        }
    }
}
