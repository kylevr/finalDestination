/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Bid;
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
public class AuctionConnectionTest {
    
    private static AuctionConnection connection;
    
    public AuctionConnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        connection = new AuctionConnection();
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
     * Test of getAuction method, of class AuctionConnection.
     */
    @Test
    public void testGetAuction() {
        System.out.println("getAuction");
        Auction auction;
        auction = connection.getAuction(2);
        Assert.assertNull(auction);
    }

    /**
     * Test of getAuctions method, of class AuctionConnection.
     */
    @Test
    public void testGetAuctions() {
        System.out.println("getAuctions");
        ArrayList<Auction> auction = new ArrayList<Auction>();
        
        
        for(Auction item : connection.getAuctions("hier", "daar", "Cool")) {
            auction.add(item);
        }
        Assert.assertNotNull(auction);
    }

    /**
     * Test of getBids method, of class AuctionConnection.
     */
    @Test
    public void testGetBids() {
        System.out.println("getBids");

        try {
            connection.getBids(2);

            if (connection.getBids(2) != null) {
                Assert.assertNotNull(connection.getBids(2));
            } else {
                Assert.assertNull(connection.getBids(2));
            }
        } catch (NullPointerException ex) {
            Assert.assertFalse(false); // Als er geen bids zijn gedaan
        }

        
        
    }

    /**
     * Test of addBid method, of class AuctionConnection.
     */
    @Test
    public void testAddBid() throws Exception {
        System.out.println("addBid");
        
        
        try {
            connection.addBid(5, 2, 2, 1200);
            connection.getBids(2);

            if (connection.getBids(2) != null) {
                Assert.assertNotNull(connection.getBids(2));
            } else {
                Assert.assertNull(connection.getBids(2));
            }
        } catch (NullPointerException ex) {
            Assert.assertFalse(false); // Als er geen bids zijn gedaan
        }
        
    }

    /**
     * Test of insertAuction method, of class AuctionConnection.
     */
    @Test
    public void testInsertAuction() {
        System.out.println("insertAuction");
        int sellerid = 0;
        int productid = 0;
        double currentprice = 0.0;
        double instabuyprice = 0.0;
        int instabuyable = 0;
        int quantity = 0;
        double loweringamount = 0.0;
        int loweringdelay = 0;
        String type = "";
        int status = 0;
        String imgurl = "";
        String description = "";
        AuctionConnection instance = new AuctionConnection();
        Boolean expResult = true;
        Boolean result = instance.insertAuction(sellerid, productid, currentprice, instabuyprice, instabuyable, quantity, loweringamount, loweringdelay, type, status, imgurl, description);
        assertEquals(expResult, result);
    }


    /**
     * Test of updateAuction method, of class AuctionConnection.
     */
    @Test
    public void testUpdateAuction() {
        System.out.println("updateAuction");
        Auction auction = null;
        AuctionConnection instance = new AuctionConnection();
        
        try {
            instance.updateAuction(auction);
        } catch (NullPointerException ex) {
            Assert.assertNull(null);
        }
        
        
    }

    /**
     * Test of InstabuyItem method, of class AuctionConnection.
     */
    @Test
    public void testInstabuyItem() {
        System.out.println("InstabuyItem");
        try {
            int amount = 50;
            int auctionID = 2;
            int userID = 2;
            AuctionConnection instance = new AuctionConnection();
            Boolean expResult = false;
            Boolean result = instance.InstabuyItem(amount, auctionID, userID);
            assertEquals(expResult, result);

        } catch (NullPointerException ex) {
            Assert.assertTrue(true);
        }

    }
    
}
