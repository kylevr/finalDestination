/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Interfaces;

import Classes.Auctions.Auction;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.Product;
import Classes.User;
import Database.AuctionConnection;
import Database.UserConnection;
import Exceptions.NotEnoughMoneyException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class IPlaceBidTest {

    public IPlaceBidTest() {
    }

    /**
     * Test of succesfull use of the placeBid method, of class IPlaceBid.
     */
    @Test
    public void testPlaceBid() throws Exception {
        System.out.println("placeBid");
        double amount = 1;
        int userID = 1;
        int AuctionID = 1;
        double price = 55000;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = true;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of placeBid method with 0 or negative amount, of class IPlaceBid.
     */
    @Test
    public void testPlaceBidWithNegativeAmount() throws Exception {
        System.out.println("placeBid");
        double amount = 0;
        int userID = 1;
        int AuctionID = 1;
        double price = 56000;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of placeBid method with unexcisting user id, of class IPlaceBid.
     */
    @Test
    public void testPlaceBidWithWrongUserID() throws Exception {
        System.out.println("placeBid");
        double amount = 1;
        int userID = 1000000;
        int AuctionID = 1;
        double price = 57000;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of placeBid method with unexcisting auctionID, of class IPlaceBid.
     */
    @Test
    public void testPlaceBidWithWrongAuctionID() throws Exception {
        System.out.println("placeBid");
        double amount = 1;
        int userID = 1;
        int AuctionID = 1000000;
        double price = 58000;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of placeBid method with 0 or negative price, of class IPlaceBid.
     */
    @Test
    public void testPlaceBidWithNegativePrice() throws Exception {
        System.out.println("placeBid");
        double amount = 1;
        int userID = 1;
        int AuctionID = 1;
        double price = 0;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of placeBid method with price below highest bid, of class IPlaceBid.
     */
    @Test
    public void testPlaceBidWithPriceTooLow() throws Exception {
        System.out.println("placeBid");
        double amount = 1;
        int userID = 1;
        int AuctionID = 1;
        double price = 1;
        IPlaceBid instance = new IPlaceBidImpl();
        boolean expResult = false;
        boolean result = instance.placeBid(amount, userID, AuctionID, price);
        assertEquals(expResult, result);
    }

    public class IPlaceBidImpl implements IPlaceBid {

//        AuctionConnection auctionConn = new AuctionConnection();
//        UserConnection userConn = new UserConnection();
//
//        User testUser = new User(666, "test user", "wachtwoord", "test user", "test@test.test", true, 1000, "");
//        Product testProduct = new Product(666, "965855547", "test product", "test description");
//        Auction testAuction = new Auction(666, testUser, testProduct, 100, 1, StatusEnum.New, "test description", "", 250) {};
        
        
        //method copied from Grand_Exchange class
        public boolean placeBid(double amount, int userID, int AuctionID, double price) throws RemoteException, NotEnoughMoneyException {
            AuctionConnection auctionConn = new AuctionConnection();
            try {
                return auctionConn.addBid(amount, AuctionID, 1, price);
            } catch (SQLException ex) {
                Logger.getLogger(Grand_Exchange.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        
//
//        @Override
//        public boolean placeBid(double amount, String userName, int AuctionID, double price) throws RemoteException, NotEnoughMoneyException {
//            
//        User u = null;
//        u = userConn.getUser(userName);
//        System.out.println(userName);
//        System.out.println(u.getUserID());
//        System.out.println(price);
//        System.out.println(AuctionID);
//        testAuction.addBid(new Bid(AuctionID, u, price));
//        //return auctionConn.addBid(amount, AuctionID, userID, price);
//        return true;
//        }
//
//        @Override
//        public boolean placeBuy(int amount, String userName, int AuctionID, double price) throws RemoteException, NotEnoughMoneyException {
//            UserConnection DB = new UserConnection();
//        
//        User u = null;
//        u = DB.getUser(userName);
//        System.out.println(userName);
//        System.out.println(u.getUserID());
//        System.out.println(price);
//        System.out.println(AuctionID);
//        for(int i=0;i<amount;i++){
//        testAuction.addBid(new Bid(AuctionID, u, price));
//        }
//        int productQuantity = testAuction.getProductQuantity() - amount;
//        testAuction.setProductQuantity(productQuantity);
//        //return auctionConn.addBid(amount, AuctionID, userID, price);
//        return true;
//        }
        
        
    }
}
