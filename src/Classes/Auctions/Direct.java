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
import java.util.Date;

/**
 *
 * @author piete
 */
public class Direct extends Auction implements Serializable{

    private  Timestamp creationDate;

    public Direct(int id, User seller, Product product, double price, Timestamp beginTime, int quantity, StatusEnum status, String description, String imageURLs, double instabuy) throws RemoteException {
        super(id, seller, product, price, quantity, status, description, imageURLs, instabuy);
        this.creationDate = beginTime;
        super.setType("direct");
    }

    public Direct(User seller, Product product, double price, Timestamp beginTime, int quantity, double instabuyprice, StatusEnum status, String description, String imageURLs) throws RemoteException {
        super(seller, product, quantity, price, instabuyprice, status, description, imageURLs);
        this.creationDate = beginTime;
        super.setType("direct");
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
}
