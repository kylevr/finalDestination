/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.Product;
import Classes.User;
import java.util.Date;
import java.util.List;
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
public class CountdownTest {
    
    Auction auction;
    User user;
    Date date;
    Product product;
    Grand_Exchange gE;
    Bid bid;
    
    public CountdownTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new User("Kyle","PassWW");
        date = new Date();
        product = new Product("1932","Smalle suède riem ","Mooi en handig voor elke maat");
        auction = new Countdown(user,product,15,45,75,75,56,56,StatusEnum.New,"Testtestetestetets","");
        bid = new Bid(user, 15);
        
        // adds a bid to the auction
        auction.addBid(bid);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetBestBid() {
        Bid result = auction.getBestBid();
        Assert.assertNotNull("Bid is null", result);
    }

    /**
     * Test of getBids method, of class Auction. This test checks if the list is
     * not empty
     */
    @Test
    public void testGetBids() {
        List<Bid> bids = auction.getBids();
        if (bids.isEmpty()) {
            fail("The list is empty");
        }
    }

    /**
     * Test of sendMailRequest method, of class Auction.
     */
    @Test
    public void testSendMailRequest() {
        String email = "Kyle_v_r@hotmail.com";

        try {
            auction.sendMailRequest(email);
        } catch (Exception e) {
            fail("Method failed");
        }

    }
    
}
