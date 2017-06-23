/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes.Auctions;

import Classes.Bid;
import Classes.Product;
import Classes.User;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class AuctionTest {

    public AuctionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    Auction test;
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
     * Test of getInstabuyPrice method, of class Auction.
     */
    @Test
    public void testGetInstabuyPrice() {
        System.out.println("getInstabuyPrice");
        double expResult = 750.0;
        double result = test.getInstabuyPrice();
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of isInstabuyable method, of class Auction.
     */
    @Test
    public void testIsInstabuyable() {
        System.out.println("isInstabuyable");
        boolean expResult = false;
        boolean result = test.isInstabuyable();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBestBid method, of class Auction.
     */
    @Test
    public void testGetBestBid() {
        System.out.println("getBestBid");
        Bid expResult = new Bid(1, new User(2, "buyer", "test", "1@2.nl", "0", false, 600, ""), 600);
        test.addBid(expResult);
        Bid result = test.getBestBid();
        assertEquals(expResult, result);
    }

    /**
     * Test of addBid method, of class Auction.
     */
    @Test
    public void testAddBid_ArrayList() {
        System.out.println("addBid");
        Bid test1 = new Bid(1, new User(2, "buyer", "test", "1@2.nl", "0", false, 600, ""), 600);
        Bid test2 = new Bid(1, new User(2, "buyer", "test", "1@2.nl", "0", false, 600, ""), 600);

        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(test1);
        bids.add(test2);
        test.addBid(bids);
        ArrayList<Bid> result = test.getBids();
        assertEquals(bids, result);
    }

    /**
     * Test of setCurrentPrice method, of class Auction.
     */
    @Test
    public void testSetCurrentPrice() {
        System.out.println("setCurrentPrice");
        double newPrice = 1100.0;
        test.setCurrentPrice(newPrice);
        assertEquals(newPrice, test.getCurrentPrice(), 0.1);
    }

    /**
     * Test of getBids method, of class Auction.
     */
    @Test
    public void testGetBids() {
        System.out.println("getBids");
        Bid test1 = new Bid(1, new User(2, "buyer", "test", "1@2.nl", "0", false, 600, ""), 600);
        Bid test2 = new Bid(1, new User(2, "buyer", "test", "1@2.nl", "0", false, 600, ""), 600);

        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(test1);
        bids.add(test2);
        test.addBid(bids);
        ArrayList<Bid> result = test.getBids();
        assertEquals(bids, result);
    }

    /**
     * Test of getProduct method, of class Auction.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        Product result = test.getProduct();
        assertEquals(product, result);
    }

    /**
     * Test of getDescription method, of class Auction.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String expResult = "nice";
        String result = test.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatus method, of class Auction.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        StatusEnum expResult = StatusEnum.New;
        StatusEnum result = test.getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrentPrice method, of class Auction.
     */
    @Test
    public void testGetCurrentPrice() {
        System.out.println("getCurrentPrice");
        Double expResult = 500.0;
        Double result = test.getCurrentPrice();
        assertEquals(expResult, result,0.1);
    }

    /**
     * Test of getProductQuantity method, of class Auction.
     */
    @Test
    public void testGetProductQuantity() {
        System.out.println("getProductQuantity");
        int expResult = 5;
        int result = test.getProductQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProductQuantity method, of class Auction.
     */
    @Test
    public void testSetProductQuantity() {
        System.out.println("setProductQuantity");
        int buyAmount = 1;
        test.setProductQuantity(buyAmount);
        int expResult = 4;
        int result = test.getProductQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getImageURLs method, of class Auction.
     */
    @Test
    public void testGetImageURLs() {
        System.out.println("getImageURLs");
        String expResult = "test.jpg";
        String result = test.getImageURLs()[0];
        assertEquals(expResult, result);
    }

    /**
     * Test of getSeller method, of class Auction.
     */
    @Test
    public void testGetSeller() {
        System.out.println("getSeller");
        User expResult = seller;
        User result = test.getSeller();
        assertEquals(expResult, result);
    }


    /**
     * Test of getId method, of class Auction.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 1;
        int result = test.getId();
        assertEquals(expResult, result);
    }

    public class AuctionImpl extends Auction {

        public AuctionImpl() {
            super(null, null, 0, 0.0, 0.0, null, "", "");
        }
    }

}
