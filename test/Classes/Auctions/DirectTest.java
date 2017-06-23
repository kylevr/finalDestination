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
public class DirectTest {

    public DirectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    Direct test;
    Product product;
    User seller;
    Timestamp time;

    @Before
    public void setUp() {
        seller = new User(1, "testUser", "password", "testAlias", "test@test.nl", true, 5000.00, "");
        product = new Product(1, "", "", "");
        time = new Timestamp(System.currentTimeMillis());
        test = new Direct(1, seller, product, 500, time, 5, StatusEnum.New, "nice", "test.jpg;test2.jpg", 750);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCreationDate method, of class Direct.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Timestamp expResult = time;
        Timestamp result = test.getCreationDate();
        assertEquals(expResult, result);
    }

}
