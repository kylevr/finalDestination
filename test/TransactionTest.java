/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Auctions.Auction;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lesley
 */
public class TransactionTest {
   
    private Transaction testTransaction;
    private Auction testAuction;
   
    @Before
    public void setUp() {
        User testUser = new User("lesley","wachtwoord");
        Product testProduct = new Product(1, "123456789","FIFA 17","beschrijving");
        Date endDate = new Date(2017,3,29);
        testAuction = new Standard(testUser,testProduct,22.50,1,endDate,StatusEnum.GoodAsNew, "Hasn't been used got it double", "");
        testTransaction = new Transaction(testAuction);
    }
    
    @After
    public void tearDown() {
        testTransaction = null;
    }

    /**
     * Test of getAmount method, of class Transaction.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        Transaction instance = testTransaction;
        double expResult = 22.5;
        double result = instance.getAmount();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getAuction method, of class Transaction.
     */
    @Test
    public void testGetAuction() {
        System.out.println("getAuction");
        Transaction instance = testTransaction;
        Auction expResult = testAuction;
        Auction result = instance.getAuction();
        assertEquals(expResult, result);
    }
    
}
