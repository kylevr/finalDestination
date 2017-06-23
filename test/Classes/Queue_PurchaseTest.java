/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SwekLord420
 */
public class Queue_PurchaseTest {
    private Queue_Purchase queuePurchase;
    
    private int quantity;
    private double minPrice;
    private double maxPrice;
    private int productID;
    private int placerID;
    
    public Queue_PurchaseTest() {
        
    }

    
    @Before
    public void setUp() {
        //    public Queue_Purchase(int Quantity, double minPrice, double maxPrice, int productID, int placerID) {
        this.quantity = 5;
        this.minPrice = 2.0;
        this.maxPrice = 4.0;
        this.productID = 1;
        this.placerID = 1;        
        
        queuePurchase = new Queue_Purchase(quantity,minPrice,maxPrice,productID,placerID);
    }
    
    @After
    public void tearDown() {
        queuePurchase = null;
    }

    /**
     * Test of getQuantity method, of class Queue_Purchase.
     */
    @Test
    public void testGetQuantity() {
        System.out.println("getQuantity");
        int expResult = 5;
        int result = queuePurchase.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinPrice method, of class Queue_Purchase.
     */
    @Test
    public void testGetMinPrice() {
        System.out.println("getMinPrice");
        double expResult = 2.0;
        double result = queuePurchase.getMinPrice();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getMaxPrice method, of class Queue_Purchase.
     */
    @Test
    public void testGetMaxPrice() {
        System.out.println("getMaxPrice");
        double expResult = 4.0;
        double result = queuePurchase.getMaxPrice();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getProductID method, of class Queue_Purchase.
     */
    @Test
    public void testGetProductID() {
        System.out.println("getProductID");
        int expResult = 1;
        int result = queuePurchase.getProductID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlacerID method, of class Queue_Purchase.
     */
    @Test
    public void testGetPlacerID() {
        System.out.println("getPlacerID");
        int expResult = 1;
        int result = queuePurchase.getPlacerID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setID method, of class Queue_Purchase.
     */
    @Test
    public void testSetID() {
        System.out.println("setID");
        int id = 2;
        queuePurchase.setID(id);
        int expResult = 2;
        assertEquals(expResult, queuePurchase.getID());
    }

    /**
     * Test of getID method, of class Queue_Purchase.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        int expResult = 1;
        queuePurchase.setID(1);
        int result = queuePurchase.getID();
        assertEquals(expResult, result);
    }
    
}
