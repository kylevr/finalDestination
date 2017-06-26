/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lesley Peters
 */
public class FeedbackTest {
    
    Feedback testInstance;
    Date timeCreated;
    
    public FeedbackTest() {
    }
    
    @Before
    public void setUp() {
        timeCreated = new Date();
        testInstance = new Feedback(timeCreated, "testuser1", "testuser2", 5, "test feedback description");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTimeCreated method, of class Feedback.
     */
    @Test
    public void testGetTimeCreated() {
        System.out.println("getTimeCreated");
        Feedback instance = testInstance;
        Date expResult = timeCreated;
        Date result = instance.getTimeCreated();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRating method, of class Feedback.
     */
    @Test
    public void testGetRating() {
        System.out.println("getRating");
        Feedback instance = testInstance;
        int expResult = 5;
        int result = instance.getRating();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Feedback.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Feedback instance = testInstance;
        String expResult = "test feedback description";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserFrom_Username method, of class Feedback.
     */
    @Test
    public void testGetUserFrom_Username() {
        System.out.println("getUserFrom_Username");
        Feedback instance = testInstance;
        String expResult = "testuser1";
        String result = instance.getUserFrom_Username();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserTo_Username method, of class Feedback.
     */
    @Test
    public void testGetUserTo_Username() {
        System.out.println("getUserTo_Username");
        Feedback instance = testInstance;
        String expResult = "testuser2";
        String result = instance.getUserTo_Username();
        assertEquals(expResult, result);
    }
    
}
