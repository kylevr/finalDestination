/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
public class FeedbackTest {

    User fromUser;
    User toUser;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //gebruiker wachtwoord
        fromUser = new User("Kyle", "PassWW");
        toUser = new User("Pieter", "OMW2FYB");

    }

    @After
    public void tearDown() {
    }

    /**
     * This method is used to test the constructor of feedback.
     */
    @Test
    public void testConstructor() {
        Feedback result = new Feedback(fromUser.getUsername(), toUser.getUsername(), 4, "Goede deal gemaakt");
        Assert.assertNotNull("result is null", result);
    }

}
