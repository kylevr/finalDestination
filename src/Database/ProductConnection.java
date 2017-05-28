/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Auctions.Auction;
import Classes.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.Connection;

/**
 *
 * @author kyle_
 */
public class ProductConnection {

    private java.sql.Connection myConn = null;
    private PreparedStatement pstmt = null;
    private ResultSet myRs = null;
    private ArrayList<Product> products;

    // Connections
    private Connection conn = new Connection();

    // SQL codes
    static final String GET_FROM_PRODUCT = "SELECT * FROM product WHERE id = ?";
    static final String GET_FROM_PRODUCTS = "SELECT * FROM product";
    static final String SET_PRODUCT_NEW = "INSERT INTO product(name, description, gtin) VALUES (?,?,?)";

    // Constructor
    public ProductConnection() {
    }

    /**
     * Get a product with a given id.
     *
     * @param productID The id of the product.
     * @return Product with the given id.
     */
    public Product getProduct(int productID) {

        Product product = null;
        int id;
        String name;
        String description;
        String gtin;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;

        if (myConn != null) {

            try {
                preparedStatement = myConn.prepareStatement(GET_FROM_PRODUCT);
                preparedStatement.setInt(1, productID);
                resultset = preparedStatement.executeQuery();
                resultset.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                id = resultset.getInt("id");
                name = resultset.getString("name");
                description = resultset.getString("description");
                gtin = resultset.getString("gtin");

                product = new Product(id, gtin, name, description);

                return product;
            } catch (SQLException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            conn.getConnection();
            getProduct(productID);
        }
        return product;
    }

    /**
     * Used to insert a new product in the database.
     *
     * @param name The name of the product.
     * @param description Text used to describe the product.
     * @param gtin Number used to insert product.
     * @return 1 if successfully inserted, 0 if it failed.
     */
    public int insertProduct(String name, String description, int gtin) {
        conn.getConnection();
        try {
            pstmt = myConn.prepareStatement(SET_PRODUCT_NEW, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, gtin);

            if (pstmt.executeUpdate() > 0) {
                int productid = 0;
                System.out.println("succesfully registered new product ");
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productid = (int) generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                conn.closeConnection();
                return productid;
            } else {
                System.out.println("Couldn't insert new queuePurchase. Rows are unaffected.");
                conn.closeConnection();
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn.closeConnection();
            return 0;
        }
    }

    /**
     * Get an arrayList with all products in the database.
     * @return ArrayList with products
     */
    public ArrayList<Product> getProducts() {
        this.products = new ArrayList<>();
        Product product;
        int id;
        String name;
        String description;
        String gtin;
        try {
            conn.getConnection();
            pstmt = myConn.prepareStatement(GET_FROM_PRODUCTS);

            myRs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (myRs.next()) {
                id = myRs.getInt("id");
                name = myRs.getString("name");
                description = myRs.getString("description");
                gtin = myRs.getString("gtin");
                product = new Product(id, gtin, name, description);

                products.add(product);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        conn.closeConnection();
        return products;
    }
}
