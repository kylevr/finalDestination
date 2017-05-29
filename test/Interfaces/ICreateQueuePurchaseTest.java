/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Interfaces;

import Database.QueuePurchaseConnection;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lesley
 */
public class ICreateQueuePurchaseTest {
    
    public ICreateQueuePurchaseTest() {
    }

    /**
     * Test of succesfull use of the createQueuePurchase method, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseSucces() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 1;
        double minPrice = 100;
        double maxPrice = 300;
        int productID = 1;
        int placerID = 1;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertTrue(result);
    }
    
    /**
     * Test of the createQueuePurchase method with 0 quantity, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseWithoutQuantity() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 0;
        double minPrice = 100;
        double maxPrice = 300;
        int productID = 1;
        int placerID = 1;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertFalse(result);
    }
    
    /**
     * Test of the createQueuePurchase method without a max price, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseWithoutMaxPrice() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 1;
        double minPrice = 0;
        double maxPrice = 0;
        int productID = 1;
        int placerID = 1;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertFalse(result);
    }
    
    /**
     * Test of the createQueuePurchase method with a max price less than the min price, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseWithLowMaxPrice() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 1;
        double minPrice = 100;
        double maxPrice = 99.9;
        int productID = 1;
        int placerID = 1;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertFalse(result);
    }
    
    /**
     * Test of the createQueuePurchase method with a productID that doesn't excist in the database, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseWithWrongProductID() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 1;
        double minPrice = 100;
        double maxPrice = 200;
        int productID = 1000000;
        int placerID = 1;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertFalse(result);
    }
    
    /**
     * Test of the createQueuePurchase method with a productID that doesn't excist in the database, of class ICreateQueuePurchase.
     */
    @Test
    public void testCreateQueuePurchaseWithWrongPlacerID() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 1;
        double minPrice = 100;
        double maxPrice = 200;
        int productID = 1;
        int placerID = 1000000;
        ICreateQueuePurchase instance = new ICreateQueuePurchaseImpl();
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertFalse(result);
    }

    public class ICreateQueuePurchaseImpl implements ICreateQueuePurchase {

        //Method copied from Grand_Exchange class
        public boolean createQueuePurchase(int Quantity, double minPrice, double maxPrice, int productID, int placerID) throws RemoteException {
            QueuePurchaseConnection qPConn = new QueuePurchaseConnection();
            return qPConn.insertQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        }
    }
    
}
