/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.CategoryEnum;
import Classes.Grand_Exchange;
import static Controllers.QueuePurchaseController.selectedProductID;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lesley
 */
public class CreateAuctionController implements Initializable {

    @FXML
    private ComboBox<String> cbAuctionType;
    @FXML
    private TextField tbAuctionTitle;
    @FXML
    private TextField tbGtin;
    @FXML
    private TextField tbStartingPrice;
    @FXML
    private CheckBox cbInstaBuy;
    @FXML
    private TextField tbInstabuyPrice;
    @FXML
    private TextField tbQuantity;
    @FXML
    private TextField tbQQuantity;
    @FXML
    private TextArea tbDescription;
    @FXML
    private TextField tbImageUrl;
    @FXML
    private Button btCreateOption;

    private Grand_Exchange GX;
    private String selectedOption;
    String auctionType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(Grand_Exchange GX) {
        this.GX = GX;
        cbAuctionType.getItems().addAll("Standard Auction", "Countdown Auction", "Direct Auction");
        cbAuctionType.setValue("Standard Auction");
        selectedOption = "Standard Auction";
    }

    public void createAuction() {

        if ("Standard Auction".equals(selectedOption)) {
            auctionType = "standard";
        }
        if ("Countdown Auction".equals(selectedOption)) {
            auctionType = "countdown";
        }
        if ("Direct Auction".equals(selectedOption)) {
            auctionType = "direct";
        }
        String productName = tbAuctionTitle.getText();
        int Gtin = Integer.parseInt(tbGtin.getText());
        double startingPrice = Double.parseDouble(tbStartingPrice.getText());
        boolean instabuy = cbInstaBuy.isSelected();
        double instabuyPrice = 0;
        if (instabuy) {
            instabuyPrice = Double.parseDouble(tbInstabuyPrice.getText());
        }
        String amount = tbQuantity.getText();
        int quantity = Integer.parseInt(amount);
        String description = tbDescription.getText();
        String imageUrl = tbImageUrl.getText();

        if ("".equals(cbAuctionType.getValue()) || "".equals(productName) || "".equals(tbGtin.getText()) || "".equals(tbStartingPrice.getText()) || "".equals(tbQuantity.getText()) || "".equals(description)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Field Error");
            alert.setHeaderText("You didn't enter all required fields.");
            alert.setContentText("Please fill in all the required fields and try again.");

            alert.showAndWait();
        } else {
            int productid;
            productid = GX.addProductToDB(productName, description, Gtin);
            if (productid != 0) {
                int userid = GX.getLoggedInUser().getUserID();
                int instabuyable = 0;
                if (instabuy) {
                    instabuyable = 1;
                }
                GX.addAuctionToDB(userid, productid, startingPrice, instabuyPrice, instabuyable, quantity, 0, 0, auctionType, 1, imageUrl, description);
            } else {
                System.out.print("Cant insert product to database.");
            }
        }
    }

    public void handleComboBoxAction(ActionEvent event) {
        selectedOption = cbAuctionType.getSelectionModel().getSelectedItem();
    }
}
