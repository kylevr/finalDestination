/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Interfaces;

import Classes.Auctions.Auction;
import Classes.Bid;
import Classes.Feedback;
import Classes.User;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
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
public class IAuctionTest {
    
    public IAuctionTest() {
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
     * Test of getAuctions method, of class IAuction.
     */
    @Test
    public void testGetAuctions() throws Exception {
        System.out.println("getAuctions");
        IAuction instance = new IAuctionImpl();
        Collection<Auction> expResult = null;
        Collection<Auction> result = instance.getAuctions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBids method, of class IAuction.
     */
    @Test
    public void testGetBids() throws Exception {
        System.out.println("getBids");
        int auctionId = 0;
        IAuction instance = new IAuctionImpl();
        List<Bid> expResult = null;
        List<Bid> result = instance.getBids(auctionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class IAuction.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        String userName = "";
        IAuction instance = new IAuctionImpl();
        User expResult = null;
        User result = instance.getUser(userName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAuction method, of class IAuction.
     */
    @Test
    public void testAddAuction_Auction() throws Exception {
        System.out.println("addAuction");
        Auction auction = null;
        IAuction instance = new IAuctionImpl();
        instance.addAuction(auction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAuction method, of class IAuction.
     */
    @Test
    public void testAddAuction_12args() throws Exception {
        System.out.println("addAuction");
        int userID = 0;
        int productID = 0;
        double startingprice = 0.0;
        double instabuyPrice = 0.0;
        int instabuyable = 0;
        int quantity = 0;
        int iets = 0;
        int iets2 = 0;
        String auctionType = "";
        int iets3 = 0;
        String imageUrl = "";
        String desrcription = "";
        IAuction instance = new IAuctionImpl();
        instance.addAuction(userID, productID, startingprice, instabuyPrice, instabuyable, quantity, iets, iets2, auctionType, iets3, imageUrl, desrcription);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFeedback method, of class IAuction.
     */
    @Test
    public void testAddFeedback() throws Exception {
        System.out.println("addFeedback");
        Feedback feedback = null;
        IAuction instance = new IAuctionImpl();
        instance.addFeedback(feedback);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMail method, of class IAuction.
     */
    @Test
    public void testSendMail() throws Exception {
        System.out.println("sendMail");
        int senderId = 0;
        int receiverId = 0;
        String content = "";
        IAuction instance = new IAuctionImpl();
        instance.sendMail(senderId, receiverId, content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of InstabuyItem method, of class IAuction.
     */
    @Test
    public void testInstabuyItem() throws Exception {
        System.out.println("InstabuyItem");
        int amount = 0;
        int auctionID = 0;
        int userID = 0;
        IAuction instance = new IAuctionImpl();
        boolean expResult = false;
        boolean result = instance.InstabuyItem(amount, auctionID, userID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAuction method, of class IAuction.
     */
    @Test
    public void testUpdateAuction() throws Exception {
        System.out.println("updateAuction");
        Auction auction = null;
        IAuction instance = new IAuctionImpl();
        instance.updateAuction(auction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IAuctionImpl implements IAuction {

        public Collection<Auction> getAuctions() throws RemoteException {
            return null;
        }

        public List<Bid> getBids(int auctionId) throws RemoteException {
            return null;
        }

        public User getUser(String userName) throws RemoteException {
            return null;
        }

        public void addAuction(Auction auction) throws RemoteException {
        }

        public void addAuction(int userID, int productID, double startingprice, double instabuyPrice, int instabuyable, int quantity, int iets, int iets2, String auctionType, int iets3, String imageUrl, String desrcription) throws RemoteException {
        }

        public void addFeedback(Feedback feedback) throws RemoteException {
        }

        public void sendMail(int senderId, int receiverId, String content) throws RemoteException {
        }

        public boolean InstabuyItem(int amount, int auctionID, int userID) throws RemoteException {
            return false;
        }

        public void updateAuction(Auction auction) throws RemoteException {
        }
    }
    
}
