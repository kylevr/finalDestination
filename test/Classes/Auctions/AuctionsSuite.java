/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Classes.Auctions;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author piete
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Classes.Auctions.AuctionTest.class, Classes.Auctions.DirectTest.class, Classes.Auctions.StandardTest.class, Classes.Auctions.CountdownTest.class, Classes.Auctions.StatusEnumTest.class})
public class AuctionsSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
