/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author SwekLord420
 */
public class DatabaseListener extends Observable{

    ArrayList<Integer> auctionIdList;
    ArrayList<Integer> queueIdList;
    private ResultSet myRs = null;
    AuctionListener auctionlistener;
    QueuePurchaseListener queueListener;
    public String type;
    

    public DatabaseListener() {
        this.auctionIdList = new ArrayList<>();
        this.queueIdList = new ArrayList<>();
        
            try {
                auctionlistener = new AuctionListener(getConnection());
                queueListener = new QueuePurchaseListener(getConnection());
            } catch (SQLException ex) {
                            Logger.getLogger(DatabaseListener.class.getName()).log(Level.SEVERE, null, ex);
System.out.println(ex.getMessage());
                System.out.println("Can't create database listener Object.");
            }
        
        auctionlistener.start();
        queueListener.start();
    }

    private void updateAuctionList() {
        ResultSet tempResultSet;
        java.sql.Connection tempCon = getConnection();
        CallableStatement tempStatement;

        try {
            tempStatement = tempCon.prepareCall("{call get_updated_auction_ids()}");
            tempStatement.execute();
            tempResultSet = tempStatement.getResultSet();
            if (tempResultSet != null){
            while (tempResultSet.next()) {
                auctionIdList.add(tempResultSet.getInt("auctionID"));
                System.out.println(tempResultSet.getInt("auctionID"));
            }
            
            }
            else{
                System.out.println("resultSet of NEW auction id's is empty ?!");
            }
            tempStatement.closeOnCompletion();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateQueueList() {
        ResultSet tempResultSet;
        java.sql.Connection tempCon = getConnection();
        CallableStatement tempStatement;

        try {
            tempStatement = tempCon.prepareCall("{call get_updated_queuepurchases()}");
            tempStatement.execute();
            tempResultSet = tempStatement.getResultSet();
            if (tempResultSet != null){
            while (tempResultSet.next()) {
                queueIdList.add(tempResultSet.getInt("queueID"));
            }
            }
            else{
                System.out.println("resultSet of NEW queuepurchase id's is empty ?!");
            }
            tempStatement.closeOnCompletion();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<Integer> getUpdateAuctionList(){
        return this.auctionIdList;
    }
    
    public ArrayList<Integer> getUpdateQueuepurchaseList(){
        return this.queueIdList;
    }

    public Connection getConnection() {
        try {
            Connection tempConnection;
            Class.forName("com.mysql.jdbc.Driver");
            tempConnection = DriverManager.getConnection("jdbc:mysql://vserver213.axc.nl:3306/lesleya213_pts?noAccessToProcedureBodies=true", "lesleya213_pts", "wachtwoord123");
            System.out.println("Database Listener connection succesfully started...");
            return tempConnection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Listener failed to start connection to database...");
            return null;
        }
    }

    class AuctionListener extends Thread  {

        private java.sql.Connection conn;
        CallableStatement myStmt;

        AuctionListener(java.sql.Connection conn) throws SQLException {

            this.conn = conn;
            this.myStmt = conn.prepareCall("{call get_count(?)}");
            myStmt.registerOutParameter(1, Types.INTEGER);

        }

        @Override
        public void run() {
            while (true) {
                boolean execute = true;
                try {

                    if(execute == myStmt.execute()){
                        System.out.println("resultSet Found !");
                    }

                    if (myStmt.getInt(1) >= 1) {
                        type = "Auction";
                        updateAuctionList();
                        setChanged();
                        notifyObservers(type);
                        getUpdateAuctionList().clear();
                    } else {
                        System.out.println(myStmt.getInt(1));
                        System.out.println("No new auctions !");
                    }

                    Thread.sleep(5000);
                } catch (Exception exc) {
                                Logger.getLogger(Database.Connection.class.getName()).log(Level.SEVERE, null, exc);
System.out.println(exc.getMessage());
                    close(conn, myStmt);
                    System.out.println("Listener Thread interrupted.");
                }
            }
        }

        private void close(Connection myConn, Statement myStmt) {
            if (myStmt != null) {
                try {
                    myStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AuctionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (myConn != null) {
                try {
                    myConn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AuctionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    
        class QueuePurchaseListener extends Thread {

        private Connection conn;
        CallableStatement myStmt;

        QueuePurchaseListener(java.sql.Connection conn) throws SQLException {

            this.conn = conn;
            this.myStmt = conn.prepareCall("{call checkQueue(?)}");
            myStmt.registerOutParameter(1, Types.INTEGER);

        }

        @Override
        public void run() {
            while (true) {
                boolean execute = true;
                try {

                    if(execute == myStmt.execute()){
                        System.out.println("resultSet Found !");
                    }

                    if (myStmt.getInt(1) >= 1) {
                        type = "Queue";
                        updateQueueList();
                        setChanged();
                        notifyObservers(type);
                        getUpdateQueuepurchaseList().clear();
                    } else {
                        System.out.println(myStmt.getInt(1));
                        System.out.println("No new queuePurchases !");
                    }

                    Thread.sleep(5000);
                } catch (Exception exc) {
                                Logger.getLogger(Database.Connection.class.getName()).log(Level.SEVERE, null, exc);
System.out.println(exc.getMessage());
                    close(conn, myStmt);
                    System.out.println("Listener Thread interrupted.");
                }
            }
        }

        private void close(Connection myConn, Statement myStmt) {
            if (myStmt != null) {
                try {
                    myStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AuctionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (myConn != null) {
                try {
                    myConn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AuctionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
