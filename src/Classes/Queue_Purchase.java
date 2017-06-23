package Classes;

import java.io.Serializable;

public class Queue_Purchase implements Serializable {

    private int id;
    private int quantity;
    private double minPrice;
    private double maxPrice;
    private int productID;
    private int placerID;

    public Queue_Purchase(int quantity, double minPrice, double maxPrice, int productID, int placerID) {
        this.quantity = quantity;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productID = productID;
        this.placerID = placerID;
    }

    /**
     * returns quantity of items to be bought
     *
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * returns minimum price for purchase
     *
     * @return double
     */
    public double getMinPrice() {
        return this.minPrice;
    }

    /**
     * returns max price for purchase
     *
     * @return double
     */
    public double getMaxPrice() {
        return this.maxPrice;
    }

    /**
     * returns id of product
     *
     * @return int
     */
    public int getProductID() {
        return this.productID;
    }

    /**
     * returns id of User(placer)
     *
     * @return int
     */
    public int getPlacerID() {
        return this.placerID;
    }

    /**
     * set id of queue purchase
     *
     * @param id: id of purcahse
     */
    public void setID(int id) {
        this.id = id;
    }
/**
 * returns id of purchase
 * @return 
 */
    public int getID() {
        return this.id;
    }
}
