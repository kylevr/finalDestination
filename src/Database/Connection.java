/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import Classes.Feedback;
import Classes.Product;
import Classes.Queue_Purchase;
import Classes.User;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorian
 */
public class Connection {

    private java.sql.Connection myConn = null;
    private PreparedStatement pstmt = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    private Auction auction;
    ArrayList<Auction> auctions;
    ArrayList<Product> products;

    static final String GET_FROM_AUCTIONS_SQL = "SELECT ? FROM auction WHERE ? = ?";
    static final String GET_FROM_AUCTIONS = "SELECT * FROM auction";
    static final String GET_FROM_USER_ID = "SELECT * FROM user WHERE id = ?";
    static final String GET_FROM_USER_BYLOGININFO = "SELECT * FROM user WHERE BINARY username = ? and BINARY password = ?";
    static final String GET_FROM_USER_BYUSERNAME = "SELECT * FROM user WHERE BINARY username = ?";
    static final String GET_FROM_PRODUCT = "SELECT * FROM product WHERE id = ?";
    static final String SET_USER_NEW = "INSERT INTO user(bsn, username, password, alias, email, verified, imageURL, saldo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    static final String REMOVE_USER_BYBSN = "DELETE FROM user WHERE bsn = ?";
    static final String REMOVE_USER_BYUSERNAME = "DELETE FROM user WHERE BINARY username = ?";
    static final String GET_AUCTION_BY_ID = "SELECT * FROM auction WHERE id = ?";
    static final String GET_FROM_PRODUCTS = "SELECT * FROM product";
    static final String SET_QUEUEPURCHASE_NEW = "INSERT INTO queuepurchase(quantity, minprice, maxprice, productid, placerID) VALUES (?,?,?,?,?)";
    static final String GET_QUEUEPURCHASE = "SELECT * FROM queuepurchase WHERE id = ?";
    static final String GET_ALL_QUEUEPURCHASE = "SELECT * FROM queuepurchase";
    static final String DELETE_QUEUEPURCHASE = "DELETE FROM queuepurchase WHERE id = ?";
    static final String GET_FROM_FEEDBACK_TOSELLER = "SELECT * FROM feedback WHERE sellerid = ?";
    static final String GET_FROM_FEEDBACK_FROMBUYER = "SELECT * FROM feedback WHERE buyerid = ?";
    static final String SET_FEEDBACK_TOSELLER = "INSERT INTO feedback(timeCreated, rating, description, sellerid, buyerid) VALUES(CURRENT_TIMESTAMP, ?, ?, ?, ?)";
    static final String GET_FROM_USER_ALLUSERS = "SELECT * FROM user";
    
    public Connection() {

    }

    public boolean getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?zeroDateTimeBehavior=convertToNull", "lesleya213_pts", "wachtwoord123");
            System.out.println("started connection to database...");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to start connection to database...");
            return false;
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public Auction getAuction(int id) {
        User user;
        Product product;
        int quantity;
        Timestamp date;
        double price;
        double instabuyprice;
        double priceloweringAmount;
        double priceloweringDelay;
        double minprice;
        StatusEnum status;
        String description;
        String imageURL;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_AUCTION_BY_ID);
            pstmt.setInt(1, id);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            myRs.next();
                if (myRs.getString("type").equals("countdown")) {
                    id = myRs.getInt("id");
                    user = getUser((myRs.getInt("sellerID")));
                    product = getProduct(myRs.getInt("productID"));
                    quantity = myRs.getInt("productquantity");
                    price = myRs.getDouble("currentprice");
                    priceloweringAmount = myRs.getDouble("priceloweringAmount");
                    priceloweringDelay = myRs.getDouble("priceloweringdelay");
                    minprice = myRs.getDouble("minPrice");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    date = myRs.getTimestamp("timecreated");
                    
                    auction = new Countdown(id, user, product, quantity, price, priceloweringAmount, priceloweringDelay, minprice, status, description, imageURL, instabuyprice, date);
                }

                // In case of Direct 
                if (myRs.getString("type").equals("direct")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Direct(id, user, product, price, begin, quantity, status, description, imageURL, instabuyprice);
                }

