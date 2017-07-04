/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Auctions.Auction;
import Classes.Auctions.Countdown;
import Classes.Auctions.Direct;
import Interfaces.IAuctionInfo;
import Classes.Auctions.Standard;
import Classes.Auctions.StatusEnum;
import Classes.Bid;
import Classes.Grand_Exchange;
import Classes.User;
import Exceptions.NotEnoughMoneyException;
import Interfaces.IAuction;
import Interfaces.IPlaceBid;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import grandexchange.RegistryManager;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class AuctionController extends UnicastRemoteObject implements IRemotePropertyListener, Initializable {

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
    @FXML
    private Button buyButton;

//    IRemotePublisherForListener auctionPublisher;
    IAuction auctionInterface;
    IPlaceBid bid;
//    Auction auction;
//    Direct directAuction;
//    Standard standardAuction;
//    private User loggedInUser;
    private RegistryManager RM;
    private IAuctionInfo auctionInfo;

    private String type;
    private String productNaam;
    private String productBeschrijving;
    private String auctionBeschrijving;
    private StatusEnum status;
    private String sellerUsername;
    private String sellerProfileURL;
    private String[] auctionImageURL;

    private boolean isInstabuyable;
    private double instabuyprice;
    private int quantity;
    private int auctionId;
    private double currentprice;

    private List<Bid> biedingen;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public AuctionController() throws RemoteException {

    }

    public void setUp(IAuctionInfo auctionInfo, RegistryManager RM) throws RemoteException {
        this.RM = RM;
        this.auctionInfo = auctionInfo;
        initialize();
        auctionInfo.subscribe(this, "currentprice");
        auctionInfo.subscribe(this, "quantity");
        auctionInfo.subscribe(this, "newbid");
    }

    public void initialize() {
        try {
            type = auctionInfo.getType();
            auctionId = auctionInfo.getId();
            productNaam = auctionInfo.getProductName();
            productBeschrijving = auctionInfo.getProductDescription();
            auctionBeschrijving = auctionInfo.getDescription();
            status = auctionInfo.getStatus();
            sellerUsername = auctionInfo.getSellerName();
            sellerProfileURL = auctionInfo.getSellerImageUrl();
            auctionImageURL = auctionInfo.getImageURLs();
            isInstabuyable = auctionInfo.isInstabuyable();
            instabuyprice = auctionInfo.getInstabuyPrice();
            quantity = auctionInfo.getProductQuantity();
            currentprice = auctionInfo.getCurrentPrice();
            biedingen = auctionInfo.getBids();
            updateGui();
        } catch (Exception ex) {
            System.out.println("kon de auction niet initialiseren...");
        }

    }

    public void updateGui() {
        productTitle.setText(productNaam);
        productDescription.setText(productBeschrijving);
        auctionDescription.setText(auctionBeschrijving);
        productStatus.setText(getStatus(status));
        int i = 0;
        Pane imagePane = new Pane();
        imagePane.setPrefWidth(85 * auctionImageURL.length);
        imagePane.setPrefHeight(70);
        for (String URL : auctionImageURL) {
            if (URL != null) {
                ImageView image = null;
                try {
                    image = new ImageView(new Image(URL));
                } catch (Exception ex) {
                    image = new ImageView(new Image(this.getClass().getResource("/Classes/unavailable.jpg").toExternalForm()));
                    System.out.println("Geen image url toegevoegd.");
                }
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
                Image bigImage = null;
                try {
                    bigImage = new Image(auctionImageURL[0]);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                bigProductImage.setImage(bigImage);
                imagePane.getChildren().add(image);
                i++;
            }
        }

        try {
            sellerImage.setImage(new Image(sellerProfileURL));
        } catch (Exception ex) {

        }
        sellerName.setText(sellerUsername);
        imagesPane.setContent(imagePane);

        // Checks if auction has instabuy.
        if (isInstabuyable == true) {
            countdownBuyBtn.setDisable(false);
            txtUnitstoBuy.setDisable(false);

        } else {
            countdownBuyBtn.setDisable(true);
            txtUnitstoBuy.setDisable(true);
        }

        //Checks if auction is of instance Countdown
        if (type.equals("countdown")) {
            //setting auction
            auctiontype.setText("Countdown Auction");

            //setting buttons etc:
            txtPriceToBid.setVisible(false);
            txtPriceToBid.setText(currentprice + "");
            BidBtn.setVisible(false);
            lblBid.setVisible(false);
            txtUnitstoBuyBid.setVisible(true);
            buyButton.setVisible(true);
            lblUnits.setVisible(true);

            countdownCurrentPrice.setText("€" + currentprice);
            InstabuyCurrentPrice.setText("€" + instabuyprice);

            if (quantity
                    > 1) {
                countdownAvailableUnits.setText("There are " + quantity + " units available");
            } else if (quantity
                    == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (quantity
                    == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }

        } else if (type.equals("standard")) {
            //setting correct buttons:           
            txtPriceToBid.setVisible(true);
            BidBtn.setVisible(true);
            lblBid.setVisible(true);
            txtUnitstoBuyBid.setVisible(false);
            buyButton.setVisible(false);
            lblUnits.setVisible(false);

            this.type = "standard";
            this.minutesBar.setVisible(false);
            auctiontype.setText("Standard Auction");
            countdownCurrentPrice.setText("€" + currentprice);
            InstabuyCurrentPrice.setText("€" + instabuyprice);

            if (quantity
                    >= 1) {
                countdownAvailableUnits.setText("You're Buying " + quantity + " units for this price!");
            }
        }

        if (type.equals("direct")) {

            auctiontype.setText("Direct Auction");
            countdownCurrentPrice.setText("€" + currentprice);
            InstabuyCurrentPrice.setText("€" + instabuyprice);

            if (quantity
                    > 1) {
                countdownAvailableUnits.setText("There are " + quantity + " units available");
            } else if (quantity
                    == 1) {
                countdownAvailableUnits.setText("There is just 1 item left");
            } else if (quantity
                    == 0) {
                countdownAvailableUnits.setText("There are no items left, you missed it");
            }
        }

        updateBids();
    }

    public void updateBids() {

        Pane BuyPane = new Pane();
        BuyPane.setPrefWidth(371);
        BuyPane.setPrefHeight(75 * biedingen.size());
        int a = 0;
        for (Bid b : biedingen) {
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
            if (b.getAmount() >= instabuyprice || instabuyprice != 0) {
                price.setText("Bought at a price of: €" + b.getAmount());
            } else {
                price.setText("Bidded: €" + b.getAmount() + " for this item");
            }
            price.setFont(new Font("Arial", 14));
            name.relocate(10, 15);
            price.relocate(10, 45);
            p.getChildren().addAll(price, name);
            BuyPane.getChildren().add(p);
            a++;
        }
        recentPurchasesPane.setContent(BuyPane);
    }

    /**
     * Returns textual status
     *
     * @param status StatusEnum
     * @return
     */
    public String getStatus(StatusEnum status) {
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

    public void buyButtonClick() throws RemoteException, NotEnoughMoneyException, SQLException {
//        RM.getPlaceBidInterface();
//        this.bid = RM.getBid();
//        System.out.println("Saldo = " + RM.getUser().getSaldo());
//        RM.getUser().getSaldo();
//
//        double totalprice = Double.parseDouble(txtUnitstoBuyBid.getText()) * auction.getCurrentPrice();
//        if (RM.getUser().getSaldo() < totalprice) {
//            JOptionPane.showMessageDialog(null, "You don't have enough money on your account to perform this action.");
//        } else {
//            if (Integer.parseInt(txtUnitstoBuyBid.getText()) >= 1 && Integer.parseInt(txtUnitstoBuyBid.getText()) <= auction.getProductQuantity()) {
//                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to Buy " + txtUnitstoBuyBid.getText() + " items with a total price of: €" + totalprice + " ?", "Are You Sure?", JOptionPane.YES_NO_OPTION);
//                if (reply == JOptionPane.YES_OPTION) {
//                    int units = Integer.parseInt(txtUnitstoBuyBid.getText());
//                    bid.placeBuy(units, loggedInUser.getUsername(), auction.getId(), auction.getCurrentPrice());
//                    //update();
//                    JOptionPane.showMessageDialog(null, "Your order has been placed");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Canceled");
//                }
//            } else if (Integer.parseInt(txtUnitstoBuyBid.getText()) < 1) {
//                JOptionPane.showMessageDialog(null, "You can't buy less than 1 object");
//            } else if (Integer.parseInt(txtUnitstoBuyBid.getText()) > auction.getProductQuantity()) {
//                JOptionPane.showMessageDialog(null, "You can't buy more objects than there are available");
//            } else {
//                JOptionPane.showMessageDialog(null, "Something went wrong");
//            }
//        }
    }

    public void bidButtonClick() throws RemoteException, NotEnoughMoneyException, SQLException {
        RM.getPlaceBidInterface();
        this.bid = RM.getBid();
        double price = Double.parseDouble(txtPriceToBid.getText());
        if (currentprice < price) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to Bid " + txtPriceToBid.getText(), "Are You Sure?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if (bid.placeBid(1, RM.getUser().getUserID(), auctionId, price)) {
                    JOptionPane.showMessageDialog(null, "Your bid has been placed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong while placing your bid.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Canceled");
            }
        } else if (currentprice > Double.parseDouble(txtPriceToBid.getText())) {
            JOptionPane.showMessageDialog(null, "You can't bid less than current price");
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }
    }

    public void instabuyButtonClick() throws RemoteException, NotEnoughMoneyException, SQLException {
//        RM.getPlaceBidInterface();
//        this.bid = RM.getBid();
//
//        if (Integer.parseInt(txtUnitstoBuy.getText()) <= auction.getProductQuantity() && Integer.parseInt(txtUnitstoBuy.getText()) > 0 && auction != null) {
//            double totalPrice = Double.parseDouble(txtUnitstoBuy.getText()) * auction.getInstabuyPrice();
//
//            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy " + txtUnitstoBuy.getText() + "\nitems with the price of: €" + auction.getInstabuyPrice() + " a item \nand a total of: €" + totalPrice, "Are you sure?", JOptionPane.YES_NO_OPTION);
//            if (reply == JOptionPane.YES_OPTION) {
//                int units = Integer.parseInt(txtUnitstoBuy.getText());
//                bid.placeBid(units, RM.getUser().getUserID(), auction.getId(), auction.getInstabuyPrice());
//                //update();
//            } else {
//                JOptionPane.showMessageDialog(null, "Canceled");
//            }
//        } else if (Integer.parseInt(txtUnitstoBuy.getText()) <= 0) {
//            JOptionPane.showMessageDialog(null, "You can't buy less than 1 object");
//        } else {
//            JOptionPane.showMessageDialog(null, "You can't buy more objects than there are available");
//        }
    }

    @FXML
    private void closeButtonClick() {
        try {
            auctionInfo.unSubscribe(this, "currentprice");
            auctionInfo.unSubscribe(this, "quantity");
            auctionInfo.unSubscribe(this, "newbid");
        } catch (RemoteException ex) {
            Logger.getLogger(AuctionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        switch (evt.getPropertyName()) {
            case "currentprice":
                currentprice = (Double) evt.getNewValue();
                break;
            case "quantity":
                quantity = (Integer) evt.getNewValue();
                break;
            case "newbid":
                Bid newBid = (Bid) evt.getNewValue();
                biedingen.add(newBid);
                break;
        }
        updateGui();
    }

}
