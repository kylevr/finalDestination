/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
public class ProductTest {
   
    private Product testProduct;
    
    @Before
    public void setUp() {
        testProduct = new Product(1,"5035223116370","FIFA 17 - Deluxe Edition - PS4", "De Deluxe Edtion met daarin 20 FIFA Ultimate Team Premium Jumbo Goud-pakketten en Team van de week FUT-leenspelers voor 3 wedstrijden. Dit is beide maximaal 1 gedurende 20 weken.");
    }
    
    @After
    public void tearDown() {
        testProduct = null;
    }

    /**
     * Test of getGTIN method, of class Product.
     */
    @Test
    public void testGetGTIN() {
        System.out.println("getGTIN");
        Product instance = testProduct;
        String expResult = "5035223116370";
        String result = instance.getGTIN();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Product.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Product instance = testProduct;
        String expResult = "FIFA 17 - Deluxe Edition - PS4";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Product.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Product instance = testProduct;
        String expResult = "De Deluxe Edtion met daarin 20 FIFA Ultimate Team Premium Jumbo Goud-pakketten en Team van de week FUT-leenspelers voor 3 wedstrijden. Dit is beide maximaal 1 gedurende 20 weken.";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }
    
}
