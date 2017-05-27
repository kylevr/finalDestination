/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.User;
import Exceptions.NotEnoughMoneyException;
import Interfaces.IAuction;
import Interfaces.IPlaceBid;
import grandexchange.RegistryManager;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class AuctionController implements Initializable {

    @FXML
    private ImageView bigProductImage;
    @FXML
    private ImageView sellerImage;
    @FXML
    private Label sellerName;
    @FXML
    private Label productTitle;
    @FXML
    private Label auctiontype;
    @FXML
    private Label productStatus;
    @FXML
    private Label countdownCurrentPrice;
    @FXML
    private Label InstabuyCurrentPrice;
    @FXML
    private Label countdownAvailableUnits;
    @FXML
    private Label instabuyTextid;
    @FXML
    private Label CreateDate;
    @FXML
    private Label lblUnits;
    @FXML
    private Label lblBid;
    @FXML
    private TextArea productDescription;
    @FXML
    private TextArea auctionDescription;
    @FXML
    private Button closeButton;
    @FXML
    private Button countdownBuyBtn;
    @FXML
    private TextField txtUnitstoBuy;
    @FXML
    private ScrollPane imagesPane;
    @FXML
    private ScrollPane recentPurchasesPane;
    @FXML
    private ProgressBar minutesBar;
    @FXML
    private Button countdownBidBuyBtn;
    @FXML
    private TextField txtUnitstoBuyBid;
    @FXML
    private TextField txtPriceToBid;
    @FXML
    private Button BidBtn;

    IAuction auctionInterface;
    IPlaceBid bid;
    Auction auction;
    Direct directAuction;
    Standard standardAuction;
    private User loggedInUser;
    private String type;
    private RegistryManager RM;

    Timeline timeline;
    int timeSeconds;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUp(Auction auction, RegistryManager RM) {
        this.RM = RM;
        this.auction = auction;
        loggedInUser = RM.getUser();
        productTitle.setText(auction.getProduct().getName());
        productDescription.setText(auction.getProduct().getDescription());
        auctionDescription.setText(auction.getDescription());
        productStatus.setText(setStatus(auction.getStatus()));
        int i = 0;
        Pane imagePane = new Pane();
        imagePane.setPrefWidth(85 * auction.getImageURLs().length);
        imagePane.setPrefHeight(70);
        for (String URL : auction.getImageURLs()) {
            ImageView image = new ImageView(new Image(URL));
            image.setFitWidth(80);
            image.setFitHeight(60);
            image.relocate(85 * i, 5);
            image.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    ImageView i = (ImageView) e.getSource();
                    bigProductImage.setImage(i.getImage());
                }
            });
            bigProductImage.setImage(new Image(auction.getImageURLs()[0]));
            imagePane.getChildren().add(image);
            i++;
        }

        sellerImage.setImage(new Image(auction.getSeller().getImageURL()));
        sellerName.setText(auction.getSeller().getUsername());
        imagesPane.setContent(imagePane);

        // Checks if auction has instabuy.
        if (auction.isInstabuyable() == true) {
            countdownBuyBtn.setDisable(false);
            txtUnitstoBuy.setDisable(false);

        } else {
            //countdownBuyBtn.setDisable(true);
            //txtUnitstoBuy.setDisable(true);
        }

        //Checks if auction is of instance Countdown
        if (auction instanceof Countdown) {
            //setting auction
            auctiontype.setText("Countdown Auction");
            Countdown countdownAuction = (Countdown) auction;
            countdownAuction.setPrice();

            //setting buttons etc:
            txtPriceToBid.setVisible(false);
            txtPriceToBid.setText(countdownAuction.getCurrentPrice() + "");
            BidBtn.setText("Buy");
            BidBtn.relocate(121, 237);
            lblBid.setVisible(false);

            countdownCurrentPrice.setText("€" + countdownAuction.getCurrentPrice());
            InstabuyCurrentPrice.setText("€" + countdownAuction.getInstabuyPrice());
            long now = System.currentTimeMillis();
            long then = countdownAuction.getCreationDate().getTime();
            long periods_passed = (long) Math.floor(((now - then) / 1000 / 60 / (int) countdownAuction.getPriceLoweringDelay()));
            long next_period_begin = ((periods_passed + 1) * 1000 * 60 * (int) countdownAuction.getPriceLoweringDelay()) + countdownAuction.getCreationDate().getTime();
            Timestamp newDate = new Timestamp(next_period_begin);
            CreateDate.setText(newDate.getMonth() + "/" + newDate.getDay() + "  " + newDate.getHours() + ":" + newDate.getMinutes() + ":" + newDate.getSeconds());
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            long duration = (next_period_begin - System.currentTimeMillis()) / 1000;
            timeSeconds = (int) duration;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {

                @Override
                public void handle(Event event) {
                    timeSeconds--;
                    int minutesToGo = (int) Math.floor(timeSeconds / 60);
                    int secondsToGo = timeSeconds - (minutesToGo * 60);
                    if (secondsToGo < 10) {
                        CreateDate.setText(minutesToGo + ":0" + secondsToGo);
                    } else {
                        CreateDate.setText(minutesToGo + ":" + secondsToGo);
                    }
                    if (minutesToGo <= 0) {
                        minutesBar.setProgress(-1);
                    } else {
                        minutesBar.setProgress(timeSeconds / (countdownAuction.getPriceLoweringDelay() * 60));
                    }
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        setUp(auction, RM);
                    }
                }
            }));
            timeline.playFromStart();

            if (auction.getProductQuantity()
                    > 1) {
                countdownAvailableUnits.setText("There are " + auction.getProductQuantity() + " units available");
            } else if (auction.getProductQuantity()
                    == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (auction.getProductQuantity()
                    == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }

            setCountdownBuys(auction);

        }

        if (auction instanceof Standard) {
            //setting correct buttons:           
            txtUnitstoBuyBid.setVisible(false);
            txtUnitstoBuyBid.setText("1");
            lblUnits.setVisible(false);

            this.type = "standard";
            this.minutesBar.setVisible(false);
            auctiontype.setText("Standard Auction");
            standardAuction = (Standard) auction;
            countdownCurrentPrice.setText("€" + standardAuction.getCurrentPrice());
            InstabuyCurrentPrice.setText("€" + standardAuction.getInstabuyPrice());

            Timestamp newDate = standardAuction.getCreationDate();
            CreateDate.setText(newDate.getMonth() + "/" + newDate.getDay() + "  " + newDate.getHours() + ":" + newDate.getMinutes() + ":" + newDate.getSeconds());

            if (auction.getProductQuantity()
                    >= 1) {
                countdownAvailableUnits.setText("You're Buying " + auction.getProductQuantity() + " units for this price!");
            }

            setCountdownBuys(auction);

        }

        if (auction instanceof Direct) {
            this.type = "direct";
            auctiontype.setText("Direct Auction");
            directAuction = (Direct) auction;
            countdownCurrentPrice.setText("€" + directAuction.getCurrentPrice());
            InstabuyCurrentPrice.setText("€" + directAuction.getInstabuyPrice());

            Timestamp newDate = directAuction.getCreationDate();
            CreateDate.setText(newDate.getMonth() + "/" + newDate.getDay() + "  " + newDate.getHours() + ":" + newDate.getMinutes() + ":" + newDate.getSeconds());

            if (auction.getProductQuantity()
                    > 1) {
                countdownAvailableUnits.setText("There are " + auction.getProductQuantity() + " units available");
            } else if (auction.getProductQuantity()
                    == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (auction.getProductQuantity()
                    == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }

            setCountdownBuys(auction);

        }

        txtUnitstoBuy.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtUnitstoBuy.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /**
     * Returns textual status
     * @param status StatusEnum
     * @return 
     */
    public String setStatus(StatusEnum status) {
        switch (status) {
            case New:
                return "New";
            case GoodAsNew:
                return "As good as new";
            case Refurbished:
                return "Refurbished";
            case Used_Good:
                return "little damage(small dents)";
            case Used_Bad:
                return "annoying damage(Cracks)";
            case Broken:
                return "not fully functional or Broken";
            default:
                return "";
        }
    }

    public void countdownBuyButtonClick() throws SQLException {

        RM.getPlaceBidInterface();
        this.bid = RM.getBid();
        
        if (Integer.parseInt(txtUnitstoBuy.getText()) <= auction.getProductQuantity() && Integer.parseInt(txtUnitstoBuy.getText()) > 0 && auction != null) {
            double totalPrice = Double.parseDouble(txtUnitstoBuy.getText()) * auction.getInstabuyPrice();

            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy " + txtUnitstoBuy.getText() + "\nitems with the price of: €" + auction.getInstabuyPrice() + " a item \nand a total of: €" + totalPrice, "Are you sure?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                for (int i = 0; i < Integer.parseInt(txtUnitstoBuy.getText()); i++) {
                    auctionInterface.InstabuyItem(Integer.valueOf(txtUnitstoBuy.getText()), auction.getId(), loggedInUser.getUserID());

                }
                auction.setProductQuantity(Integer.parseInt(txtUnitstoBuy.getText()));
                setCountdownBuys(auction);
                auctionInterface.updateAuction(auction);
                if (auction.getProductQuantity() > 1) {
                    countdownAvailableUnits.setText("There are " + auction.getProductQuantity() + " units available");
                } else if (auction.getProductQuantity() == 1) {
                    countdownAvailableUnits.setText("There is just 1 item left");
                } else if (auction.getProductQuantity() == 0) {
                    countdownAvailableUnits.setText("There are no items left, you missed it");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Canceled");
            }
        } else if (Integer.parseInt(txtUnitstoBuy.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "You can't buy less than 1 object");
        } else {
            JOptionPane.showMessageDialog(null, "You can't buy more objects than there are available");
        }
    }

    // TODO: aanpassen dat het het tweede textveld goed erin zet.
    public void bidBuyButtonClick() throws SQLException, RemoteException, NotEnoughMoneyException {

        RM.getPlaceBidInterface();
        this.bid = RM.getBid();
        
        if (Integer.parseInt(txtUnitstoBuyBid.getText()) <= auction.getProductQuantity() && Integer.parseInt(txtUnitstoBuyBid.getText()) > 0 && auction != null) {
            double totalPrice = Double.parseDouble(txtUnitstoBuyBid.getText()) * auction.getCurrentPrice();
            double unitsToBuy = Double.parseDouble(txtUnitstoBuyBid.getText());
            double bedrag = Double.parseDouble(txtPriceToBid.getText());

            //int getal = Integer.parseInt(txtUnitstoBuy.getText());
            if (Double.parseDouble(txtPriceToBid.getText()) > auction.getCurrentPrice()) {
                auction.addBid(new Bid(auction.getId(), RM.getUser(), Double.parseDouble(txtPriceToBid.getText()))); // adds bid to auction
                if (bid.placeBid(unitsToBuy, auction.getId(), loggedInUser.getUserID(), bedrag)) {
                    System.out.println("Correct geinsert");
                } else {
                    System.out.println("FAILED");
                }
                System.out.println("Units To buy: " + unitsToBuy);
                System.out.println("Auction ID : " + auction.getId());
                System.out.println("User ID : " + loggedInUser.getUserID());
                System.out.println("Price To pay: " + bedrag);
                auction.setCurrentPrice(Double.parseDouble(txtPriceToBid.getText()));
                countdownCurrentPrice.setText("€" + Double.parseDouble(txtPriceToBid.getText()));

            }

            //Voegt bid to aan de auction
            //GX.addBid(Double.valueOf(txtUnitstoBuy.getText()), auction.getId(), loggedInUser.getUserID(),auction.getCurrentPrice());
            //auction.setProductQuantity(Integer.parseInt(txtUnitstoBuyBid.getText()));
            setCountdownBuys(auction);
            auctionInterface.updateAuction(auction);
            if (auction.getProductQuantity() > 1) {
                countdownAvailableUnits.setText("There are " + auction.getProductQuantity() + " units available");
            } else if (auction.getProductQuantity() == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (auction.getProductQuantity() == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }
        } else if (Integer.parseInt(txtUnitstoBuyBid.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "You can't buy less than 1 object");
        } else {
            JOptionPane.showMessageDialog(null, "You can't buy more objects than there are available");
        }
    }

    public void setCountdownBuys(Auction auction) {
        Pane BuyPane = new Pane();
        BuyPane.setPrefWidth(371);
        BuyPane.setPrefHeight(75 * auction.getBids().size());
        int a = 0;
        ArrayList<Bid> bids = auction.getBids();
        Collections.reverse(bids);
        for (Bid b : bids) {
            Pane p = new Pane();
            p.setPrefHeight(75);
            p.setPrefWidth(371);
            p.relocate(0, a * 75);
            if ((a % 2) == 0) {
                p.setStyle("-fx-background-color: lightgrey ");
            }
            Label name = new Label();
            name.setText(b.getPlacerUsername());
            name.setFont(new Font("Arial", 17));
            Label price = new Label();
            price.setText("Bought at a price of: €" + b.getAmount());
            price.setFont(new Font("Arial", 14));
            name.relocate(10, 15);
            price.relocate(10, 45);
            p.getChildren().addAll(price, name);
            BuyPane.getChildren().add(p);
            a++;
        }
        recentPurchasesPane.setContent(BuyPane);
    }

    @FXML
    private void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
