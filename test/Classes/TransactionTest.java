/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes;

import Classes.Auctions.Auction;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
public class TransactionTest {
    
    Transaction testInstance;
    Auction testAuction;
    User testSeller;
    User testBuyer1;
    User testBuyer2;
    Product testProduct;
    
    public TransactionTest() {
    }
    
    @Before
    public void setUp() throws ParseException {
        this.testSeller = new User(1,"testuser", "password", "testalias", "test@mail.nl", true, 1500, "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        this.testBuyer1 = new User(1,"testbuyer1", "password", "testalias", "test@mail.nl", true, 1500, "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        this.testBuyer2 = new User(1,"testbuyer2", "password", "testalias", "test@mail.nl", true, 1500, "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        this.testProduct = new Product(1, "501368428", "test product", "test product beschrijving");
        Calendar calendar = Calendar.getInstance();
        Date now =  calendar.getTime();
        Timestamp beginTijd = new Timestamp(now.getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) dateFormat.parse("25/08/2017");
        long time = date.getTime();
        Timestamp eindTijd = new Timestamp(time);
        this.testAuction = new Standard(1,testSeller,testProduct,100,20,beginTijd,eindTijd,StatusEnum.New, "test auction description", "imgurl", 250);
        Bid bid1 = new Bid(1,testBuyer1,180);
        testAuction.addBid(bid1);
        Bid bid2 = new Bid(1,testBuyer2,200);
        testAuction.addBid(bid2);
        this.testInstance = new Transaction(testAuction);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAmount method, of class Transaction.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        Transaction instance = testInstance;
        double expResult = 200;
        double result = instance.getAmount();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getAuction method, of class Transaction.
     */
    @Test
    public void testGetAuction() {
        System.out.println("getAuction");
        Transaction instance = testInstance;
        Auction expResult = testAuction;
        Auction result = instance.getAuction();
        assertEquals(expResult, result);
    }
    
}
