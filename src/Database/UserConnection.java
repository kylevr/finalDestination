/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Feedback;
import Classes.Product;
import Classes.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyle_
 */
public class UserConnection {
    private java.sql.Connection myConn = null;
    private PreparedStatement pstmt = null;
    private ResultSet myRs = null;

    // Connections
    //private UserConnection userConn = new UserConnection();
    private Connection conn = new Connection();
    //private ProductConnection productConn = new ProductConnection();

    // SQL codes
    static final String GET_FROM_USER_BYLOGININFO = "SELECT * FROM user WHERE BINARY username = ? and BINARY password = ?";
    static final String GET_FROM_USER_BYUSERNAME = "SELECT * FROM user WHERE BINARY username = ?";
    static final String GET_FROM_USER_ID = "SELECT * FROM user WHERE id = ?";
    static final String SET_USER_NEW = "INSERT INTO user(username, password, alias, email, verified, imageURL, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    static final String GET_FROM_FEEDBACK_TOSELLER = "SELECT * FROM feedback WHERE sellerid = ?";
    static final String GET_FROM_FEEDBACK_FROMBUYER = "SELECT * FROM feedback WHERE buyerid = ?";
    static final String REMOVE_USER_BYUSERNAME = "DELETE FROM user WHERE BINARY username = ?";
    static final String GET_FROM_USER_ALLUSERS = "SELECT * FROM user";
    static final String SET_FEEDBACK_TOSELLER = "INSERT INTO feedback(timeCreated, rating, description, sellerid, buyerid) VALUES(CURRENT_TIMESTAMP, ?, ?, ?, ?)";
    static final String SET_ISAUTHORIZED = "UPDATE user SET isAuthorized = ? WHERE username = ?";
    static final String GET_HASBOUGHT_FROM_SELLER = "SELECT * from transaction t, auction a where a.id = t.auctionID and a.buyerID = ? and t.sellerID = ?;";

    //alter table chatuser  add column isAuthorized bool;
    
    
    /**
     * Registers a new user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param alias The alias of the user.
     * @param email The email of the user.
     * @param imageUrl The url of the image of the user.
     * @param saldo The saldo of the user.
     * @return True if user is successfully registered, failed if not.
     */
    public Boolean setUser_REGISTER(String username, String password, String alias, String email, String imageUrl, double saldo) {
        conn.getConnection();
        myConn = conn.getMyConn();
        
        if (myConn != null) {
            if (getUser(username, password) == null) {
                try {
                    conn.getConnection();
                    myConn = conn.getMyConn();
                    boolean verified = false;
                    pstmt = myConn.prepareStatement(SET_USER_NEW);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.setString(3, alias);
                    pstmt.setString(4, email);
                    pstmt.setBoolean(5, verified);
                    pstmt.setString(6, imageUrl);
                    pstmt.setDouble(7, saldo);

                    if (pstmt.executeUpdate() > 0) {
                        System.out.println("succesfully registered new user with username: " + username);
                        return true;
                    } else {
                        System.out.println("Couldn't insert new user. Rows are unaffected.");
                        return false;
                    }
                } catch (SQLException ex) {
                    System.out.println("failed to register new user. SQLException");
                    ex.printStackTrace();
                    conn.closeConnection();
                    return false;
                }
            } else {
                System.out.println("Registration of duplicate user isn't allowed.");
                return false;
            }
        } else {
            System.out.println("failed to register new user. No connection to database.");
            return false;
        }
    }

    /**
     * Gets a user with a specific id.
     *
     * @param id The id of the user.
     * @return User with the given id.
     */
    public User getUser(int id) {
        User user = null;
        int userID;
        //removed  int bsn;
        String username;
        String password;
        String alias;
        String email;
        boolean verified;
        float saldo;

        PreparedStatement preparedStatement;
        ResultSet resultset = null;
        if (myConn != null) {
            try {
                preparedStatement = myConn.prepareStatement(GET_FROM_USER_ID);
                preparedStatement.setInt(1, id);
                resultset = preparedStatement.executeQuery();
                preparedStatement.close();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                userID = resultset.getInt("id");
                username = resultset.getString("username");
                password = resultset.getString("password");
                alias = resultset.getString("alias");
                email = resultset.getString("email");
                verified = resultset.getBoolean("verified");
                saldo = resultset.getFloat("saldo");
                String imgURL = resultset.getString("imageUrl");

                user = new User(userID, username, password, alias, email, verified, saldo, imgURL);

                return user;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("There is no existing connection");
        }
        return user;
    }

    /**
     * Get a user from the database.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return User with the given username and password
     */
    public User getUser(String username, String password) {
        User user = null;
        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement(GET_FROM_USER_BYLOGININFO);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            int userID = myRs.getInt("id");
            //removed  int bsn = myRs.getInt("bsn");
            String usernm = myRs.getString("username");
            String pass = myRs.getString("password");
            String alias = myRs.getString("alias");
            String email = myRs.getString("email");
            boolean verified = myRs.getBoolean("verified");
            double saldo = myRs.getDouble("saldo");
            String imgURL = myRs.getString("imageUrl");

            user = new User(userID, usernm, pass, alias, email, verified, saldo, imgURL);
            conn.closeConnection();
        } catch (SQLException ex) {
            System.out.println("User not found");
            conn.closeConnection();
        }
        return user;
    }

