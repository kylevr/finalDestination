/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;
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
    Countdown test;
    Product product;
    User seller;
    Timestamp time;

    @Before
    public void setUp() {
         seller = new User(1, "testUser", "password", "testAlias", "test@test.nl", true, 5000.00, "");
        product = new Product(1, "", "", "");
        time = new Timestamp(System.currentTimeMillis());
        test = new Countdown(1,seller,product,5,5000,25,10,500,StatusEnum.New,"","",500,time);
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
        Timestamp expResult = time;
        Timestamp result = test.getCreationDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPriceLoweringAmount method, of class Countdown.
     */
    @Test
    public void testGetPriceLoweringAmount() {
        System.out.println("getPriceLoweringAmount");
        double expResult = 25.0;
        double result = test.getPriceLoweringAmount();
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getPriceLoweringDelay method, of class Countdown.
     */
    @Test
    public void testGetPriceLoweringDelay() {
        System.out.println("getPriceLoweringDelay");
        double expResult = 10.0;
        double result = test.getPriceLoweringDelay();
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of setPrice method, of class Countdown.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        test.setPrice();
        double expResult = 5000;
        double result = test.getCurrentPrice();
        assertEquals(expResult, result, 0.1);
    }

}
