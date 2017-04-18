package Classes;

public class Queue_Purchase {

    private int id;
    private int quantity;
    private double minPrice;
    private double maxPrice;

    public int getQuantity() {
        return quantity;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public int getProductID() {
        return productID;
    }

    public int getPlacerID() {
        return placerID;
    }
    private int productID;
    private int placerID;

        public Queue_Purchase(int Quantity, double minPrice, double maxPrice, int productID, int placerID)
        {
            this.quantity=quantity;
            this.minPrice=minPrice;
            this.maxPrice=maxPrice;
            this.productID = productID;
            this.placerID = placerID;
        }
        
        public void setID(int id){
            this.id = id;
        }
        
        public int getID(){
            return this.id;
        }
}