/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.CategoryEnum;
import Classes.Grand_Exchange;
import static Controllers.QueuePurchaseController.selectedProductID;
import Interfaces.IAuction;
import Interfaces.IAuthorized;
import Interfaces.ICreateProduct;
import grandexchange.RegistryManager;
import static java.lang.Math.toIntExact;
import java.net.URL;
import java.rmi.RemoteException;
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
import javax.swing.JOptionPane;

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
    private TextArea tbDescription;
    @FXML
    private TextField tbImageUrl;
    @FXML
    private Button btCreateOption;

    private RegistryManager RM;
    private String selectedOption;
    private IAuction auctionInterface;
    private ICreateProduct productInterface;
    String auctionType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(RegistryManager RM) {
        this.RM = RM;
        cbAuctionType.getItems().addAll("Standard Auction", "Countdown Auction", "Direct Auction");
        cbAuctionType.setValue("Standard Auction");
        selectedOption = "Standard Auction";
    }

    public void createAuction() throws RemoteException {

        RM.getAuctionInterface();
        RM.getProductInterface();
        productInterface = RM.getProduct();
        auctionInterface = RM.getAuction();
        
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
            productid = productInterface.createProduct(Gtin, productName, description);
            if (productid != 0) {
                int userid = RM.getUser().getUserID();
                int instabuyable = 0;
                if (instabuy) {
                    instabuyable = 1;
                }
                int priceloweringAmount = 0;
                int priceloweringdelay = 0;
                if(auctionType.equalsIgnoreCase("countdown")){
                    priceloweringAmount = toIntExact(Math.round(startingPrice / 100));
                    priceloweringdelay = 10;
                }
                boolean succeeded = false;
                try{
                    succeeded = auctionInterface.addAuction(userid, productid, startingPrice, instabuyPrice, instabuyable, quantity, priceloweringAmount, priceloweringdelay, auctionType, 1, imageUrl, description);
                    if(succeeded){
                    JOptionPane.showMessageDialog(null, "Your auction has been added!", "InfoBox: " + "Succes", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Your auction isn't added to our database.", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Your auction isn't added to our database", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } else {
                System.out.print("Cant insert product to database.");
            }
        }
    }

    public void handleComboBoxAction(ActionEvent event) {
        selectedOption = cbAuctionType.getSelectionModel().getSelectedItem();
    }
}
