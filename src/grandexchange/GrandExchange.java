/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grandexchange;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.Product;
import Classes.User;
import Controllers.AuctionController;
import Controllers.LoginController;
import Controllers.MainController;
import java.io.IOException;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Database.*;
import java.util.ArrayList;

/**
 *
 * @author piete
 */
public class GrandExchange extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        //primaryStage.setTitle("Grand Exchange-Login");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
