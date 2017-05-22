/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.Auctions.Auction;
import Classes.Bid;
import Classes.Feedback;
import Classes.User;
import java.util.List;

/**
 *
 * @author piete
 */
public interface IAuction {

    /**
     *returns all auctions 
     * @return list of auctions
     */
    List<Auction> getAuctions();

    /**
     *returns list of Bids with auction id
     * @param auctionId id of corresponding auction
     * @return list of bids
     */
    List<Bid> getBids(int auctionId);
    
    /**
     *returns the id of the seller of the auction
     * @param auctionId id of corresponding auction
     * @return seller
     */
    User getSeller(int auctionId);
    
    /**
     *adds a auction tot the database
     * @param auction auction to be added
     */
    void addAuction(Auction auction);
    
    /**
     *adds feedback to a seller
     * @param feedback feedback to be added
     */
    void addFeedback(Feedback feedback);
    
    /**
     * sends a mail to the Seller
     * @param content content to be send to seller
     */
    void sendMail(String content);
}
