/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes.Auctions;

import java.sql.Timestamp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author piete
 */
public class CountdownTest {
    
    public CountdownTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCreationDate method, of class Countdown.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Countdown instance = null;
        Timestamp expResult = null;
        Timestamp result = instance.getCreationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPriceLoweringAmount method, of class Countdown.
     */
    @Test
    public void testGetPriceLoweringAmount() {
        System.out.println("getPriceLoweringAmount");
        Countdown instance = null;
        double expResult = 0.0;
        double result = instance.getPriceLoweringAmount();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPriceLoweringDelay method, of class Countdown.
     */
    @Test
    public void testGetPriceLoweringDelay() {
        System.out.println("getPriceLoweringDelay");
        Countdown instance = null;
        double expResult = 0.0;
        double result = instance.getPriceLoweringDelay();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrice method, of class Countdown.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        Countdown instance = null;
        instance.setPrice();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
