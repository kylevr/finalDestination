/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Product;
import Classes.Queue_Purchase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyle_
 */
public class QueuePurchaseConnection {

    private java.sql.Connection myConn = null;
    private PreparedStatement pstmt = null;
    private ResultSet myRs = null;

    // Connections
    private Connection conn = new Connection();

    //  SQL codes
    static final String SET_QUEUEPURCHASE_NEW = "INSERT INTO queuepurchase(quantity, minprice, maxprice, productid, placerID) VALUES (?,?,?,?,?)";
    static final String GET_ALL_QUEUEPURCHASE = "SELECT * FROM queuepurchase";
    static final String GET_QUEUEPURCHASE = "SELECT * FROM queuepurchase WHERE id = ?";
    
    
    // Constructor
    public QueuePurchaseConnection() {
    }

    /**
     * Get an ArrayList with all QueuePurchases
     *
     * @return ArrayList with QueuePurchases
     */
    public ArrayList<Queue_Purchase> getQueuePurchases() {

        ArrayList<Queue_Purchase> queuepurchases = new ArrayList<>();
        Queue_Purchase queuepurchase = null;
        int id;
        int quantity;
        double minPrice;
        double maxPrice;
        int productID;
        int placerID;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        try {
            conn.getConnection();
            pstmt = conn.getMyConn().prepareStatement(GET_ALL_QUEUEPURCHASE);
            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (myRs.next()) {
                id = resultset.getInt("id");
                quantity = resultset.getInt("quantity");
                minPrice = resultset.getDouble("minprice");
                maxPrice = resultset.getDouble("maxprice");
                productID = resultset.getInt("productid");
                placerID = resultset.getInt("placerID");
                queuepurchase = new Queue_Purchase(quantity, minPrice, maxPrice, productID, placerID);
                queuepurchase.setID(id);
                queuepurchases.add(queuepurchase);
                conn.closeConnection();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Queuepurchases not retrieved from database");
        }
        
        return queuepurchases;
    }

    /**
     * Insert a QueuePurchase into the database.
     * @param quantity The ammount
     * @param minprice The minimum price.
     * @param maxprice The maximum price.
     * @param productid The id of the product.
     * @param placerid The id of the placer.
     * @return True if successfully inserted, false if not.
     */
    public Boolean insertQueuePurchase(int quantity, double minprice, double maxprice, int productid, int placerid) {
        conn.getConnection();

        try {
            //INSERT INTO queuepurchase(quantity, minprice, maxprice, productid, placerID
            pstmt = conn.getMyConn().prepareStatement(SET_QUEUEPURCHASE_NEW);
            pstmt.setInt(1, quantity);
            pstmt.setDouble(2, minprice);
            pstmt.setDouble(3, maxprice);
            pstmt.setInt(4, productid);
            pstmt.setInt(5, placerid);

            if (pstmt.executeUpdate() > 0) {
                System.out.println("succesfully registered new queuePurchase: ");
                conn.closeConnection();
                return true;
            } else {
                System.out.println("Couldn't insert new queuePurchase. Rows are unaffected.");
                conn.closeConnection();
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn.closeConnection();
            return false;
        }

    }
    
    /**
     * Deletes a Queue_Purchase with the given id.
     * @param queueID The id of the Queue_Purchase that needs to be deleted
     * @return Queue_Purchase
     */
    public void deleteQueuePurchase(int queueID) {
        
        conn.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultset;

            try {
                preparedStatement = conn.getMyConn().prepareStatement(GET_QUEUEPURCHASE);
                preparedStatement.setInt(1, queueID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            conn.getConnection();
            getQueuePurchase(queueID);
    }
    
    /**
     * Get a Queue_Purchase with the given id.
     * @param queueID The id of the Queue_Purchase
     * @return Queue_Purchase with the given id.
     */
    public Queue_Purchase getQueuePurchase(int queueID) {
        
        conn.getConnection();
        Queue_Purchase queuepurchase = null;
        int id;
        int quantity;
        double minPrice;
        double maxPrice;
        int productID;
        int placerID;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

            try {
                preparedStatement = conn.getMyConn().prepareStatement(GET_QUEUEPURCHASE);
                preparedStatement.setInt(1, queueID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                id = resultset.getInt("id");
                quantity = resultset.getInt("quantity");
                minPrice = resultset.getDouble("minprice");
                maxPrice = resultset.getDouble("maxprice");
                productID = resultset.getInt("productid");
                placerID = resultset.getInt("placerID");

                queuepurchase = new Queue_Purchase(quantity, minPrice, maxPrice, productID, placerID);
                queuepurchase.setID(id);
                conn.closeConnection();
                return queuepurchase;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            conn.getConnection();
            getQueuePurchase(queueID);
        
        return queuepurchase;
    }
}
