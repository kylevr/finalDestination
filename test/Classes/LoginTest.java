package Classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controllers.LoginController;
import Controllers.MainController;
import Database.Connection;
import grandexchange.GrandExchange;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
public class LoginTest {
    
    public LoginTest() {
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
     * Test of initialize method, of class LoginController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        LoginController instance = new LoginController();
        instance.initialize(url, rb);
    }

    /**
     * Tests if user can log in and if it has the correct user.
     */
    @Test
    public void loginUser() throws Exception {
        System.out.println("button_loginUser");
        Grand_Exchange GX = new Grand_Exchange();
        assertTrue(GX.login("lesley", "wachtwoord"));
        assertEquals("lesley", GX.loggedInUser.getUsername());
    }

    /**
     * Tests if user can log out after logging in.
     */
    @Test
    public void logoutUser() throws Exception {
        System.out.println("button_loginUser");
        Grand_Exchange GX = new Grand_Exchange();
        assertTrue(GX.login("lesley", "wachtwoord"));
        assertEquals("lesley", GX.loggedInUser.getUsername());
        GX.logout();
        assertNull(GX.loggedInUser.getUsername());
    }
    
}
