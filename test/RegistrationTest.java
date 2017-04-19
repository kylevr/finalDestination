/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Controllers.RegistrationController;
import Database.Connection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gebruiker
 */
public class RegistrationTest {

    public RegistrationTest() {
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
     * Test of initialize method, of class RegistrationController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        RegistrationController instance = new RegistrationController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of connection.
     */
    @Test
    public void testConnection() throws Exception {
        System.out.println("connection test");
        Connection conn = new Connection();
        conn.getConnection();
    }

    /**
     * Test of button_goBack method, of class RegistrationController.
     */
    @Test
    public void testButton_goBack() throws Exception {
        System.out.println("button_goBack");
        RegistrationController instance = new RegistrationController();
        instance.button_goBack();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test registration method
     * removes user afterwards
     */
    @Test
    public void Register_User() throws Exception {
        System.out.println("test_registration_user");

        Database.Connection conn = new Database.Connection();
       // String bsnString = "1234";
        String username = "test1234";
        String password = "password";
        String email = "test1234@email.com";
        String alias = "xTest1234";

        
        assertTrue(conn.setUser_REGISTER(username, password, alias, email, null, 0));
        assertTrue(conn.removeUser_BYUSERNAME("test1234"));
    }
    
    /**
     * Test if registration method can insert a duplicate user into database
     * removes user afterwards
     */
    @Test
    public void Register_DuplicateUser() throws Exception {
        System.out.println("test_registration_duplicate_user");
        
        Database.Connection conn = new Database.Connection();
      
        String username = "test2345";
        String password = "password";
        String email = "test2345@email.com";
        String alias = "xTest2345";

        
        assertTrue(conn.setUser_REGISTER( username, password, alias, email, null, 0));

       
        username = "test2345";
        password = "password";
        email = "test2345@email.com";
        alias = "xTest2345";

       
        assertFalse(conn.setUser_REGISTER(username, password, alias, email, null, 0));
        assertTrue(conn.removeUser_BYUSERNAME("test2345"));
    }
}
