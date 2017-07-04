/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author piete
 */
public class Countdown extends Auction implements Serializable {

    private double priceloweringAmount;
    private double priceloweringDelay;
    private double minPrice;
    private double startingPrice;
    private  Timestamp creationDate;

    public Countdown(User seller, Product product, int quantity, double price, double instabuyprice, double priceloweringAmount, double priceloweringDelay, double minprice, StatusEnum status, String description, String imageURLs, Timestamp creatDate) throws RemoteException {
        super(seller, product, quantity, price, instabuyprice, status, description, imageURLs);
        this.priceloweringAmount = priceloweringAmount;
        this.priceloweringDelay = priceloweringDelay;
        this.startingPrice = price;
        this.minPrice = minprice;
        this.creationDate = creatDate;
        setPrice();
        super.setType("countdown");
        super.publisher.registerProperty("remainingtime");
    }

    public Countdown(int id, User seller, Product product, int quantity, double price, double priceloweringAmount, double priceloweringDelay, double minprice, StatusEnum status, String description, String imageURLs, double instabuy, Timestamp creatDate) throws RemoteException {
        super(id, seller, product, price, quantity, status, description, imageURLs, instabuy);
        this.priceloweringAmount = priceloweringAmount;
        this.priceloweringDelay = priceloweringDelay;
        this.startingPrice = price;
        this.minPrice = minprice;
        this.creationDate = creatDate;
        setPrice();
        super.setType("countdown");

    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public double getPriceLoweringAmount() {
        return priceloweringAmount;
    }

    public double getPriceLoweringDelay() {
        return priceloweringDelay;
    }

    public void setPrice() {
        long now = System.currentTimeMillis();
        long then = creationDate.getTime();
        
        if(priceloweringDelay > 0){
            long periods = (now - then) / 60000 / (long) priceloweringDelay;
        double newPrice = startingPrice - ((int) periods * (int) priceloweringAmount);
        if(newPrice < 0.01){
            setCurrentPrice(0.01);
        }else{
            setCurrentPrice(newPrice);
        }
        }
    }

}
