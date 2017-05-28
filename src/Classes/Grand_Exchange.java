package Classes;

import Classes.Auctions.Auction;
import Classes.User;
import java.util.*;
import Database.*;
import Exceptions.NotEnoughMoneyException;
import Interfaces.IAuction;
import Interfaces.IAuthorized;
import Interfaces.ICreateProduct;
import Interfaces.ICreateQueuePurchase;
import Interfaces.IPlaceBid;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Grand_Exchange implements Observer, IAuthorized, IAuction, ICreateProduct, ICreateQueuePurchase, IPlaceBid {

    ArrayList<Product> products;
    ArrayList<User> users;
    ArrayList<Auction> auctions;
    ArrayList<Queue_Purchase> queuepurchases;
    Connection con;
    AuctionConnection auctionConn;
    ProductConnection productConn;
    QueuePurchaseConnection qPConn;
    UserConnection userConn;
    DatabaseListener dbListener;
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public User loggedInUser;

    /**
     * returns user logged in
     *
     * @return User: logged in user
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * initialize GX
     */
    public Grand_Exchange() throws RemoteException {
        products = new ArrayList<>();
        users = new ArrayList<>();
        auctions = new ArrayList<>();
        queuepurchases = new ArrayList<>();
        con = new Connection();
        auctionConn = new AuctionConnection();
        productConn = new ProductConnection();
        qPConn = new QueuePurchaseConnection();

        //Gets all existing auctions.
        auctions = auctionConn.getAuctions("*", "auction", "''");
        products = productConn.getProducts();
        queuepurchases = qPConn.getQueuePurchases();

        dbListener = new DatabaseListener();
        dbListener.addObserver(this);
    }

    /**
     * adds user tot he collection of users
     *
     * @param user : may not be null
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.add(user);
        }
    }

    /**
     * removes user from collection of users
     *
     * @param user : may not be null
     */
    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.remove(user);
        }
    }

    /**
     * adds auction to list of auctions
     *
     * @param auction :auction to be added
     */
    public void addAuction(Auction auction) throws RemoteException {
        if (auction == null) {
            throw new IllegalArgumentException();
        } else {
            auctions.add(auction);
        }
    }

    /**
     * removes auction from list of auctions
     *
     * @param auction :auction to be deleted
     */
    public void removeAuction(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException();
        } else {
            auctions.add(auction);
        }
    }

    /**
     * Adds product to collection of products
     *
     * @param product : may not be null
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.add(product);
        }
    }

    /**
     * adds queue purchase to Database
     *
     * @param quantity : amount of items to be bought
     * @param minprice : minimum price to pay for items
     * @param maxprice : maximum price to pay for items
     * @param productid: id of product to be bought
     * @param placerid : id of user who placed the queue purchase
     */
    public void addQueuePurchase(int quantity, double minprice, double maxprice, int productid, int placerid) {
        qPConn.insertQueuePurchase(quantity, minprice, maxprice, productid, placerid);
    }

    /**
     * adds product to database
     *
     * @param name : name of product
     * @param description : description of product
     * @param gtin : global trading number of product
     * @return
     */
    public int addProductToDB(String name, String description, int gtin) {
        return productConn.insertProduct(name, description, gtin);
    }

    /**
     * adds auction to database
     *
     * @param sellerid : id of user who sells item
     * @param productid: id of product to be sold
     * @param currentprice : price of product at the moment
     * @param instabuyprice: price where it can be bought imedeatel
     * @param instabuyable : is the item instabuyable?
     * @param quantity : quantity of products for sale
     * @param loweringamount: amount of the pricelowering after the specified
     * amount of time
     * @param loweringdelay : delay for lowering the price
     * @param type : type of auction, standard, countdown or direct
     * @param status: status of product
     * @param imgurl: urls of images splitted by ;
     * @param description : description of auction
     * @return
     */
    public boolean addAuctionToDB(int sellerid, int productid, double currentprice, double instabuyprice, int instabuyable, int quantity, double loweringamount, int loweringdelay, String type, int status, String imgurl, String description) {
        return auctionConn.insertAuction(sellerid, productid, currentprice, instabuyprice, instabuyable, quantity, loweringamount, loweringdelay, type, status, imgurl, description);
    }

    /**
     * removes product from collection of products
     *
     * @param product : may not be null
     */
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.remove(product);
        }
    }

    /**
     * Checks user login
     *
     * @param username : may not be empty nor null
     * @param password : may not be empty nor null
     * @return loggedIn
     */
    public User login(String username, String password) throws RemoteException {
        User Guest = userConn.getUser(username, password);
        if (Guest != null && userConn.setAuthorized(Guest.getUsername(), true)) {
            users.add(Guest);
            System.out.println("user with username " + Guest.getUsername() + " is logged in");
            return Guest;
        } else {
            System.out.println("no user is logged in");
            return null;
        }
    }

    /**
     * handles transaction
     *
     * @param transaction : may not be null
     */
    public void handleTransaction(Transaction transaction) {

    }

    /**
     * Returns list of all 'official' products
     *
     * @return List<Product>
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * returns list of products with filters
     *
     * @param name : search terms
     * @param category : category to search in
     * @return ArrayList<Product>
     */
    public ArrayList<Product> getProducts(String name, CategoryEnum category) {
        ArrayList<Product> tempList = new ArrayList<>();
        String productName = name.toLowerCase();
        for (Product p : products) {
            if (productName.equals("")) {
                if (p.getCategory().equals(category)) {
                    tempList.add(p);
                }
            } else if (p.getName().contains(productName) && p.getCategory().equals(category)) {
                tempList.add(p);
            } else if (p.getName().contains(productName)) {
                tempList.add(p);
            }
        }

        return tempList;
    }

    /**
     * returns list of all auctions available at the moment
     *
     * @return
     */
    public Collection<Auction> getAuctions() throws RemoteException {
        return auctions;
    }

    /**
     * performs instabuy for user
     *
     * @param amount : amount of items to be bought
     * @param auctionID: id of auction to buy
     * @param buyerID : id of user who buys
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean InstabuyItem(int amount, int auctionID, int buyerID) throws RemoteException {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + "BID: " + buyerID);
            auctionConn.InstabuyItem(amount, auctionID, 1);
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }

    /**
     *
     * @param newQueuePurchases
     */
    public void updateQueuePurchaseFromDB(ArrayList<Integer> newQueuePurchases) {
        Queue_Purchase tempQueuePurchase;
        for (int i : newQueuePurchases) {
            tempQueuePurchase = qPConn.getQueuePurchase(i);

            if (tempQueuePurchase == null) {
                System.out.println("QueuePurchase is null");
            }

            for (Queue_Purchase QP : queuepurchases) {
                if (QP.getID() == tempQueuePurchase.getID()) {
                    queuepurchases.set(queuepurchases.indexOf(QP), tempQueuePurchase);
                    System.out.println("Queue purchase replaced with ID : " + tempQueuePurchase.getID());
                }
            }
            if (!queuepurchases.contains(tempQueuePurchase) && tempQueuePurchase != null) {
                queuepurchases.add(tempQueuePurchase);
                System.out.println("New queue purchase added with ID: " + tempQueuePurchase.getID());
            }
        }
    }

    /**
     *
     * @param newAuctionIDs
     */
    public void updateAuctionsFromDB(ArrayList<Integer> newAuctionIDs) {
        Auction tempAuction;
        for (int i : newAuctionIDs) {
            tempAuction = auctionConn.getAuction(i);

            if (tempAuction == null) {
                System.out.println("Auction is null");
            }

            for (Auction A : auctions) {
                if (A.getId() == tempAuction.getId()) {
                    auctions.set(auctions.indexOf(A), tempAuction);
                    System.out.println(tempAuction.getProduct().getName() + "Replaced in list.");
                }
            }
            if (!auctions.contains(tempAuction) && tempAuction != null) {

                for (Queue_Purchase QP : queuepurchases) {
                    if (tempAuction.getProduct().getId() == QP.getProductID()) {
                        if (tempAuction.getInstabuyPrice() < QP.getMaxPrice()) {
                            if (tempAuction.getProductQuantity() >= QP.getQuantity()) {
                                auctionConn.InstabuyItem(QP.getQuantity(), tempAuction.getId(), QP.getPlacerID());
                                //TODO Queuepurchase has to be dropped from database, AND displayed in the GUI
                            }
                        }
                    }
                }

                auctions.add(tempAuction);
                System.out.println(tempAuction.getProduct().getName() + "New Auction added to list.");
            }
        }
    }

    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        String type = arg.toString();
        if (type.equals("Auction")) {
            System.out.println("New auctions found.");
            updateAuctionsFromDB(dbListener.getUpdateAuctionList());
        } else if (type.equals("Queue")) {
            System.out.println("New QueuePurchase found.");
            updateQueuePurchaseFromDB(dbListener.getUpdateQueuepurchaseList());
        }

    }

    /**
     * updates auction from DB
     *
     * @param auction :auction to be updated
     */
    public void updateAuction(Auction auction) {
        auctionConn.updateAuction(auction);
    }

    /**
     * gest user from collection of users with given username
     *
     * @param userName
     */
    public User getUser(String userName) throws RemoteException {
        User missingUser = null;
        for (User u : this.users) {
            if (u.getUsername().equals(userName)) {
                missingUser = u;
            }
        }
        return missingUser;
    }

    /**
     * updates all users from database
     */
    public void updateUsers() {
        this.users.clear();
        for (User u : this.userConn.getAllUsers()) {
            this.addUser(u);
        }
    }

    @Override
    public List<Bid> getBids(int auctionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addFeedback(Feedback feedback) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * updates feedbacklist of user with given username
     * @param username
     * @return True if succesfull, false if username doesn't exist
     */
    public boolean updateFeedbacklist(String username) {
        Connection conn = new Connection();
        conn.getConnection();
        boolean successful = false;

        if (userConn.getUser(username) != null) {
            for (User u : this.users) {
                if (u.getUsername().equals(username)) {
                    u.removeAllFeedback();
                    for (Feedback f : userConn.getFeedbackToSeller(username)) {
                        u.addFeedback(f);
                    }
                    for (Feedback f : userConn.getFeedbackFromBuyer(username)) {
                        u.addFeedback(f);
                    }
                    u.sortFeedbacklistByDate();
                    successful = true;
                }
            }
        }
        return successful;
    }
    
    /**
     * registers a new users and returns errormessage
     * @param username
     * @param password
     * @param alias
     * @param email
     * @return message that says if it's successful or not
     */
    public String registerUser(String username, String password, String alias, String email)
    {
        String errorMsg = "Failed to register user:";

        try {
            username = username.trim();
            password = password.trim();
            alias = alias.trim();
            email = email.trim();

            System.out.println("Starting registration...");

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || alias.isEmpty()) {
                errorMsg += "\n -All fields must be filled";
            } else {
                Connection conn = new Connection();
                boolean duplicateUsername = userConn.hasDuplicateUsername(username);
                boolean duplicateAlias = userConn.hasDuplicateAlias(alias);
                boolean duplicateEmail = userConn.hasDuplicateEmail(email);

                if (duplicateUsername) {
                    errorMsg += "\n -Username is already used";
                }
                if (duplicateAlias) {
                    errorMsg += "\n -Alias is already used";
                }
                if (duplicateEmail) {
                    errorMsg += "\n -Email is already used";
                }

                if (!duplicateUsername && !duplicateAlias && !duplicateEmail) {
                    userConn.setUser_REGISTER(username, password, alias, email, null, 0);
                    errorMsg = "Succesfully registered new user!";
                }
            }

        } catch (NumberFormatException ex) {
            errorMsg += "\n -BSN field must constain a number";
        } finally {
            System.out.println(errorMsg);
            return errorMsg;
        }
    }

    @Override
    public int createProduct(int GTIN, String name, String description) throws RemoteException {
        int newProductID = productConn.insertProduct(name, description, GTIN);
        return newProductID;
    }

    @Override
    public boolean createQueuePurchase(int Quantity, double minPrice, double maxPrice, int productID, int placerID) throws RemoteException {
        return qPConn.insertQueuePurchase(Quantity, minPrice, maxPrice, productID, placerID);
    }

    @Override
    public boolean placeBid(double amount, int userID, int AuctionID, double price) throws RemoteException, NotEnoughMoneyException {
        try {
            return auctionConn.addBid(amount, AuctionID, 1, price);
        } catch (SQLException ex) {
            Logger.getLogger(Grand_Exchange.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean logout(String username) throws RemoteException {
        if (this.setIsAuthorized(username, false))
        {
            System.out.println("User with username " + username + " is logged out");
            return true;
        }
        else
        {
            System.out.println("Failed to logout User with username " + username);
            return false;
        }
    }

    @Override
    public boolean setIsAuthorized(String username, boolean isAuthorized) throws RemoteException {
        boolean successful = this.userConn.setAuthorized(username, isAuthorized);
        return successful;
    }

    @Override
    public void sendMail(int senderId, int receiverId, String content) throws RemoteException {
        User sender = userConn.getUser(senderId);
        User receiver = userConn.getUser(receiverId);
        try {
            generateAndSendEmail(sender.getUsername(), receiver.getUserEmail(), content);
        } catch (MessagingException ex) {
            Logger.getLogger(Grand_Exchange.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generateAndSendEmail(String senderUsername, String receiverEmail, String message) throws AddressException, MessagingException {
 
		// Step1
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
 
		// Step2
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
		generateMailMessage.setSubject("Message from GrandExchange user: " + senderUsername);
		generateMailMessage.setContent(message, "text/html");
 
		// Step3
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "grandexchangemail@gmail.com", "Wachtwoord123");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

    @Override
    public void addAuction(int userID, int productID, double startingprice, double instabuyPrice, int instabuyable, int quantity, int iets, int iets2, String auctionType, int iets3, String imageUrl, String desrcription) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
