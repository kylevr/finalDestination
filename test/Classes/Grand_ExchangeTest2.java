/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

import Classes.Auctions.Auction;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
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
public class Grand_ExchangeTest2 {
    
    public Grand_ExchangeTest2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    Grand_Exchange GX;
    @Before
    public void setUp() throws RemoteException {
        GX = new Grand_Exchange();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class Grand_Exchange.
     */
    @Test
    public void testAddUser() throws RemoteException {
        System.out.println("addUser");
        User user = new User("testaddUser","","");
        GX.addUser(user);
        User result = GX.getUser("testaddUser");
        assertEquals(user, result);
    }

    /**
     * Test of closeAuction method, of class Grand_Exchange.
     */
    @Test
    public void testCloseAuction() throws Exception {
        System.out.println("closeAuction");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeUser method, of class Grand_Exchange.
     */
    @Test
    public void testRemoveUser() {
        System.out.println("removeUser");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAuction method, of class Grand_Exchange.
     */
    @Test
    public void testAddAuction_Auction() throws Exception {
        System.out.println("addAuction");
        Auction auction = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAuction method, of class Grand_Exchange.
     */
    @Test
    public void testRemoveAuction() {
        System.out.println("removeAuction");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProduct method, of class Grand_Exchange.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addQueuePurchase method, of class Grand_Exchange.
     */
    @Test
    public void testAddQueuePurchase() {
        System.out.println("addQueuePurchase");
        int quantity = 0;
        double minprice = 0.0;
        double maxprice = 0.0;
        int productid = 0;
        int placerid = 0;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProductToDB method, of class Grand_Exchange.
     */
    @Test
    public void testAddProductToDB() {
        System.out.println("addProductToDB");
        String name = "";
        String description = "";
        int gtin = 0;
        int expResult = 0;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAuctionToDB method, of class Grand_Exchange.
     */
    @Test
    public void testAddAuctionToDB() {
        System.out.println("addAuctionToDB");
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
        boolean expResult = false;
        boolean result = true;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeProduct method, of class Grand_Exchange.
     */
    @Test
    public void testRemoveProduct() {
        System.out.println("removeProduct");
        Product product = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Grand_Exchange.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "";
        String password = "";
        User expResult = null;
        User result = null;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleTransaction method, of class Grand_Exchange.
     */
    @Test
    public void testHandleTransaction() {
        System.out.println("handleTransaction");
        Transaction transaction = null;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducts method, of class Grand_Exchange.
     */
    @Test
    public void testGetProducts_0args() {
        System.out.println("getProducts");
        ArrayList<Product> expResult = null;
        ArrayList<Product> result =null;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducts method, of class Grand_Exchange.
     */
    @Test
    public void testGetProducts_String_CategoryEnum() {
        System.out.println("getProducts");
        String name = "";
        ArrayList<Product> expResult = null;
        ArrayList<Product> result = null;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuctions method, of class Grand_Exchange.
     */
    @Test
    public void testGetAuctions() throws Exception {
        System.out.println("getAuctions");
        Grand_Exchange instance = new Grand_Exchange();
        Collection<Auction> expResult = null;
        Collection<Auction> result = instance.getAuctions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuction method, of class Grand_Exchange.
     */
    @Test
    public void testGetAuction() {
        System.out.println("getAuction");
        int id = 0;
        Auction expResult = null;
        Auction result = null;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of InstabuyItem method, of class Grand_Exchange.
     */
    @Test
    public void testInstabuyItem() throws Exception {
        System.out.println("InstabuyItem");
        int amount = 0;
        int auctionID = 0;
        int buyerID = 0;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.InstabuyItem(amount, auctionID, buyerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateQueuePurchaseFromDB method, of class Grand_Exchange.
     */
    @Test
    public void testUpdateQueuePurchaseFromDB()throws Exception {
        System.out.println("updateQueuePurchaseFromDB");
        ArrayList<Integer> newQueuePurchases = null;
        Grand_Exchange instance = new Grand_Exchange();
        instance.updateQueuePurchaseFromDB(newQueuePurchases);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAuctionsFromDB method, of class Grand_Exchange.
     */
    @Test
    public void testUpdateAuctionsFromDB()throws Exception {
        System.out.println("updateAuctionsFromDB");
        ArrayList<Integer> newAuctionIDs = null;
        Grand_Exchange instance = new Grand_Exchange();
        instance.updateAuctionsFromDB(newAuctionIDs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Grand_Exchange.
     */
    @Test
    public void testUpdate()throws Exception {
        System.out.println("update");
        Observable o = null;
        Object arg = null;
        Grand_Exchange instance = new Grand_Exchange();
        instance.update(o, arg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAuction method, of class Grand_Exchange.
     */
    @Test
    public void testUpdateAuction()throws Exception {
        System.out.println("updateAuction");
        Auction auction = null;
        Grand_Exchange instance = new Grand_Exchange();
        instance.updateAuction(auction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class Grand_Exchange.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        String userName = "";
        Grand_Exchange instance = new Grand_Exchange();
        User expResult = null;
        User result = instance.getUser(userName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUsers method, of class Grand_Exchange.
     */
    @Test
    public void testUpdateUsers() throws Exception{
        System.out.println("updateUsers");
        Grand_Exchange instance = new Grand_Exchange();
        instance.updateUsers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBids method, of class Grand_Exchange.
     */
    @Test
    public void testGetBids()throws Exception {
        System.out.println("getBids");
        int auctionId = 0;
        Grand_Exchange instance = new Grand_Exchange();
        List<Bid> expResult = null;
        List<Bid> result = instance.getBids(auctionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFeedback method, of class Grand_Exchange.
     */
    @Test
    public void testAddFeedback() throws Exception {
        System.out.println("addFeedback");
        Feedback feedback = null;
        Grand_Exchange instance = new Grand_Exchange();
        instance.addFeedback(feedback);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitFeedbackToDB method, of class Grand_Exchange.
     */
    @Test
    public void testSubmitFeedbackToDB()throws Exception {
        System.out.println("submitFeedbackToDB");
        Feedback feedback = null;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.submitFeedbackToDB(feedback);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateFeedbacklist method, of class Grand_Exchange.
     */
    @Test
    public void testUpdateFeedbacklist()throws Exception {
        System.out.println("updateFeedbacklist");
        String username = "";
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.updateFeedbacklist(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Grand_Exchange.
     */
    @Test
    public void testRegisterUser()throws Exception {
        System.out.println("registerUser");
        String username = "";
        String password = "";
        String alias = "";
        String email = "";
        Grand_Exchange instance = new Grand_Exchange();
        String expResult = "";
        String result = instance.registerUser(username, password, alias, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createProduct method, of class Grand_Exchange.
     */
    @Test
    public void testCreateProduct() throws Exception {
        System.out.println("createProduct");
        int GTIN = 0;
        String name = "";
        String description = "";
        Grand_Exchange instance = new Grand_Exchange();
        int expResult = 0;
        int result = instance.createProduct(GTIN, name, description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createQueuePurchase method, of class Grand_Exchange.
     */
    @Test
    public void testCreateQueuePurchase() throws Exception {
        System.out.println("createQueuePurchase");
        int Quantity = 0;
        double minPrice = 0.0;
        double maxPrice = 0.0;
        int productID = 0;
        int placerID = 0;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.createQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeBid method, of class Grand_Exchange.
     */
    @Test
    public void testPlaceBid() throws Exception {
        System.out.println("placeBid");
        double amount = 0.0;
        String userName = "";
        int AuctionID = 0;
        double price = 0.0;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userName, AuctionID, price);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeBuy method, of class Grand_Exchange.
     */
    @Test
    public void testPlaceBuy() throws Exception {
        System.out.println("placeBuy");
        int amount = 0;
        String userName = "";
        int AuctionID = 0;
        double price = 0.0;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.placeBuy(amount, userName, AuctionID, price);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class Grand_Exchange.
     */
    @Test
    public void testLogout() throws Exception {
        System.out.println("logout");
        String username = "";
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.logout(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsAuthorized method, of class Grand_Exchange.
     */
    @Test
    public void testSetIsAuthorized() throws Exception {
        System.out.println("setIsAuthorized");
        String username = "";
        boolean isAuthorized = false;
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.setIsAuthorized(username, isAuthorized);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMail method, of class Grand_Exchange.
     */
    @Test
    public void testSendMail() throws Exception {
        System.out.println("sendMail");
        int senderId = 0;
        int receiverId = 0;
        String content = "";
        Grand_Exchange instance = new Grand_Exchange();
        instance.sendMail(senderId, receiverId, content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateAndSendEmail method, of class Grand_Exchange.
     */
    @Test
    public void testGenerateAndSendEmail() throws Exception {
        System.out.println("generateAndSendEmail");
        String senderUsername = "";
        String receiverEmail = "";
        String message = "";
        Grand_Exchange.generateAndSendEmail(senderUsername, receiverEmail, message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAuction method, of class Grand_Exchange.
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
        String description = "";
        Grand_Exchange instance = new Grand_Exchange();
        boolean expResult = false;
        boolean result = instance.addAuction(userID, productID, startingprice, instabuyPrice, instabuyable, quantity, iets, iets2, auctionType, iets3, imageUrl, description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
