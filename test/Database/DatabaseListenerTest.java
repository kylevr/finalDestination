/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Database;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SwekLord420
 */
public class DatabaseListenerTest {
    
    public DatabaseListenerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUpdateAuctionList method, of class DatabaseListener.
     */
    @Test
    public void testGetUpdateAuctionList() {
        System.out.println("getUpdateAuctionList");
        DatabaseListener instance = new DatabaseListener();
        ArrayList<Integer> expResult = new ArrayList<>();
        ArrayList<Integer> result = instance.getUpdateAuctionList();
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of getUpdateQueuepurchaseList method, of class DatabaseListener.
     */
    @Test
    public void testGetUpdateQueuepurchaseList() {
        System.out.println("getUpdateQueuepurchaseList");
        DatabaseListener instance = new DatabaseListener();
        ArrayList<Integer> expResult = new ArrayList<>();
        ArrayList<Integer> result = instance.getUpdateQueuepurchaseList();
        assertEquals(expResult.size(), result.size());
    }
    
}
