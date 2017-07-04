/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Auctions.Auction;
import Classes.CategoryEnum;
import Interfaces.IAuction;
import grandexchange.RegistryManager;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

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
    private Button btnQueuePurchase;
    @FXML
    private TextField textField_usernameOfFeedbackOwner;

    private IAuction auctionInterface;
    private RegistryManager RM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUp(RegistryManager RM) throws RemoteException {
        this.RM = RM;
        RM.getAuctionInterface();
        auctionInterface = RM.getAuction();
        this.refreshAuctions();
        try {
            loggedInUserImage.setImage(new Image(RM.getUser().getImageURL()));
        } catch (NullPointerException ex) {
            loggedInUserImage.setImage(new Image(this.getClass().getResource("/Classes/unavailable.jpg").toExternalForm()));

            System.out.println("LoggedInUser doesn't have an imageURL yet");

        }
        comboBoxCategory.getItems().setAll(CategoryEnum.values());
    }

    public void refreshAuctions() throws RemoteException {
        Task taskCalc = new Task<Pane>() {
            @Override
            public Pane call() throws RemoteException {
                //get auctions
                long start = System.currentTimeMillis();
                Collection<Auction> auctions = auctionInterface.getAuctions();
                long elapsedTime = System.currentTimeMillis() - start;
                System.out.println("PerformanceGetAuctionsInMS=" + elapsedTime);

                //draw auctions
                start = System.currentTimeMillis();
                Pane allAuctions = new Pane();
                allAuctions.setPrefWidth(800);
                allAuctions.setPrefHeight(150 * auctions.size());
                int i = 0;
                for (Auction a : auctions) {
                    try {
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

                        //setting image of auction
                        ImageView image;
                        try {
                            image = new ImageView(new Image(a.getImageURLs()[0]));
                        } catch (Exception ex) {
                            image = new ImageView(new Image(this.getClass().getResource("/Classes/unavailable.jpg").toExternalForm()));
                        }
                        image.setFitWidth(100);
                        image.setFitHeight(100);
                        image.relocate(25, 25);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
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
                    } catch (Exception ex) {
                    }
                }
                elapsedTime = System.currentTimeMillis() - start;
                System.out.println("PerformanceSetAuctionsPaneInMS=" + elapsedTime);

                return allAuctions;
            }
        };

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        try {
                            Thread auctionCalcThread = new Thread(taskCalc);
                            auctionCalcThread.start();
                            auctionsPane.setContent((Pane)taskCalc.get());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }).start();
    }

    public void showAuction(Auction a) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Auction.fxml"));
        Scene newScene;
        newScene = new Scene(loader.load());
        AuctionController controller = loader.<AuctionController>getController();
        controller.setUp(a, this.RM);
        Stage inputStage = new Stage();
        inputStage.getIcons().add(new Image("/Icon/scale.png"));
        inputStage.setScene(newScene);
        inputStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage X Disabled");
                we.consume();
                JOptionPane.showMessageDialog(null, "Please use the close button located on the top left of the screen");
            }
        });
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
        int selected = lstCategory.getSelectionModel().getSelectedIndex();
        if (selected >= 0 && selected < lstCategory.getItems().size()) {
            lstCategory.getItems().remove(selected);
        }
    }

    @FXML
    public void button_Logout() throws IOException {
        RM.getAuthorization().logout(RM.getUser().getUsername());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image("/Icon/scale.png"));

        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.setScene(new Scene(root, Color.TRANSPARENT));
        newStage.show();

        Stage stage = (Stage) auctionsPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void queuePurchaseClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/queuePurchase.fxml"));
        Scene newScene;
        newScene = new Scene(loader.load());
        QueuePurchaseController controller = loader.<QueuePurchaseController>getController();
        controller.setUp(RM);
        Stage inputStage = new Stage();
        inputStage.getIcons().add(new Image("/Icon/scale.png"));
        inputStage.setScene(newScene);
        inputStage.showAndWait();
    }

    @FXML
    public void createAuction() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CreateAuction.fxml"));
            Scene newScene;
            newScene = new Scene(loader.load());
            CreateAuctionController controller = loader.<CreateAuctionController>getController();
            controller.setUp(RM);
            Stage inputStage = new Stage();
            inputStage.getIcons().add(new Image("/Icon/scale.png"));
            inputStage.setScene(newScene);
            inputStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(AuctionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void button_goToFeedbackOf() throws IOException {
        if (!textField_usernameOfFeedbackOwner.getText().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Profile_Feedback.fxml"));
                Parent root = loader.load();
                Profile_FeedbackController controller = (Profile_FeedbackController) loader.getController();
                controller.setUp(RM, textField_usernameOfFeedbackOwner.getText());
                Stage inputStage = new Stage();
                Scene newScene = new Scene(root);
                inputStage.getIcons().add(new Image("/Icon/scale.png"));
                inputStage.setScene(newScene);
                inputStage.setTitle("Grand Exchange");
                inputStage.show();
                Stage stage = (Stage) auctionsPane.getScene().getWindow();
                stage.close();
            } catch (Exception ex) {
                System.out.println("Failed to open feedback screen");
                ex.printStackTrace();
                Logger.getLogger(AuctionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("textField_usernameOfFeedbackOwner may not be empty when trying to open profile feedback");
        }
    }

    public void button_exit() {
        Runtime.getRuntime().halt(1);
    }

    public void btnRefresh() {
        try {
            this.refreshAuctions();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