                //In case of standard auction
                if (myRs.getString("type").equals("standard")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    date = myRs.getTimestamp("timeend");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Standard(id, user, product, price, quantity, begin, date, status, description, imageURL, instabuyprice);
                }
                return auction;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to get auction by ID");
        }

        closeConnection();
        return null;
    }
    
    /**
     *
     * @param selectFrom
     * @param where
     * @param is
     * @return
     */
    public ArrayList<Auction> getAuctions(String selectFrom, String where, String is) {

        auctions = new ArrayList<Auction>() {};
        int id;
        User user;
        Product product;
        int quantity;
        Timestamp date;
        double price;
        double instabuyprice;
        double priceloweringAmount;
        double priceloweringDelay;
        double minprice;
        StatusEnum status;
        String description;
        String imageURL;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_AUCTIONS);
//            pstmt.setString(1, selectFrom);
//            pstmt.setString(2, where);
//            pstmt.setString(3, is);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {

                if (myRs.getString("type").equals("countdown")) {
                    id = myRs.getInt("id");
                    user = getUser((myRs.getInt("sellerID")));
                    product = getProduct(myRs.getInt("productID"));
                    quantity = myRs.getInt("productquantity");
                    price = myRs.getDouble("currentprice");
                    priceloweringAmount = myRs.getDouble("priceloweringAmount");
                    priceloweringDelay = myRs.getDouble("priceloweringdelay");
                    minprice = myRs.getDouble("minPrice");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    date = myRs.getTimestamp("timecreated");
                    auction = new Countdown(id, user, product, quantity, price, priceloweringAmount, priceloweringDelay, minprice, status, description, imageURL, instabuyprice, date);
                }

                // In case of Direct 
                if (myRs.getString("type").equals("direct")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Direct(id, user, product, price, begin, quantity, status, description, imageURL, instabuyprice);
                }

                if (myRs.getString("type").equals("standard")) {
                    id = myRs.getInt("id");
                    user = getUser(myRs.getInt("sellerID"));
                    product = getProduct(myRs.getInt("productID"));
                    price = myRs.getDouble("currentprice");
                    quantity = myRs.getInt("productquantity");
                    Timestamp begin = myRs.getTimestamp("timecreated");
                    date = myRs.getTimestamp("timeend");
                    status = StatusEnum.values()[myRs.getInt("status")];
                    description = myRs.getString("description");
                    imageURL = myRs.getString("imageUrl");
                    instabuyprice = myRs.getDouble("instabuyprice");
                    auction = new Standard(id, user, product, price, quantity, begin, date, status, description, imageURL, instabuyprice);
                }

                auctions.add(auction);
                auction = null;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Je bent fucked.");
        }

        closeConnection();
        return auctions;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Product> getProducts() {

        this.products = new ArrayList<>();
        Product product;
        int id;
        String name;
        String description;
        String gtin;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_PRODUCTS);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {
                    id = myRs.getInt("id");
                    name = myRs.getString("name");
                    description = myRs.getString("description");
                    gtin = myRs.getString("gtin");
                    product = new Product(id, gtin, name, description);
                
                products.add(product);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Product ding connection ophalen failed ofzo.");
        }

        closeConnection();
        return products;
    }
    
    public ArrayList<Queue_Purchase> getQueuePurchases() {

        ArrayList<Queue_Purchase> queuepurchases = new ArrayList<>();
        Queue_Purchase queuepurchase = null;
        int id;
        int quantity;
        double minPrice;
        double maxPrice;
        int productID;
        int placerID;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

        try {
            getConnection();
            pstmt = myConn.prepareStatement(GET_ALL_QUEUEPURCHASE);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (myRs.next()) {
                id = resultset.getInt("id");
                quantity = resultset.getInt("quantity");
                minPrice = resultset.getDouble("minprice");
                maxPrice = resultset.getDouble("maxprice");
                productID = resultset.getInt("productid");
                placerID = resultset.getInt("placerID");

                queuepurchase = new Queue_Purchase(quantity, minPrice, maxPrice, productID, placerID);
                queuepurchase.setID(id);
                
                queuepurchases.add(queuepurchase);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Product ding connection ophalen failed ofzo.");
        }

        closeConnection();
        return queuepurchases;
    }

    /**
     *
     * @param id
     * @return
     */
    public User getUser(int id) {
        User user = null;
        int userID;
        int bsn;
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
                preparedStatement = myConn.prepareStatement(GET_FROM_USER_ID);
                preparedStatement.setInt(1, id);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                userID = resultset.getInt("id");
                bsn = resultset.getInt("bsn");
                username = resultset.getString("username");
                password = resultset.getString("password");
                alias = resultset.getString("alias");
                email = resultset.getString("email");
                verified = resultset.getBoolean("verified");
                saldo = resultset.getFloat("saldo");
                String imgURL = resultset.getString("imageUrl");

                user = new User(userID,bsn, username, password, alias, email, verified, saldo, imgURL);

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
     *
     * @param username
     * @param password
     * @return
     */
    public User getUser(String username, String password) {
        User user = null;

        try {
            getConnection();
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
            int bsn = myRs.getInt("bsn");
            String usernm = myRs.getString("username");
            String pass = myRs.getString("password");
            String alias = myRs.getString("alias");
            String email = myRs.getString("email");
            boolean verified = myRs.getBoolean("verified");
            double saldo = myRs.getDouble("saldo");
            String imgURL = myRs.getString("imageUrl");

            user = new User(userID, bsn, usernm, pass, alias, email, verified, saldo, imgURL);
            closeConnection();
        } catch (SQLException ex) {
            System.out.println("User not found");
            closeConnection();
        }

        return user;
    }
    
    /**
     * Gets user with given username
     * @param username
     * @return
     */
    public User getUser(String username) {
        User user = null;

        try {
            getConnection();
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
            int bsn = myRs.getInt("bsn");
            String usernm = myRs.getString("username");
            String pass = myRs.getString("password");
            String alias = myRs.getString("alias");
            String email = myRs.getString("email");
            boolean verified = myRs.getBoolean("verified");
            double saldo = myRs.getDouble("saldo");
            String imgURL = myRs.getString("imageUrl");

            user = new User(userID, bsn, usernm, pass, alias, email, verified, saldo, imgURL);
            closeConnection();
        } catch (SQLException ex) {
            System.out.println("User not found");
            closeConnection();
        }

        return user;
    }
    
    /**
     *
     * @param checkValue
     * @return
     */
    public boolean hasDuplicateBSN(int checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
            pstmt = myConn.prepareStatement("SELECT * FROM user WHERE bsn = ?");
            pstmt.setInt(1, checkValue);
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
            int bsn = myRs.getInt("bsn");
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    /**
     *
     * @param checkValue
     * @return
     */
    public boolean hasDuplicateUsername(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
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
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    /**
     *
     * @param checkValue
     * @return
     */
    public boolean hasDuplicateEmail(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
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
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }

    /**
     *
     * @param checkValue
     * @return
     */
    public boolean hasDuplicateAlias(String checkValue) {
        Boolean hasDuplicate = false;
        int count = 0;

        try {
            getConnection();
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
            closeConnection();
            hasDuplicate = true;
        } catch (SQLException ex) {
            closeConnection();
        }

        return hasDuplicate;
    }
    
    /**
     * Instabuy on a auction. This will make a new transaction and lowers the amount of the items
     * in the auction.
     * @param amount The amount of items the User want to buy.
     * @param auctionID The ID of the auction.
     * @param userID The ID of the sser. 
     * @return true if succesfully added to the database, false if it failed to add info to the database.
     * @throws SQLException if statement failed to add info to the database.
     */
    public Boolean InstabuyItem(int amount, int auctionID, int userID) {
        getConnection();
        
        
        User user = getUser(userID);
        Auction auction = getAuction(auctionID);
        
        // Checks if User exsists
        if(user != null) {
            // Checks if Saldo is high enough
            if(user.getSaldo() >= auction.getCurrentPrice()) {
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?noAccessToProcedureBodies=true", "lesleya213_pts", "wachtwoord123");
                    CallableStatement myStmt = myConn.prepareCall("{call instabuy(?,?,?)}");
                    myStmt.setInt(1, amount);
                    myStmt.setInt(2, auctionID);
                    myStmt.setInt(3, userID);
                    
                    myStmt.execute();
                    System.out.println("GELUKT!!");
                    closeConnection();
                    return true;
                } catch(SQLException ex) {
                    closeConnection();
                    
                     System.out.println(ex);
                    return false;
                }
            } else {
                closeConnection();
                 System.out.println("Te weinig Saldo!");
                return false;
            }
        } else {
             System.out.println("User is Null");
            closeConnection();
            return false;
        }
    }
    
    /**
     *
     * @param amount
     * @param auctionID
     * @param userID
     * @param price
     * @return
     * @throws SQLException
     */
    public Boolean addBid(double amount, int auctionID, int userID, double price) throws SQLException {
        getConnection();
        User user = getUser(userID);
        Auction auction = getAuction(auctionID);
        
        // Checks if User exsists
        if(user != null) {
            // Checks if Saldo is high enough
            if(user.getSaldo() >= auction.getCurrentPrice()) {
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?noAccessToProcedureBodies=true", "lesleya213_pts", "wachtwoord123");
                    CallableStatement myStmt = myConn.prepareCall("{call bid(?,?,?,?)}");
                    myStmt.setInt(1, auctionID);
                    myStmt.setInt(2, userID);
                    myStmt.setDouble(3, amount);
                    myStmt.setDouble(4, price);
                    
                    myStmt.execute();
                    System.out.println("GELUKT!!");
                    closeConnection();
                    return true;
                } catch(SQLException ex) {
                    closeConnection();
                    
                     System.out.println(ex);
                    return false;
                }
            } else {
                closeConnection();
                 System.out.println("Te weinig Saldo!");
                return false;
            }
        } else {
             System.out.println("User is Null");
            closeConnection();
            return false;
        }
    }
    
    /**
     *
     * @param bsn
     * @param username
     * @param password
     * @param alias
     * @param email
     * @param imageUrl
     * @param saldo
     * @return
     */
    public Boolean setUser_REGISTER(int bsn, String username, String password, String alias, String email, String imageUrl, double saldo) {
        getConnection();

        if (myConn != null) {
            if (getUser(username, password) == null) {
                try {
                    getConnection();
                    boolean verified = false;
                    pstmt = myConn.prepareStatement(SET_USER_NEW);
                    pstmt.setInt(1, bsn);
                    pstmt.setString(2, username);
                    pstmt.setString(3, password);
                    pstmt.setString(4, alias);
                    pstmt.setString(5, email);
                    pstmt.setBoolean(6, verified);
                    pstmt.setString(7, imageUrl);
                    pstmt.setDouble(8, saldo);

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
                    closeConnection();
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
    
    public Boolean insertQueuePurchase(int quantity, double minprice, double maxprice, int productid, int placerid) {
        getConnection();
        
                try {
                    //INSERT INTO queuepurchase(quantity, minprice, maxprice, productid, placerID
                    pstmt = myConn.prepareStatement(SET_QUEUEPURCHASE_NEW);
                    pstmt.setInt(1, quantity);
                    pstmt.setDouble(2, minprice);
                    pstmt.setDouble(3, maxprice);
                    pstmt.setInt(4, productid);
                    pstmt.setInt(5, placerid);

                    if (pstmt.executeUpdate() > 0) {
                        System.out.println("succesfully registered new queuePurchase: ");
                        closeConnection();
                        return true;
                    } else {
                        System.out.println("Couldn't insert new queuePurchase. Rows are unaffected.");
                        closeConnection();
                        return false;
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    closeConnection();
                    return false;
                }
            

    }

    /**
     * removes a user with given bsn note: doesn't delete any objects yet that
     * the user created (e.g. auctions, bids, feedbacks)
     *
     * @param bsn
     * @return
     */
    public Boolean removeUser_BYBSN(int bsn) {
        getConnection();

        if (myConn != null) {
            try {
                pstmt = myConn.prepareStatement(REMOVE_USER_BYBSN);
                pstmt.setInt(1, bsn);

                if (pstmt.executeUpdate() > 0) {
                    System.out.println("succesfully deleted user with bsn: " + bsn);
                    return true;
                } else {
                    System.out.println("Couldn't delete user because user with bsn:: " + bsn + " doesn't exist in the database");
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("failed to remove user with bsn: " + bsn + ". SQLException");
                ex.printStackTrace();
                closeConnection();
                return false;
            }
        } else {
            System.out.println("failed to remove user with bsn: " + bsn + ". No connection to database.");
            return false;
        }
    }

    /**
     * removes a user with given username 
     * note: doesn't delete any objects yet that the user created (e.g. auctions, bids, feedbacks)
     * @param username
     * @return
     */
    public Boolean removeUser_BYUSERNAME(String username) {
        getConnection();

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
                closeConnection();
                return false;
            }
        } else {
            System.out.println("failed to remove user with username: " + username + ". No connection to database.");
            return false;
        }
    }
    
    private Product getProduct(int productID) {

        Product product = null;
        int id;
        String name;
        String description;
        String gtin;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

        if (myConn != null) {

            try {
                preparedStatement = myConn.prepareStatement(GET_FROM_PRODUCT);
                preparedStatement.setInt(1, productID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                id = resultset.getInt("id");
                name = resultset.getString("name");
                description = resultset.getString("description");
                gtin = resultset.getString("gtin");

                product = new Product(id, gtin, name, description);

                return product;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            getConnection();
            getProduct(productID);
        }
        return product;
    }
    
    public Queue_Purchase deleteQueuePurchase(int queueID) {
        
        getConnection();
        Queue_Purchase queuepurchase = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

            try {
                preparedStatement = myConn.prepareStatement(GET_QUEUEPURCHASE);
                preparedStatement.setInt(1, queueID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            getConnection();
            getQueuePurchase(queueID);
        
        return queuepurchase;
    }
    
    public Queue_Purchase getQueuePurchase(int queueID) {
        
        getConnection();
        Queue_Purchase queuepurchase = null;
        int id;
        int quantity;
        double minPrice;
        double maxPrice;
        int productID;
        int placerID;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

            try {
                preparedStatement = myConn.prepareStatement(GET_QUEUEPURCHASE);
                preparedStatement.setInt(1, queueID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                id = resultset.getInt("id");
                quantity = resultset.getInt("quantity");
                minPrice = resultset.getDouble("minprice");
                maxPrice = resultset.getDouble("maxprice");
                productID = resultset.getInt("productid");
                placerID = resultset.getInt("placerID");

                queuepurchase = new Queue_Purchase(quantity, minPrice, maxPrice, productID, placerID);
                queuepurchase.setID(id);
                closeConnection();
                return queuepurchase;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            getConnection();
            getQueuePurchase(queueID);
        
        return queuepurchase;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();

        int bsn;
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
                getConnection();
                preparedStatement = myConn.prepareStatement(GET_FROM_USER_ALLUSERS);
                resultset = preparedStatement.executeQuery();

                while (resultset.next()) {
                    bsn = resultset.getInt("bsn");
                    username = resultset.getString("username");
                    password = resultset.getString("password");
                    alias = resultset.getString("alias");
                    email = resultset.getString("email");
                    verified = resultset.getBoolean("verified");
                    saldo = resultset.getFloat("saldo");
                    String imgURL = resultset.getString("imageUrl");

                    User foundUser =    new User(bsn, username, password, alias, email, verified, saldo, imgURL);
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

    public List<Feedback> getFeedbackToSeller(String sellerid) {
        List<Feedback> feedbackToSeller = new ArrayList<Feedback>();

        try {
            getConnection();
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
            closeConnection();
        } catch (SQLException ex) {
            System.out.println("SQLException at getFeedbackToSeller");
            ex.printStackTrace();
            closeConnection();
        }

        return feedbackToSeller;
    }

    public List<Feedback> getFeedbackFromBuyer(String buyerid) {
        List<Feedback> feedbackFromBuyer = new ArrayList<Feedback>();

        try {
            getConnection();
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
            closeConnection();
        } catch (SQLException ex) {
            System.out.println("SQLException at getFeedbackToSeller");
            ex.printStackTrace();
            closeConnection();
        }

        return feedbackFromBuyer;
    }

    public Boolean submitFeedback(int rating, String description, String sellerid, String buyerid) {
        getConnection();

        if (myConn != null) {
            try {
                getConnection();
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
                closeConnection();
                return false;
            }
        } else {
            System.out.println("Registration of duplicate user isn't allowed.");
            return false;
        }
    }
    
    /**
     *
     * @param auction
     */
    public void updateAuction (Auction auction){

    }

    private boolean closeConnection() {
        try {
            myRs.close();
            myConn.close();
            pstmt.close();
            System.out.println("Closing connection to database...");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
