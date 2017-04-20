/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Auctions.Auction;
import Classes.CategoryEnum;
import Classes.Grand_Exchange;
import Classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author piete
 */
public class MainController implements Initializable {

    @FXML
    private ScrollPane auctionsPane;
    @FXML
    private ImageView loggedInUserImage;
    @FXML
    private ComboBox<CategoryEnum> comboBoxCategory;
    @FXML
    private ListView lstCategory;
    @FXML
    private ScrollPane scrollPaneBiddedAuctions;
    @FXML
    private ScrollPane scrollPaneWonBought;
    @FXML
    private Button btnQueuePurchase;
    @FXML
    private TextField textField_usernameOfFeedbackOwner;

    private Grand_Exchange GX;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(Grand_Exchange GX) {
        this.GX = GX;
        Pane allAuctions = new Pane();
        allAuctions.setPrefWidth(800);
        allAuctions.setPrefHeight(150 * GX.getAuctions().size());
        int i = 0;
        for (Auction a : GX.getAuctions()) {
            Pane Auction = new Pane();
            Auction.setPrefWidth(800);
            Auction.setPrefHeight(150);
            Auction.relocate(0, 150 * i);
            if ((i % 2) == 0) {
                Auction.setStyle("-fx-background-color: lightgrey ");
            }
            Label productName = new Label();
            productName.setText(a.getProduct().getName());
            productName.setFont(new Font("Arial", 25));
            productName.relocate(150, 25);

            Label price = new Label();
            price.setText("€" + a.getCurrentPrice());
            price.setFont(new Font("Arial", 20));
            price.relocate(550, 120);

            Label seller = new Label();
            seller.setText(a.getSeller().getUsername());
            seller.setFont(new Font("Arial", 15));
            seller.relocate(550, 20);

            TextArea description = new TextArea();
            description.setPrefSize(200, 60);
            description.relocate(150, 65);
            description.setText(a.getDescription());
            description.wrapTextProperty().setValue(Boolean.TRUE);
            description.setEditable(false);

            ImageView image = new ImageView(new Image(a.getImageURLs()[0]));
            image.setFitWidth(100);
            image.setFitHeight(100);
            image.relocate(25, 25);
            image.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    ImageView i = (ImageView) e.getSource();
                    try {
                        showAuction(a);
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            Auction.getChildren().addAll(productName, image, price, seller, description);
            allAuctions.getChildren().add(Auction);

            i++;
        }
        auctionsPane.setContent(allAuctions);

        try
        {
            loggedInUserImage.setImage(new Image(GX.loggedInUser.getImageURL()));
        }
        catch(NullPointerException ex)
        {
            System.out.println("LoggedInUser doesn't have an imageURL yet");
        }
        comboBoxCategory.getItems().setAll(CategoryEnum.values());
    }

    public void showAuction(Auction a) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Auction.fxml"));
        Scene newScene;
        newScene = new Scene(loader.load());
        AuctionController controller = loader.<AuctionController>getController();
        controller.setUp(a, GX);
        Stage inputStage = new Stage();
        inputStage.setScene(newScene);
        inputStage.showAndWait();
    }

    public void CategorySelected(Event E) {
        ComboBox C = (ComboBox) E.getSource();
        int i = C.getSelectionModel().getSelectedIndex();

        if (!lstCategory.getItems().contains(CategoryEnum.values()[i])) {
            lstCategory.getItems().add(CategoryEnum.values()[i]);

            Collections.sort(lstCategory.getItems());
        }
    }

    public void CategoryDelete(Event E) {
        ListView lst = (ListView) E.getSource();
        int selected = lstCategory.getSelectionModel().getSelectedIndex();
        if (selected >= 0 && selected < lstCategory.getItems().size()) {
            lstCategory.getItems().remove(selected);
        }
    }
    
    @FXML
    public void button_Logout() throws IOException {
        GX.logout();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        Stage stage = (Stage) auctionsPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void queuePurchaseClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/queuePurchase.fxml"));
        Scene newScene;
        newScene = new Scene(loader.load());
        QueuePurchaseController controller = loader.<QueuePurchaseController>getController();
        controller.setUp(GX);
        Stage inputStage = new Stage();
        inputStage.setScene(newScene);
        inputStage.showAndWait();
    }
    
    @FXML
    public void createAuction() throws IOException{
        try{
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CreateAuction.fxml"));
        Scene newScene;
        newScene = new Scene(loader.load());
        CreateAuctionController controller = loader.<CreateAuctionController>getController();
        controller.setUp(GX);
        Stage inputStage = new Stage();
        inputStage.setScene(newScene);
        inputStage.showAndWait();}
        catch(Exception e){e.printStackTrace();}
    }
    
    public void button_goToFeedbackOf() throws IOException {
        if (!textField_usernameOfFeedbackOwner.getText().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Profile_Feedback.fxml"));
                Parent root = loader.load();

                Profile_FeedbackController controller = (Profile_FeedbackController) loader.getController();

                controller.setUp(GX, textField_usernameOfFeedbackOwner.getText());

                Stage inputStage = new Stage();
                Scene newScene = new Scene(root);
                inputStage.setScene(newScene);
                inputStage.setTitle("Grand Exchange");
                inputStage.show();
                Stage stage = (Stage) auctionsPane.getScene().getWindow();
                stage.close();
            } catch (Exception ex) {
                System.out.println("Failed to open feedback screen");
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("textField_usernameOfFeedbackOwner may not be empty when trying to open profile feedback");
        }
    }
    
    public void btnRefresh(){
        setUp(GX);
    }
}
