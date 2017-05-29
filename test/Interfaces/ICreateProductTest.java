/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Interfaces;

import Classes.CategoryEnum;
import Classes.Grand_Exchange;
import Classes.Product;
import Database.ProductConnection;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
public class ICreateProductTest {
    
    ICreateProduct instance;
    
    public ICreateProductTest() {
        instance = new ICreateProductImpl();
    }

    /**
     * Test of succesfull use of the createProduct method, of class ICreateProduct.
     */
    @Test
    public void testCreateProduct() throws Exception {
        System.out.println("createProduct");
        int random = (int )(Math.random() * 100000000 + 899999999);
        int GTIN = random;
        String name = "Test product";
        String description = "This product is for unittests";
        boolean result = false;
        if(instance.createProduct(GTIN, name, description) != 0){
            result = true;
        }
        assertTrue(result);
    }
    
    /**
     * Test of createProduct method with an empty name value, of class ICreateProduct.
     */
    @Test
    public void testCreateProductWithEmptyName() throws Exception {
        System.out.println("createProduct");
        int random = (int )(Math.random() * 100000000 + 899999999);
        int GTIN = random;
        String name = "";
        String description = "This product is for unittests";
        boolean result = false;
        if(instance.createProduct(GTIN, name, description) != 0){
            result = true;
        }
        assertFalse(result);
    }

    /**
     * Test of getProducts method, of class ICreateProduct.
     */
    @Test
    public void testGetProducts() throws Exception {
        System.out.println("getProducts");
        String productName = "Test product";
        CategoryEnum type = CategoryEnum.Electronics;
        boolean result = false;
        if(instance.getProducts(productName, type).size()>0){
            result = true;
        }
        assertTrue(result);
    }

    public class ICreateProductImpl implements ICreateProduct {

        ArrayList<Product> products;

        private ICreateProductImpl() {
            products = new ArrayList<>();
        }
        
        //Kopie van de createProduct methode in de Grand_Exchange klassen
        public int createProduct(int GTIN, String name, String description) throws RemoteException {
            ProductConnection productConn = new ProductConnection();
            int newProductID = productConn.insertProduct(name, description, GTIN);
            productConn = new ProductConnection();
            if(newProductID != 0){
            Product newProduct = productConn.getProduct(newProductID);
            products.add(newProduct);
            }
            return newProductID;
        }

        public ArrayList<Product> getProducts(String productName, CategoryEnum type) throws RemoteException {
            Product product = null;
            product = new Product(1,"589651475","test product","test description");
            products.add(product);
            return products;
        }
    }

    
    
}
