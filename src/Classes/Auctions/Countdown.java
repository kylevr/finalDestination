/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Auctions;

import Classes.Product;
import Classes.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author piete
 */
public class Countdown extends Auction {

    private double priceloweringAmount;
    private double priceloweringDelay;
    private double minPrice;
    private Timestamp creationDate;

    public Countdown(User seller, Product product, int quantity, double price, double instabuyprice, double priceloweringAmount, double priceloweringDelay, double minprice, StatusEnum status, String description, String imageURLs, Timestamp creatDate) {
        super(seller, product, quantity, price, instabuyprice, status, description, imageURLs);
        this.priceloweringAmount = priceloweringAmount;
        this.priceloweringDelay = priceloweringDelay;
        this.minPrice = minprice;
        this.creationDate = creatDate;
        long now = System.currentTimeMillis();
        long then = creatDate.getTime();
        long periods = (now - then) / 60000 / (long) priceloweringDelay;
        double newPrice = price - ((int) periods * (int) priceloweringAmount);
        setCurrentPrice(newPrice);
    }

    public Countdown(int id, User seller, Product product, int quantity, double price, double priceloweringAmount, double priceloweringDelay, double minprice, StatusEnum status, String description, String imageURLs, double instabuy, Timestamp creatDate) {
        super(id, seller, product, price, quantity, status, description, imageURLs, instabuy);
        this.priceloweringAmount = priceloweringAmount;
        this.priceloweringDelay = priceloweringDelay;
        this.minPrice = minprice;
        this.creationDate = creatDate;
        long now = System.currentTimeMillis();
        long then = creatDate.getTime();
        long periods = (now - then) / 60000 / (long) priceloweringDelay;
        double newPrice = price - ((int) periods * (int) priceloweringAmount);
        setCurrentPrice(newPrice);
        
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
    
   

}
