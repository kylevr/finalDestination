package Classes;

import Classes.Auctions.Auction;
import java.util.*;
import Exceptions.*;
import Database.*;

/**
 * The User class represents a user from our application.
 *
 * @author Lesley Peters
 * @version 0.1, March 27
 */
public class User {

    Grand_Exchange manages;
    private int userID;
    private int BSN;
    private String username;
    private String password;
    private String alias;
    private String email;
    private boolean verified;
    private double saldo;
    private String imageURL;
    private List<Bid> bids;
    private List<Auction> placedAuctions;
    private List<Queue_Purchase> placedOrders;
    private List<Transaction> transactions;

    /**
     * constructor for a user that gets initialized by supplying his name and password.
     * his info is then taken from the server
     * @param username
     * @param password
     */
    public User(String username, String password) {
        Connection conn = new Connection();
        User myUser = conn.getUser(username, password);
        
        this.userID = myUser.userID;
        this.BSN = myUser.BSN;
        this.username = myUser.username;
        this.password = myUser.password;
        this.alias = myUser.alias;
        this.email = myUser.email;
        this.verified = myUser.verified;
        this.saldo = myUser.saldo;
        this.imageURL = myUser.imageURL;
        this.bids = myUser.bids;
        this.placedAuctions = myUser.placedAuctions;
        this.placedOrders = myUser.placedOrders;
        this.transactions = myUser.transactions;
    }
    
    /**
     * constructor for a user that gets initialized by supplying his name and password.
     * @param username
     * @param password
     * @param imageURL
     */
    public User(String username, String password, String imageURL) {
        this.username = username;
        this.password = password;
        this.imageURL = imageURL;
        
        //User myUser = Database.Connection().getUser()
    }

    
    /**
     * constructor for a user with everything manually inputted.
     */
    public User(int userID, int BSN, String username, String password, String alias, String email, boolean verified, double saldo, String imageURL) {
        this.BSN = BSN;
        this.username = username;
        this.password = password;
        this.alias = alias;
        this.email = email;
        this.verified = verified;
        this.saldo = saldo;
        this.imageURL = imageURL;
        this.bids = new ArrayList<Bid>();
        this.placedAuctions = new ArrayList<Auction>();
        this.placedOrders = new ArrayList<Queue_Purchase>();
        this.transactions = new ArrayList<Transaction>();
    }   
    
    public int getUserID() {
        return userID;
    }

    
    /**
     * Adds feedback to another user.
     *
     * @param receiver is the username from the feedback receiver
     * @param description is the string that contains the feedback message
     * @exception EmptyFieldException is thrown when the receiver or description
     * String is empty
     */
    public void sendFeedback(String receiver, int rating, String description) throws EmptyFieldException {
        // TODO: send feedback to other user

        if (receiver == "") {
            throw new EmptyFieldException("Feedback receiver can't be empty.");
        }

        if (description == "") {
            throw new EmptyFieldException("Feedback description can't be empty.");
        }

        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Creates a new auction for this user
     *
     * @param auction is the new auction added to the user
     */
    public void addAuction(Auction auction) {
        this.placedAuctions.add(auction);
    }

    /**
     * Adds feedback to another user.
     *
     * @param auction is the auction that needs be removed from the user's lists
     */
    public void removeAuction(Auction auction) {
        this.placedAuctions.remove(auction);
    }

    /**
     * Adds feedback to another user.
     *
     * @param auction is the auction where the user wants to place the bid
     * @param bid is the bid that the user wants to place
     */
    public void requestPlaceBid(Auction auction, Bid bid) {
        // TODO: place the bid to the given auction, then return if the bid succeeded or not
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Het lijkt me logisch dat we een bieding niet kunnen removen, mocht je dit wel logisch vinden, vul hem dan maar in :P
    public void requestRemoveBid() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Adds or removes momey from the user's saldo
     *
     * @param amount is the amount of money to be added or removed from the user
     * @exception NotEnoughMoneyException is thrown when the user doesn't have
     * enough money
     */
    public void addSaldo(double amount) throws NotEnoughMoneyException {
        double oldSaldo = this.saldo;
        this.saldo += amount;
        if (this.saldo < 0) {
            throw new NotEnoughMoneyException("Not enough money.");
        }
    }

    /**
     * Returns the amount of money the user has
     *
     * @return the amount of money the user has
     */
    public double getSaldo() {
        return this.saldo;
    }

    /**
     * Receives the transaction history from this user
     *
     * @return a list with transaction objects
     */
    public List<Transaction> getTransactionHistory() {
        return this.transactions;
    }

    /**
     * Adds a queue purchase
     *
     * @param purchase is the purchase that needs to be added to the list
     */
    public void addQueuePurchase(Queue_Purchase purchase) {
        this.placedOrders.add(purchase);
    }

    /**
     * removes a queue purchase
     *
     * @param purchase is the purchase that needs to be removed from the list
     */
    public void removeQueuePurchase(Queue_Purchase purchase) {
        this.placedOrders.remove(purchase);
    }

    /**
     * Method that sends a request to verify the user's account
     */
    public void requestVerification() {
        // TODO: implement the method to verify a user.
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String getUsername() {
        return username;
    }
    
    public String getImageURL() {
        return imageURL;
    }
}
