package Classes;

import Classes.Auctions.Auction;
import java.util.*;

public class Transaction {

    private double amount;
    Bid bid;
    Auction auction;

    public Transaction(Auction auction){
        this.auction = auction;
        this.bid = auction.getBestBid();
        this.amount = bid.getAmount();
    }
    
    /**
     * returns the total price
     *
     * @return double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * returns Auction of transaction
     *
     * @return Auction
     */
    public Auction getAuction() {
        return auction;
    }

}
