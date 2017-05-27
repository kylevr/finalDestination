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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author piete
 */
public interface IAuction extends Remote{

    /**
     *returns all auctions 
     * @return list of auctions
     */
    Collection<Auction> getAuctions() throws RemoteException;

    /**
     *returns list of Bids with auction id
     * @param auctionId id of corresponding auction
     * @return list of bids
     */
    List<Bid> getBids(int auctionId) throws RemoteException;
    
    /**
     *returns the id of the seller of the auction
     * @param userName username of user to be searched
     * @return seller
     */
    User getUser(String userName) throws RemoteException ;
    
    /**
     *adds a auction tot the database
     * @param auction auction to be added
     */
   void addAuction(Auction auction)throws RemoteException ;
    
    /**
     *adds feedback to a seller
     * @param feedback feedback to be added
     */
    void addFeedback(Feedback feedback)throws RemoteException;
    
    /**
     * sends a mail to the Seller
     * @param content content to be send to seller
     */
    void sendMail(int senderId, int receiverId, String content)throws RemoteException;
}
