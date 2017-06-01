/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

import Classes.Auctions.Auction;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import Exceptions.EmptyFieldException;
import java.sql.Timestamp;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lesley Peters
 */
public class UserTest {
    
    User testInstance;
    Product testProduct;
    Timestamp beginTime;
    Timestamp endTime;
    Auction testAuction;
    
    public UserTest() {
   
    }
    
    @Before
    public void setUp() {         
        testInstance = new User(1, "test user", "wachtwoord", "test user", "test@gmail.com", false, 200.85, "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        testProduct = new Product(1, "824659214", "test product", "test description");
        beginTime = new Timestamp(2017, 5, 30, 12, 0, 0, 0);
        endTime = new Timestamp(2017, 6, 1, 12, 0, 0, 0);
        testAuction = new Standard(testInstance, testProduct, 5, 100, 150, beginTime, endTime, StatusEnum.New, "test auction description", "");
    }
    
    @After
    public void tearDown() {
        testInstance = null;
    }

    /**
     * Test of setIsAuthorized method, of class User.
     */
    @Test
    public void testSetIsAuthorized() {
        System.out.println("setIsAuthorized");
        boolean isAuthorized = true;
        User instance = testInstance;
        instance.setIsAuthorized(isAuthorized);
        boolean expResult = true;
        boolean result = instance.getIsAuthorized();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getIsAuthorized method, of class User.
     */
    @Test
    public void testGetIsAuthorizedFalse() {
        System.out.println("getIsAuthorized");
        User instance = testInstance;
        boolean expResult = false;
        boolean result = instance.getIsAuthorized();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getIsAuthorized method, of class User.
     */
    @Test
    public void testGetIsAuthorizedTrue() {
        System.out.println("getIsAuthorized");
        User instance = testInstance;
        instance.setIsAuthorized(true);
        boolean expResult = true;
        boolean result = instance.getIsAuthorized();
        assertEquals(expResult, result);
    }

    

    /**
     * Test of getUserID method, of class User.
     */
    @Test
    public void testGetUserID() {
        System.out.println("getUserID");
        User instance = testInstance;
        int expResult = 1;
        int result = instance.getUserID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUserEmail method, of class User.
     */
    @Test
    public void testGetUserEmail() {
        System.out.println("getUserEmail");
        User instance = testInstance;
        String expResult = "test@gmail.com";
        String result = instance.getUserEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of sendFeedback method, of class User.
     */
    @Test
    public void testSendFeedback() throws Exception {
        System.out.println("sendFeedback");
        String receiver = "lesleyy";
        int rating = 5;
        String description = "test feedback";
        User instance = testInstance;
        boolean expResult = true;
        boolean result = instance.sendFeedback(receiver, rating, description);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendFeedback method, of class User.
     */
    @Test(expected=EmptyFieldException.class)
    public void testSendFeedbackWithoutReceiver() throws Exception {
        System.out.println("sendFeedback");
        String receiver = "";
        int rating = 5;
        String description = "test feedback";
        User instance = testInstance;
        instance.sendFeedback(receiver, rating, description);
    }
    
    /**
     * Test of sendFeedback method, of class User.
     */
    @Test(expected=EmptyFieldException.class)
    public void testSendFeedbackWithoutDescription() throws Exception {
        System.out.println("sendFeedback");
        String receiver = "lesleyy";
        int rating = 5;
        String description = "";
        User instance = testInstance;
        instance.sendFeedback(receiver, rating, description);
    }

    /**
     * Test of addAuction method, of class User.
     */
    @Test
    public void testAddAuction() {
        System.out.println("addAuction");
        Auction auction = testAuction;
        User instance = testInstance;
        instance.addAuction(auction);
        int expResult = 1;
        int result = instance.getAuctions().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeAuction method, of class User.
     */
    @Test
    public void testRemoveAuction() {
        System.out.println("removeAuction");
        Auction auction = testAuction;
        User instance = testInstance;
        instance.addAuction(auction);
        instance.removeAuction(auction);
        int expResult = 0;
        int result = instance.getAuctions().size();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getSaldo method, of class User.
     */
    @Test
    public void testGetSaldo() {
        System.out.println("getSaldo");
        User instance = testInstance;
        double expResult = 200.85;
        double result = instance.getSaldo();
        assertEquals(expResult, result, 0.0);
    }
    
    /**
     * Test of addSaldo method, of class User.
     */
    @Test
    public void testAddSaldo() throws Exception {
        System.out.println("addSaldo");
        double amount = 50.33;
        User instance = testInstance;
        instance.addSaldo(amount);
        double expResult = 251.18;
        double result = instance.getSaldo();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of addQueuePurchase method, of class User.
     */
    @Test
    public void testAddQueuePurchase() {
        System.out.println("addQueuePurchase");
        Queue_Purchase purchase = new Queue_Purchase(1, 10, 100, 1, testInstance.getUserID());
        User instance = testInstance;
        instance.addQueuePurchase(purchase);
        int expResult = 1;
        int result = instance.getQueuePurchase().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeQueuePurchase method, of class User.
     */
    @Test
    public void testRemoveQueuePurchase() {
        System.out.println("removeQueuePurchase");
        Queue_Purchase purchase = new Queue_Purchase(1, 10, 100, 1, testInstance.getUserID());
        User instance = testInstance;
        instance.addQueuePurchase(purchase);
        instance.removeQueuePurchase(purchase);
        int expResult = 0;
        int result = instance.getQueuePurchase().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of requestVerification method, of class User.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRequestVerification() {
        System.out.println("requestVerification");
        User instance = testInstance;
        instance.requestVerification();
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = testInstance;
        String expResult = "test user";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getImageURL method, of class User.
     */
    @Test
    public void testGetImageURL() {
        System.out.println("getImageURL");
        User instance = testInstance;
        String expResult = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";
        String result = instance.getImageURL();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFeedback method, of class User.
     */
    @Test
    public void testAddFeedback() {
        System.out.println("addFeedback");
        Feedback feedback = new Feedback("lesleyy", "test user", 5, "test feedback");
        User instance = testInstance;
        instance.addFeedback(feedback);
        int expResult = 1;
        int result = instance.getFeedbackToMe().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFeedback method, of class User.
     */
    @Test
    public void testRemoveFeedback() {
        System.out.println("addFeedback");
        Feedback feedback = new Feedback("lesleyy", "test user", 5, "test feedback");
        User instance = testInstance;
        instance.addFeedback(feedback);
        instance.removeFeedback(feedback);
        int expResult = 0;
        int result = instance.getFeedbackToMe().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeAllFeedback method, of class User.
     */
    @Test
    public void testRemoveAllFeedback() {
        System.out.println("addFeedback");
        Feedback feedback1 = new Feedback("lesleyy", "test user", 5, "test feedback");
        Feedback feedback2 = new Feedback("lesleyy", "test user", 5, "test feedback");
        User instance = testInstance;
        instance.addFeedback(feedback1);
        instance.addFeedback(feedback2);
        instance.removeAllFeedback();
        int expResult = 0;
        int result = instance.getFeedbackToMe().size();
        assertEquals(expResult, result);
    }
    
}
