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
     *returns all auctions 
     * @return list of auctions
     */
    Auction getAuction(int id) throws RemoteException;


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
     *adds a auction tot the database
     * //TODO: er staat 3 keer "iets" in. Dit moet nog veranderd worden naar een betere naam.
     * @param auction auction to be added
     */
   boolean addAuction(int userID, int productID, double startingprice, double instabuyPrice, int instabuyable, int quantity, int  iets, int iets2, String auctionType, int iets3, String imageUrl, String desrcription)throws RemoteException ;
   
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
    
    /**
     * Buy a instabuy item and put it into the database
     *
     * @param amount The amount the user wants to pay.
     * @param auctionID The id of the auction.
     * @param userID The id of the user.
     * @return True if instabuy item is be brought, false if its not.
     */
    public boolean InstabuyItem(int amount, int auctionID, int userID) throws RemoteException;;
    
    /**
     * Updates the auction given in the parameter.
     *
     * @param auction the auction that need to be updated
     */
    public void updateAuction(Auction auction) throws RemoteException;;
    
}
