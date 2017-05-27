/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.CategoryEnum;
import Classes.Grand_Exchange;
import Classes.Product;
import Interfaces.ICreateProduct;
import Interfaces.ICreateQueuePurchase;
import grandexchange.RegistryManager;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author SwekLord420
 */
public class QueuePurchaseController implements Initializable {
    
    @FXML
    private ListView lstCategory;
    
    @FXML
    private Label lblProductName;
    
    @FXML
    private ScrollPane productsPane;
    
    @FXML
    private TextField textFieldProductName;
    
    @FXML
    private TextField txtQuantity;
    
    @FXML
    private TextField txtMinPrice;
    
    @FXML
    private TextField txtMaxPrice;
    
    @FXML
    private ComboBox<CategoryEnum> comboBoxCategory;
    
    public static int selectedProductID;
    private ICreateProduct productInterface;
    private ICreateQueuePurchase queuePurchaseInterface;
    private RegistryManager RM;
    private ArrayList<Product> products;
    private CategoryEnum selectedCategory;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(RegistryManager RM) {
        this.RM = RM;
        comboBoxCategory.getItems().setAll(CategoryEnum.values());
    }
    
    public void searchProduct(){
        
        RM.getProductInterface();
        productInterface = RM.getProduct();
        
        this.products = productInterface.getProducts(textFieldProductName.getText(),this.selectedCategory);
        Pane allProducts = new Pane();
        allProducts.setPrefWidth(800);
        System.out.println(textFieldProductName.getText());
        allProducts.setPrefHeight(150 * this.products.size());
        int i = 0;
        for (Product p : this.products) {
            Pane Product = new Pane();
            Product.setPrefWidth(800);
            Product.setPrefHeight(150);
            Product.relocate(0, 150 * i);
            if ((i % 2) == 0) {
                Product.setStyle("-fx-background-color: lightgrey ");
            }
            Label productName = new Label();
            productName.setText(p.getName());
            productName.setFont(new Font("Arial", 25));
            productName.relocate(150, 25);

            TextArea description = new TextArea();
            description.setPrefSize(200, 60);
            description.relocate(150, 65);
            description.setText(p.getDescription());
            description.wrapTextProperty().setValue(Boolean.TRUE);
            description.setEditable(false);

            ImageView image = new ImageView(new Image("https://cdn3.iconfinder.com/data/icons/modern-future-technology/128/3d-512.png"));
            image.setFitWidth(100);
            image.setFitHeight(100);
            image.relocate(25, 25);
            image.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    ImageView i = (ImageView) e.getSource();
                    lblProductName.setText(p.getName() + " " + p.getGTIN());
                    selectedProductID = p.getId();
                    System.out.println(p.getId());
                }
            });

            Product.getChildren().addAll(productName, image, description);
            allProducts.getChildren().add(Product);

            i++;
        }
        productsPane.setContent(allProducts);
    }

    public void CategoryDelete(Event E) {
        ListView lst = (ListView) E.getSource();
        int selected = lstCategory.getSelectionModel().getSelectedIndex();
        if (selected >= 0 && selected < lstCategory.getItems().size()) {
            lstCategory.getItems().remove(selected);
            lstCategory.setUserData(lst);
        }
    }

    public void CategorySelected(Event E) {
        ComboBox C = (ComboBox) E.getSource();
        int i = C.getSelectionModel().getSelectedIndex();

        if (!lstCategory.getItems().contains(CategoryEnum.values()[i])) {
            lstCategory.getItems().add(CategoryEnum.values()[i]);

            Collections.sort(lstCategory.getItems());
        }
    }

    public void createQueuePurchase() throws RemoteException{
        
        
        
        if (txtQuantity.getText().equals("") || txtMinPrice.getText().equals("") || txtMaxPrice.getText().equals("") || selectedProductID == 0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Wat de F doe jij nou weer");
            alert.setHeaderText("This is a header WOO !");
            alert.setContentText("There are still empty fields ! fgt");

            alert.showAndWait();
        }
        else{
            int quantity = Integer.parseInt(txtQuantity.getText());
            double minAmount = Double.parseDouble(txtMinPrice.getText());
            double maxAmount = Double.parseDouble(txtMaxPrice.getText());
            queuePurchaseInterface.createQueuePurchase(quantity, minAmount, maxAmount, selectedProductID, RM.getUser().getUserID());
        }
    }
    
    
    
    public void handleComboBoxAction(ActionEvent event) {
        selectedCategory = comboBoxCategory.getSelectionModel().getSelectedItem();
 }

}
