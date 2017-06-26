/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

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
public class BidTest {
    
    Bid testInstance;
    User testUser;
    
    public BidTest() {
    }
    
    @Before
    public void setUp() {
        testUser = new User(1,"testuser", "password", "testalias", "test@mail.nl", true, 1500, "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        testInstance = new Bid(1,testUser,300);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPlacerUsername method, of class Bid.
     */
    @Test
    public void testGetPlacerUsername() {
        System.out.println("getPlacerUsername");
        Bid instance = testInstance;
        String expResult = "testuser";
        String result = instance.getPlacerUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAmount method, of class Bid.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        Bid instance = testInstance;
        double expResult = 300;
        double result = instance.getAmount();
        assertEquals(expResult, result, 0.0);
    }
    
}
