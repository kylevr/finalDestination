/*
 * This project is for PTS3 Fontys Eindhoven
 * Jorian Vas, Kyle van Raaij, Pieter Beukelman, Sam Dirkx, Lesley Peeters, Robin Welten
 * �2016-2017
 */
package Interfaces;

import Classes.User;
import Database.Connection;
import Database.UserConnection;
import grandexchange.RegistryManager;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
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
public class IAuthorizationTest {

    private User user;
    private RegistryManager RM;
    private IAuthorized authorization;

    public IAuthorizationTest() {
        RM = new RegistryManager();
        RM.getAuthorizationInterface();
        authorization = RM.getAuthorization();
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    Connection conn = new Connection();
    java.sql.Connection myConn = conn.getMyConn();
    PreparedStatement pstmt;
    ResultSet myRs;
    
    UserConnection userConn = new UserConnection();
    
    public void createSavePoint() throws SQLException
    {        
        myConn.setSavepoint();
    }
    
    public void rollBack(Savepoint svpnt) throws SQLException
    {
        myConn.rollback(svpnt);
    }

//    SAVEPOINT identifier
//ROLLBACK [WORK] TO [SAVEPOINT] identifier
//RELEASE SAVEPOINT identifier
    
    
    /**
     * logs a user in the application.
     * boolean isAuthorized of this user is set to true.
     * @param username
     * @param password
     * @return the user that will be logged into the application. Null if no user is found with given username and password.
     * @throws RemoteException
     */
    @Test
    public User login (String username, String password) throws RemoteException
    {
        //assert.assertNotNull(username, user);
        return null;
    }

    /**
     * logs user with specified username out of the application.
     * boolean isAuthorized of this user is set to false.
     * @param username
     * @return success of the operation
     * @throws RemoteException
     */
    public boolean logout(String username) throws RemoteException
    {
        return false;
    }

    /**
     * registers a new user to the application.
     * @param username
     * @param password
     * @param alias
     * @param email
     * @return "Failed to register user:: + (specific errormessages)" when failed, and "Succesfully registered new user!" if successful 
     * @throws RemoteException
     */
    public String registerUser(String username, String password, String alias, String email) throws RemoteException
    {
        return "";
    }

    /**
     * sets if user with specified username will be authorized or not
     * @param username username of user you want to (de)authorize
     * @param isAuthorized 
     * @return
     * @throws RemoteException
     */
    public boolean setIsAuthorized(String username, boolean isAuthorized) throws RemoteException
    {
        return false;
    }
    
    
    /**
     * Get the current logged in user.
     * @return User that is logged in at this moment
     * @throws RemoteException 
     */
    public User getLoggedInUser() throws RemoteException
    {
        return null;
    }
}