    /**
     * Get a user with the given username
     *
     * @param username The username of the user.
     * @return a user with the given username.
     */
    public User getUser(String username) {
        User user = null;

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement(GET_FROM_USER_BYUSERNAME);
            pstmt.setString(1, username);

            myRs = pstmt.executeQuery();
            myRs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            int userID = myRs.getInt("id");
            //removed  int bsn = myRs.getInt("bsn");
            String usernm = myRs.getString("username");
            String pass = myRs.getString("password");
            String alias = myRs.getString("alias");
            String email = myRs.getString("email");
            boolean verified = myRs.getBoolean("verified");
            double saldo = myRs.getDouble("saldo");
            String imgURL = myRs.getString("imageUrl");            

            user = new User(userID, usernm, pass, alias, email, verified, saldo, imgURL);
            conn.closeConnection();
        } catch (SQLException ex) {
            System.out.println("User not found");
            conn.closeConnection();
        }

        return user;
    }
    
    /**
     * Gets all the users in the database.
     *
     * @return all users
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String username;
        String password;
        String alias;
        String email;
        boolean verified;
        float saldo;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

        if (myConn != null) {

            try {
                conn.getConnection();
                myConn = conn.getMyConn();
                myConn = conn.getMyConn();
                preparedStatement = myConn.prepareStatement(GET_FROM_USER_ALLUSERS);
                resultset = preparedStatement.executeQuery();

                while (resultset.next()) {
                    username = resultset.getString("username");
                    password = resultset.getString("password");
                    alias = resultset.getString("alias");
                    email = resultset.getString("email");
                    verified = resultset.getBoolean("verified");
                    saldo = resultset.getFloat("saldo");
                    String imgURL = resultset.getString("imageUrl");

                    User foundUser = new User(username, password, alias, email, verified, saldo, imgURL);
                    users.add(foundUser);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("There is no existing connection");
        }
        return users;
    }

    /**
     * Checks if the username is already known in the database.
     *
     * @param checkValue Value to check if the username is double
     * @return True if username is a duplicate, false if not.
     */
    public boolean hasDuplicateUsername(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE username = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String username = myRs.getString("username");
            conn.closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            conn.closeConnection();
        }

        return hasDuplicate;
    }

    /**
     * Checks if the email is already known in the database.
     *
     * @param checkValue Value to check if the email is double
     * @return True if email is a duplicate, false if not.
     */
    public boolean hasDuplicateEmail(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE email = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String email = myRs.getString("email");
            conn.closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            conn.closeConnection();
        }

        return hasDuplicate;
    }

    /**
     * Checks if the alias is already known in the database.
     *
     * @param checkValue Value to check if the alias is double
     * @return True if alias is a duplicate, false if not.
     */
    public boolean hasDuplicateAlias(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE alias = ?");
            pstmt.setString(1, checkValue);
            myRs = pstmt.executeQuery();

            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasDuplicate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String alias = myRs.getString("alias");
            conn.closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            conn.closeConnection();
        }

        return hasDuplicate;
    }

    /**
     * removes a user with given username note: doesn't delete any objects yet
     * that the user created (e.g. auctions, bids, feedbacks)
     *
     * @param username
     * @return
     */
    public Boolean removeUser_BYUSERNAME(String username) {
        conn.getConnection();
        myConn = conn.getMyConn();

        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(REMOVE_USER_BYUSERNAME);
                pstmt.setString(1, username);

                if (pstmt.executeUpdate() > 0) {
                    System.out.println("succesfully deleted user with username: " + username);
                    return true;
                } else {
                    System.out.println("Couldn't delete user because User with username: " + username + " doesn't exist in the database");
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("failed to remove with username: " + username + ". SQLException");
                ex.printStackTrace();
                conn.closeConnection();
                return false;
            }
        } else {
            System.out.println("failed to remove user with username: " + username + ". No connection to database.");
            return false;
        }
    }

    /**
     * gets a list with all the feedback the seller from a auction has gotten.
     *
     * @param sellerid The id of the seller
     * @return a List with the feedback from an user.
     */
    public List<Feedback> getFeedbackToSeller(String sellerid) {
        List<Feedback> feedbackToSeller = new ArrayList<Feedback>();

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement(GET_FROM_FEEDBACK_TOSELLER);
            pstmt.setString(1, sellerid);
            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {
                String sellerUsername = myRs.getString("sellerid");
                String buyerUsername = myRs.getString("buyerid"); //in tabel user is id nu varchar(20) ipv int. met username wordt nu het id ingevuld
                int rating = myRs.getInt("rating");
                String description = myRs.getString("description");

                //gets date from 
                Date timeCreated = null;
                timeCreated = myRs.getDate("timeCreated");
                Timestamp timestamp = myRs.getTimestamp("timeCreated");
                if (timestamp != null) {
                    timeCreated = new Date(timestamp.getTime());
                }

                Feedback f = new Feedback(timeCreated, buyerUsername, sellerUsername, rating, description);
                feedbackToSeller.add(f);
            }
            conn.closeConnection();
        } catch (SQLException ex) {
            System.out.println("SQLException at getFeedbackToSeller");
            ex.printStackTrace();
            conn.closeConnection();
        }

        return feedbackToSeller;
    }

    /**
     * gets a list with all the feedback the buyer from a auction has gotten.
     *
     * @param buyerid The id of the buyer
     * @return a List with the feedback from an user.
     */
    public List<Feedback> getFeedbackFromBuyer(String buyerid) {
        List<Feedback> feedbackFromBuyer = new ArrayList<Feedback>();

        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement(GET_FROM_FEEDBACK_FROMBUYER);
            pstmt.setString(1, buyerid);
            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {
                String sellerUsername = myRs.getString("sellerid");
                String buyerUsername = myRs.getString("buyerid"); //in tabel user is id nu varchar(20) ipv int. met username wordt nu het id ingevuld
                int rating = myRs.getInt("rating");
                String description = myRs.getString("description");

                //gets date from 
                Date timeCreated = null;
                timeCreated = myRs.getDate("timeCreated");
                Timestamp timestamp = myRs.getTimestamp("timeCreated");
                if (timestamp != null) {
                    timeCreated = new Date(timestamp.getTime());
                }

                Feedback f = new Feedback(timeCreated, buyerUsername, sellerUsername, rating, description);
                feedbackFromBuyer.add(f);
            }
            conn.closeConnection();
        } catch (SQLException ex) {
            System.out.println("SQLException at getFeedbackToSeller");
            ex.printStackTrace();
            conn.closeConnection();
        }
        return feedbackFromBuyer;
    }

    
    /**
     * checks if user with buyerid has allready bought something from user with sellerid
     * @param buyer_ID
     * @param seller_ID
     * @return true if that's correct, otherwise false
     */
    public Boolean hasBought_FromSeller(String buyer_ID, String seller_ID) {
        Boolean hasBought = false;
        int count = 0;
        
        try {
            conn.getConnection();
            myConn = conn.getMyConn();
            pstmt = myConn.prepareStatement(GET_HASBOUGHT_FROM_SELLER);
            pstmt.setString(1, buyer_ID);
            pstmt.setString(2, seller_ID);
            myRs = pstmt.executeQuery();
            
            while (myRs.next()) {
                ++count;
            }
            if (count > 0) {
                hasBought = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasBought;
    }

    
    /**
     * Submnits feedback to a buyer.
     *
     * @param rating Rating of the feedback.
     * @param description Description of the feedback.
     * @param sellerid The id of the seller.
     * @param buyerid The id of the buyer.
     * @return True if feedback successfully is submited, false if its not.
     */
    public Boolean submitFeedback(int rating, String description, String sellerid, String buyerid) {
        conn.getConnection();
        myConn = conn.getMyConn();

        if (myConn != null && this.hasBought_FromSeller(buyerid, sellerid)) {
            try {               
                pstmt = myConn.prepareStatement(SET_FEEDBACK_TOSELLER);
                pstmt.setInt(1, rating);
                pstmt.setString(2, description);
                pstmt.setString(3, sellerid);
                pstmt.setString(4, buyerid);

                if (pstmt.executeUpdate() > 0) {
                    System.out.println("succesfully submitted new feedback from " + buyerid + " to " + sellerid);
                    return true;
                } else {
                    System.out.println("Couldn't insert new feedback from " + buyerid + " to " + sellerid + ". Rows are unaffected.");
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("failed to submit new feedback from " + buyerid + " to " + sellerid + ". SQLException");
                ex.printStackTrace();
                conn.closeConnection();
                return false;
            }
        } else {
            System.out.println("Registration of duplicate user isn't allowed.");
            return false;
        }
    }
    
    /**
     * sets if user with specified username will be authorized or not
     * @param username username of user you want to (de)authorize
     * @param isAuthorized 
     * @return
     * @throws RemoteException
     */
    public Boolean setAuthorized(String username, boolean isAuthorized) {
        conn.getConnection();
        myConn = conn.getMyConn();
        if (myConn != null) {
            if (getUser(username) != null) {
                try {
                    conn.getConnection();
                    myConn = conn.getMyConn();
                    pstmt = myConn.prepareStatement(SET_ISAUTHORIZED);
                    pstmt.setString(1, username);
                    pstmt.setBoolean(2, isAuthorized);

                    if (pstmt.executeUpdate() > 0) {
                        System.out.println("User with username: " + username + " is now authorized");
                        return true;
                    } else {
                        System.out.println("Can't authorize User with username: " + username + ". Rows are unaffected.");
                        return false;
                    }
                } catch (SQLException ex) {
                    System.out.println("Can't authorize User with username: " + username + ". SQLException.");
                    ex.printStackTrace();
                    conn.closeConnection();
                    return false;
                }
            } else {
                System.out.println("Can't find User with username: " + username);
                return false;
            }
        } else {
            System.out.println("Can't authorize User with username: " + username + ". No connection to database.");
            return false;
        }
    }

}
