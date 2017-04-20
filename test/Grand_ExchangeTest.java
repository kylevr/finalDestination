/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Auctions.Auction;
import Classes.Auctions.StatusEnum;
import java.util.Collection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robin
 */
public class Grand_ExchangeTest {

    User TestUser;
    Grand_Exchange GE;
    Product p;
    Auction a;
    Transaction trans;

    @Before
    public void setUp() {
        //gebruiker wachtwoord
        TestUser = new User("Robin", "test");

    }
/** 
   * A user will be added
   * the name can't be empty
   */
    @Test
    public void TestaddUser() {
        GE.addUser(TestUser);
        assertEquals(TestUser.getUsername(), "Robin");
    }
/** 
   * A user will be removed.
   * The name can't be empty
   */
    @Test
    public void TestremoveUser() {
        GE.addUser(TestUser);
        assertEquals(TestUser.getUsername(), "Robin");
        GE.removeUser(TestUser);
        assertNull(TestUser.getUsername());

    }
/** 
   * a product will be added.
   * 
   */
    @Test
    public void TestAddProduct() {
        p = new Product(1,"1234", "testprodcut", "gewoon een test");
        GE.addProduct(p);
        assertEquals(p.getName(), "testproduct");

    }
/** 
   * a product will be removed.
   *the product must exist.
   */
    @Test
    public void TestRemoveProduct() {
        p = new Product(1, "1234", "testprodcut", "gewoon een test");
        assertEquals(p.getName(), "testproduct");
        GE.removeProduct(p);
        assertNull(p.getName());

    }
/** 
   * a auction will be added.
   * 
   */
    @Test
    public void TestAddAuction() {
        p = new Product(1,"1234", "testprodcut", "gewoon een test");
        a = new Auction(TestUser, p, 15, 2, StatusEnum.New, "test",10);
        GE.addAuction(a);
        Collection<Auction> auc;
        auc = GE.getAuctions();
        assertEquals(auc.size(), 1);

    }
/** 
   * a auction will be removed.
   *
   */
    @Test
    public void TestRemoveAuction() {
        Collection<Auction> auc;
        auc = GE.getAuctions();
        a = new Auction(TestUser, p, 15, 2, goed, "test",10);
        GE.addAuction(a);
        assertEquals(auc.size(), 1);
        GE.removeAuction(a);
        assertEquals(auc.size(), 0);

    }
/** 
   * a transaction will be handled
   * 
   */
    @Test
    public void TesthandleTransaction() {
        trans = new Transaction(a);
        GE.handleTransaction(trans);
        Collection<Transaction> col = TestUser.getTransactionHistory();
        assertEquals(col.size(), 1);
    }

}
