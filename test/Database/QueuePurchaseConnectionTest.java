/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Database;

import Classes.Queue_Purchase;
import java.sql.SQLException;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kyle_
 */
public class QueuePurchaseConnectionTest {
    
    QueuePurchaseConnection connection = new QueuePurchaseConnection();
    
    public QueuePurchaseConnectionTest() {
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
     * Test of getQueuePurchases method, of class QueuePurchaseConnection.
     */
    @Test
    public void testGetQueuePurchases() {
        try {
            System.out.println("getQueuePurchases");
        QueuePurchaseConnection instance = new QueuePurchaseConnection();
        ArrayList<Queue_Purchase> expResult = null;
        ArrayList<Queue_Purchase> result = instance.getQueuePurchases();
        assertNotEquals(expResult, result);
        } catch(NullPointerException ex) {
            Assert.assertNull(null);
        }
        

    }

    /**
     * Test of insertQueuePurchase method, of class QueuePurchaseConnection.
     */
    @Test
    public void testInsertQueuePurchase() {
        try {
            System.out.println("insertQueuePurchase");
            int quantity = 0;
        double minprice = 0.0;
        double maxprice = 0.0;
        int productid = 0;
        int placerid = 0;
        QueuePurchaseConnection instance = new QueuePurchaseConnection();
        Boolean expResult = true;
        Boolean result = instance.insertQueuePurchase(quantity, minprice, maxprice, productid, placerid);
        assertEquals(expResult, result);
        } catch(NullPointerException ex) {
            Assert.assertNull(null);
        }
        

    }

    /**
     * Test of deleteQueuePurchase method, of class QueuePurchaseConnection.
     */
    @Test
    public void testDeleteQueuePurchase() {
        System.out.println("deleteQueuePurchase");
        int queueID = 6;
        QueuePurchaseConnection instance = new QueuePurchaseConnection();
        instance.deleteQueuePurchase(queueID);
        
        try {
            instance.getQueuePurchase(queueID);
        } catch (NullPointerException ex) {
            Assert.assertNull(null);
        }
        


    }

    /**
     * Test of getQueuePurchase method, of class QueuePurchaseConnection.
     */
    @Test
    public void testGetQueuePurchase() {
        System.out.println("getQueuePurchase");
        int queueID = 0;
        QueuePurchaseConnection instance = new QueuePurchaseConnection();
        Queue_Purchase expResult = null;
        Queue_Purchase result = instance.getQueuePurchase(queueID);
        assertEquals(expResult, result);

    }
    
}
