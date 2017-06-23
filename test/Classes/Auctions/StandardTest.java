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
public class StandardTest {
    
    public StandardTest() {
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
     * Test of getTimeRemaining method, of class Standard.
     */
    @Test
    public void testGetTimeRemaining() {
        System.out.println("getTimeRemaining");
        Standard instance = null;
        long expResult = 0L;
        long result = instance.getTimeRemaining();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreationDate method, of class Standard.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Standard instance = null;
        Timestamp expResult = null;
        Timestamp result = instance.getCreationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndDate method, of class Standard.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Standard instance = null;
        Timestamp expResult = null;
        Timestamp result = instance.getEndDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
