package Classes;

import Classes.Auctions.Auction;
import java.util.Date;

public class Bid {

    private double amount;
    private Date timeCreated;
    private User placer;
    private Auction auction;

    /**
     * 
     * @param placer : may not be null
     * @param amount : must be higher than 0
     */
    public Bid(User placer, double amount) {
        if (placer == null || amount <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.placer = placer;
            this.amount = amount;
            this.auction = auction;
            Date date = new Date();
            this.timeCreated = date;
        }
    }

    /**
     * This method is used to get a string of the placer of this bid.
     *
     * @return User
     */
    public String getPlacerUsername() {
        return this.placer.getUsername();
    }

    /**
     * returns the bids amount
     *
     * @return double
     */
    public double getAmount() {
        return amount;
    }

    public Auction getAuction() {
        return auction;
    }

}
