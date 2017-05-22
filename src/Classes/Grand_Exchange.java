package Classes;

import Classes.Auctions.Auction;
import Classes.User;
import java.util.*;
import Database.*;
import Interfaces.IAuthorized;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Grand_Exchange implements Observer,IAuthorized {

    ArrayList<Product> products;
    ArrayList<User> users;
    ArrayList<Auction> auctions;
    ArrayList<Queue_Purchase> queuepurchases;
    Connection con;
    DatabaseListener dbListener;

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
    public Grand_Exchange() {
        products = new ArrayList<>();
        users = new ArrayList<>();
        auctions = new ArrayList<>();
        queuepurchases = new ArrayList<>();
        con = new Connection();

        //Gets all existing auctions.
        auctions = con.getAuctions("*", "auction", "''");
        products = con.getProducts();
        queuepurchases = con.getQueuePurchases();

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
    public void addAuction(Auction auction) {
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
     * @param quantity : amount of items to be bought
     * @param minprice : minimum price to pay for items
     * @param maxprice : maximum price to pay for items
     * @param productid: id of product to be bought
     * @param placerid : id of user who placed the queue purchase
     */
    public void addQueuePurchase(int quantity, double minprice, double maxprice, int productid, int placerid) {
        con.insertQueuePurchase(quantity, minprice, maxprice, productid, placerid);
    }
/**
 * adds product to database
 * @param name : name of product
 * @param description : description of product
 * @param gtin : global trading number of product
 * @return 
 */
    public int addProductToDB(String name, String description, int gtin) {
        return con.insertProduct(name, description, gtin);
    }
/**
 * adds auction to database
 * @param sellerid : id of user who sells item
 * @param productid: id of product to be sold
 * @param currentprice : price of product at the moment
 * @param instabuyprice: price where it can be bought imedeatel
 * @param instabuyable : is the item instabuyable?
 * @param quantity     : quantity of products for sale
 * @param loweringamount: amount of the pricelowering after the specified amount of time
 * @param loweringdelay : delay for lowering the price
 * @param type  : type of auction, standard, countdown or direct
 * @param status: status of product
 * @param imgurl: urls of images splitted by ;
 * @param description : description of auction
 * @return 
 */
    public boolean addAuctionToDB(int sellerid, int productid, double currentprice, double instabuyprice, int instabuyable, int quantity, double loweringamount, int loweringdelay, String type, int status, String imgurl, String description) {
        return con.insertAuction(sellerid, productid, currentprice, instabuyprice, instabuyable, quantity, loweringamount, loweringdelay, type, status, imgurl, description);
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
    public boolean login(String username, String password) throws RemoteException {
        User Guest = con.getUser(username, password);
        if (Guest != null) {
            users.add(Guest);
            System.out.println("user with username " + loggedInUser.getUsername() + " is logged in");
            return true;
        } else {
            System.out.println("no user is logged in");
            return false;
        }
    }

    /**
     * logout of user who is logged in
     *
     */
    public void logout() {
        this.loggedInUser = null;
        System.out.println("logged out user");
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
     * @return 
     */
    public Collection<Auction> getAuctions() {
        return auctions;
    }

    /**
     * performs instabuy for user
     * @param amount : amount of items to be bought
     * @param auctionID: id of auction to buy
     * @param buyerID : id of user who buys
     * @return boolean
     * @throws SQLException 
     */
    public boolean InstabuyItem(int amount, int auctionID, int buyerID) throws SQLException {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + "BID: " + buyerID);
            con.InstabuyItem(amount, auctionID, 1);
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }

    /**
     * adds bid to auction and to db
     * @param amount
     * @param auctionID
     * @param buyerID
     * @param price
     * @return 
     */
    public boolean addBid(double amount, int auctionID, int buyerID, double price) {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + " BID: " + buyerID + " Price: " + price);
            con.addBid(amount, auctionID, 1, price);
            return true;
        } catch (SQLException ex) {
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
            tempQueuePurchase = con.getQueuePurchase(i);

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
            tempAuction = con.getAuction(i);

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
                                con.InstabuyItem(QP.getQuantity(), tempAuction.getId(), QP.getPlacerID());
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
 * @param auction :auction to be updated
 */
    public void updateAuction(Auction auction) {
        con.updateAuction(auction);
    }

    /**
     * gest user from collection of users with given username
     *
     * @param userName
     */
    public User getUser(String userName) {
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
        for (User u : this.con.getAllUsers()) {
            this.addUser(u);
        }
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
        
        if (conn.getUser(username) != null)
        {
            for (User u : this.users)
            {
                if (u.getUsername().equals(username))
                {
                    u.removeAllFeedback();
                    for (Feedback f : conn.getFeedbackToSeller(username)) {
                        u.addFeedback(f);
                    }          
                    for (Feedback f : conn.getFeedbackFromBuyer(username)) {
                        u.addFeedback(f);
                    }     
                    u.sortFeedbacklistByDate();
                    successful = true;
                }
            }
        }
        return successful;
    }
}
