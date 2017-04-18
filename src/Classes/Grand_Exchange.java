package Classes;

import Classes.Auctions.Auction;
import Classes.User;
import java.util.*;
import Database.*;
import java.sql.SQLException;

public class Grand_Exchange implements Observer {

    ArrayList<Product> products;
    ArrayList<User> users;
    ArrayList<Auction> auctions;
    ArrayList<Queue_Purchase> queuepurchases;
    Connection con;
    DatabaseListener dbListener;

    public User loggedInUser;// = new User("AAP","test","http://www.jamiemagazine.nl/upload/artikel/jm/banaan-vierkant.jpg");

    public User getLoggedInUser() {
        return loggedInUser;
    }

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

    public void Load() {

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

    public void addAuction(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException();
        } else {
            auctions.add(auction);
        }
    }

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
    
    public void addQueuePurchase(int quantity, double minprice, double maxprice, int productid, int placerid) {
        con.insertQueuePurchase(quantity, minprice, maxprice, productid, placerid);
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
     */
    public boolean login(String username, String password) {
        this.loggedInUser = con.getUser(username, password);
        if (this.loggedInUser != null) {
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
    
    public ArrayList<Product> getProducts(String name, CategoryEnum category) {
        ArrayList<Product> tempList = new ArrayList<>();
        String productName = name.toLowerCase();
        for(Product p : products){
            if(productName.equals("")){
                if (p.getCategory().equals(category)){
                    tempList.add(p);
                }
            }            
            else if(p.getName().contains(productName) && p.getCategory().equals(category)){
                tempList.add(p);
            }
            else if (p.getName().contains(productName)){
                tempList.add(p);
            }
        }
        
        return tempList;
    }

    public Collection<Auction> getAuctions() {
        return auctions;
    }
    
    public boolean InstabuyItem(int amount, int auctionID, int buyerID) throws SQLException {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + "BID: " + buyerID);
            con.InstabuyItem(amount, auctionID, 1);
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }
    
    public boolean addBid(double amount, int auctionID, int buyerID, double price) {
        try {
            System.out.println("amount :" + amount + " AID: " + auctionID + " BID: " + buyerID + " Price: " + price);
            con.addBid(amount, auctionID, 1, price);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
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
                if(!queuepurchases.contains(tempQueuePurchase) && tempQueuePurchase != null){
                    queuepurchases.add(tempQueuePurchase);
                    System.out.println("New queue purchase added with ID: " + tempQueuePurchase.getID());
                }
            }
        }

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
                if(!auctions.contains(tempAuction) && tempAuction != null){
                    
                    for(Queue_Purchase QP : queuepurchases){
                        if(tempAuction.getProduct().getId() == QP.getProductID()){
                            if (tempAuction.getInstabuyPrice() < QP.getMaxPrice()){
                                if(tempAuction.getProductQuantity() >= QP.getQuantity()){
                                    con.InstabuyItem(QP.getQuantity(),tempAuction.getId(),QP.getPlacerID());
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

    @Override
    public void update (Observable o, Object arg) {
        String type = arg.toString();
        if (type.equals("Auction")){
            System.out.println("New auctions found.");
            updateAuctionsFromDB(dbListener.getUpdateAuctionList());
        }
        else if(type.equals("Queue")){
            System.out.println("New QueuePurchase found.");
            updateQueuePurchaseFromDB(dbListener.getUpdateQueuepurchaseList());
        }
        
    }


    public void updateAuction(Auction auction) {
        con.updateAuction(auction);
    }
}
