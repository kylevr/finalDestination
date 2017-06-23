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
public class StandardTest {
    
    public StandardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    Standard test;
    Product product;
    User seller;
    Timestamp time;
    Timestamp timeEnd;
    @Before
    public void setUp() {
         seller = new User(1, "testUser", "password", "testAlias", "test@test.nl", true, 5000.00, "");
        product = new Product(1, "", "", "");
        time = new Timestamp(System.currentTimeMillis());
        timeEnd = new Timestamp(System.currentTimeMillis()+120000);
        test = new Standard(1,seller,product,50,5,time,timeEnd,StatusEnum.New,"","",5000);
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
        long expResult = 60;
        long result = test.getTimeRemaining();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreationDate method, of class Standard.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Timestamp expResult = time;
        Timestamp result = test.getCreationDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndDate method, of class Standard.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Timestamp expResult = timeEnd;
        Timestamp result = test.getEndDate();
        assertEquals(expResult, result);
    }
    
}
