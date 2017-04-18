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
        //   Connection conn = new Connection();
        //  conn.getConnection();
        //User user1 = conn.getUser(000001);
        //User user2 = conn.getUser("user2", "password");
//working insert
//INSERT INTO user(bsn, username, password, alias, email, verified, saldo) VALUES (6, 'user6', 'password', 'xUser6', 'user6@example.com', 0, 600.0);
        // conn.setUser_REGISTER(000007, "user7", "password", "xUser7", "user7@example.com", 700);
/*
        ArrayList<Auction> auctions = new ArrayList<>();
        User user = new User("Kyle", "PassWW", "https://www.aap.nl/uploads/Regina.jpg");
        User user7 = new User("Pieter", "PassWW", "https://www.aap.nl/uploads/Linda_1.jpg");
        User user3 = new User("Robin", "PassWW", "https://www.hmcdn.eu/ImageRepository/Converted/310/3109b54c-90b4-4463-8435-bd533a69cf38.jpg.ashx?w=300");
        User user4 = new User("Lesley", "PassWW", "http://media.mkbservicedesk.nl/3b6b898c3880e711b48725d00826606f.jpg");
        User user5 = new User("Jorian", "PassWW", "https://pbs.twimg.com/profile_images/2284561206/image_400x400.jpg");
        User user6 = new User("Sam", "PassWW", "http://www.lachkaarten.com/images/kaarten/armeaap.jpg");
        Date date = new Date();
        Product product = new Product("0479588001", "Audi R8 4.2 V8 FSI CARBON", "De Audi R8 Coupé is de benchmark. Met een vermogen van 610 pk, een topsnelheid van 330 km/h en een acceleratie van 0 naar 100 km/h in 3,2 is de topversie - de Audi R8 V10 plus - goed voor adembenemende prestaties. Het scherpe design past daar goed bij. De Audi R8 Coupé is onmiskenbaar een bolide met race-DNA.");
        Auction auction = new Countdown(1, user, product, 59999, 1, 1, 600, 10, StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.", "https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
        Bid bid = new Bid(user7, 60000);
        Bid bid2 = new Bid(user, 61000);
        Bid bid3 = new Bid(user3, 62000);
        Bid bid4 = new Bid(user4, 63000);
        Bid bid5 = new Bid(user5, 64000);
        Bid bid6 = new Bid(user6, 65000);
        auction.addBid(bid6);
        auction.addBid(bid5);
        auction.addBid(bid4);
        auction.addBid(bid3);
        auction.addBid(bid2);
        auction.addBid(bid);
        Auction auction2 = new Countdown(2,user4, product, 59999, 1, 1, 600, 10, StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.", "https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
        Auction auction3 = new Countdown(3,user3, product, 59999, 1, 1, 600, 10, StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.", "https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
        Auction auction4 = new Countdown(5,user5, product, 59999, 1, 1, 600, 10, StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.", "https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
        Auction auction5 = new Countdown(6,user7, product, 59999, 1, 1, 600, 10, StatusEnum.GoodAsNew, "Deze audi verkeerd in zeer goede staat, geen krasjes,deukjes. De binnenkant verkeerd eveneens in uitstekende staat.", "https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/TtgAAOSwDmBY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/tUIAAOSwA29Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/OmMAAOSww3tY39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/pK0AAOSwCU1Y39YC/$_85.JPG;https://i.marktplaats.com/00/s/NjgzWDEwMjQ=/z/Mh0AAOSwTM5Y39YB/$_85.JPG");
        auctions.add(auction);
        auctions.add(auction2);
        auctions.add(auction3);
        auctions.add(auction4);
        auctions.add(auction5);
         */
 /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Auction.fxml"));
        Parent root = loader.load();
        AuctionController controller = loader.<AuctionController>getController();
        
        controller.setAuction(auction);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Auction");
        primaryStage.show();
         */
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
