/*
 * TCSS 305 - Easy Street
 */

package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.Human;
import model.Light;
import model.Terrain;

import org.junit.Test;

/**
 * Unit tests for class Human.
 * 
 * @author Alan Fowler (acfowler@uw.edu)
 * @version Spring 2015
 */
public class HumanTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** Test method for Human constructor. */
    @Test
    public void testHumanConstructor() {
        final Human h = new Human(10, 11, Direction.NORTH, Terrain.GRASS);
        
        assertEquals("Human x coordinate not initialized correctly!", 10, h.getX());
        assertEquals("Human y coordinate not initialized correctly!", 11, h.getY());
        assertEquals("Human direction not initialized correctly!",
                     Direction.NORTH, h.getDirection());
        assertEquals("Human death time not initialized correctly!", 45, h.getDeathTime());
        assertTrue("Human isAlive() fails initially!", h.isAlive());
    }
    
    /** Test method for Human setters. */
    @Test
    public void testHumanSetters() {
        final Human h = new Human(10, 11, Direction.NORTH, Terrain.GRASS);
        
        h.setX(12);
        assertEquals("Human setX failed!", 12, h.getX());
        h.setY(13);
        assertEquals("Human setY failed!", 13, h.getY());
        h.setDirection(Direction.NORTH);
        assertEquals("Human setDirection failed!", Direction.NORTH, h.getDirection());
    }

    /**
     * Test method for {@link Human#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        // start from each terrain type except WALL
        for (final Terrain testTerrain : Terrain.values()) {
            if (testTerrain != Terrain.WALL) { // Humans do not start on Walls
                final Human human = new Human(0, 0, Direction.NORTH, testTerrain);
                // go to each terrain type
                for (final Terrain t : Terrain.values()) {
                    // try the test under each light condition
                    for (final Light l : Light.values()) {
                        if ((t == testTerrain)
                            || (t == Terrain.LIGHT && testTerrain == Terrain.STREET)
                            || (t == Terrain.STREET && testTerrain == Terrain.LIGHT)) {
                            // humans can pass the terrain they start on under any light
                            // conditions, and can also pass lights if they start on
                            // streets and vice-versa

                            assertTrue("Human started on " + testTerrain
                                                       + " should be able to pass " + t
                                                       + ", with light " + l,
                                       human.canPass(t, l));
                        } else {
                            // humans can't leave their terrain

                            assertFalse("Human started on " + testTerrain
                                         + " should NOT be able to pass " + t
                                         + ", with light " + l, human.canPass(t, l));
                        }
                    }
                } 
            }
        }
    }

    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirection() {
        // There is an assumption that there will always be at least one valid choice
        // for the chooseDirection() method to return. No test for surrounded by WALLs!
        
        // humans need to stay on their own terrain
        // (STREET and LIGHT are considered the same Terrain for this purpose)
        // Humans choose randomly from among directions which lead to STREET or LIGHT
        
        // If a Human starts on STREET it should also be willing to move to LIGHT
        // If a Human starts on LIGHT it should also be willing to move to STREET
        
        // The program should not freeze
        // if a Human is on STREET and the only valid choices are LIGHT
        // if a Human is on LIGHT and the only valid choices are STREET

        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.WALL);
        neighbors.put(Direction.NORTH, Terrain.WALL);
        /*   W
         * W ? ?
         *   ?
         */

        for (final Terrain t : Terrain.values()) {
            if (t == Terrain.WALL) {
                continue; // Humans don't start on WALLs
            }
            final Human human = new Human(0, 0, Direction.NORTH, t);
            neighbors.put(Direction.EAST, t);
            
            // provide both STREET and LIGHT options
            if (t == Terrain.STREET) {
                neighbors.put(Direction.SOUTH, Terrain.LIGHT);
            } else if (t == Terrain.LIGHT) {
                neighbors.put(Direction.SOUTH, Terrain.STREET);
            } else {
                neighbors.put(Direction.SOUTH, t);
            }
            /*   W
             * W t t
             *   ?         LIGHT, STREET, or t depending on t
             */
            int tries = 0;
            boolean seenSouth = false;
            boolean seenEast = false;
            while (tries < TRIES_FOR_RANDOMNESS) {
                tries = tries + 1;
                final Direction dir = human.chooseDirection(neighbors);
                assertTrue("on " + t + ", should choose east or south, was " + dir,
                           dir == Direction.EAST || dir == Direction.SOUTH);                   
                    
                seenSouth = seenSouth || dir == Direction.SOUTH;
                seenEast = seenEast || dir == Direction.EAST;
            }
            assertTrue("human randomness", seenSouth && seenEast);
            
            // now check with only one valid option    
            neighbors.put(Direction.EAST, Terrain.WALL);
            /*   W
             * W t W
             *   ?         LIGHT, STREET, or t depending on t
             */
            tries = 0;
            while (tries < TRIES_FOR_RANDOMNESS) {
                tries = tries + 1;
                final Direction dir = human.chooseDirection(neighbors);
                assertSame("invalid dir chosen, should be south, was " + dir,
                           Direction.SOUTH, dir);
            }
            
            // for STREET and LIGHT also check with only the opposite type available 
            neighbors.put(Direction.EAST, t);
            if (t == Terrain.STREET || t == Terrain.LIGHT) {
                neighbors.put(Direction.SOUTH, t);
                /*   W
                 * W t t
                 *   t
                 */
                tries = 0;
                seenSouth = false;
                seenEast = false;
                while (tries < TRIES_FOR_RANDOMNESS) {
                    tries = tries + 1;
                    final Direction dir = human.chooseDirection(neighbors);
                    assertTrue("on " + t + ", should choose east or south, was " + dir,
                               dir == Direction.EAST || dir == Direction.SOUTH);
                    seenSouth = seenSouth || dir == Direction.SOUTH;
                    seenEast = seenEast || dir == Direction.EAST;
                }
                assertTrue("human randomness issue! SOUTH : " + seenSouth
                                               + "; EAST : " + seenEast,
                           seenSouth && seenEast);
            }
        }
    }

}
