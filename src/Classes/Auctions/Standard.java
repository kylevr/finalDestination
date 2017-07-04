/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

/**
 *
 * @author piete
 */
public class Standard extends Auction implements Serializable {

    private Timestamp creationDate;
    private Timestamp timeEnd;

    public Standard(int id, User seller, Product product, double price, int quantity, Timestamp beginTime, Timestamp timeEnd, StatusEnum status, String description, String imageURLs, double instabuy) throws RemoteException {
        super(id, seller, product, price, quantity, status, description, imageURLs, instabuy);
        this.timeEnd = timeEnd;
        this.creationDate = beginTime;
        this.timeEnd = new Timestamp(beginTime.getTime()+(long)604800000);
        super.setType("standard");
    }

    public Standard(User seller, Product product, int quantity, double price, double instabuyprice, Timestamp beginTime, Timestamp timeEnd, StatusEnum status, String description, String imageURLs) throws RemoteException {
        super(seller, product, quantity, price, instabuyprice, status, description, imageURLs);
        this.timeEnd = timeEnd;
        this.creationDate = beginTime;
        super.setType("standard");
    }

    /**
     * Returns the remaining time of a standard auction in seconds.
     *
     * @return remaining time of a standard auction in seconds
     */
    public long getTimeRemaining() {
        long diff = timeEnd.getTime() - creationDate.getTime();
        return diff / 1000 % 60;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Timestamp getEndDate() {
        return timeEnd;
    }
}
