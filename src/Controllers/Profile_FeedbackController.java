/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Grand_Exchange;
import Classes.User;
import Database.Connection;
import grandexchange.RegistryManager;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gebruiker
 */
public class Profile_FeedbackController implements Initializable {

    Grand_Exchange GX;
    RegistryManager RM;
    User feedbackOwner;
    
    @FXML
    AnchorPane currentPane;
    @FXML
    ListView listview_profile_feedback; 
    @FXML
    Label label_owner;    
    @FXML
    Label label_submitter;
    @FXML
    Slider slider_rating;
    @FXML
    TextField textfield_description;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUp(RegistryManager RM, String userNameFeedback) throws RemoteException {
        this.RM = RM;
        /*
        * Hier iets maken waardoor er een user kan worden opgehaald waar je feedback op wilt geven ?
        */        
        
        // this.feedbackOwner = ?????????
        this.refreshFeedbacklist();
        this.label_owner.setText(feedbackOwner.getUsername() + "'s feedback");
        this.label_submitter.setText(GX.loggedInUser.getUsername() + "'s feedback to " + this.feedbackOwner.getUsername());
    }
    
    @FXML
    public void button_submitFeedback()
    {
        Connection conn = new Connection();
        conn.submitFeedback((int)Math.round(slider_rating.getValue()), textfield_description.getText(), feedbackOwner.getUsername(), GX.loggedInUser.getUsername());
        this.refreshFeedbacklist();
    }
    
    @FXML
    public void refreshFeedbacklist()
    {
        this.GX.updateFeedbacklist(feedbackOwner.getUsername());
        listview_profile_feedback.getItems().setAll(feedbackOwner.getFeedbackToMe());
    }
    
    @FXML
    public void button_cancelFeedback()
    {
        this.textfield_description.clear();
        this.slider_rating.setValue(1);
    }
    
    @FXML
    public void button_goBack() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Main.fxml"));
            Parent root = loader.load();
            
            MainController controller = (MainController) loader.getController();
            controller.setUp(RM);

            Stage inputStage = new Stage();
            Scene newScene = new Scene(root);
            inputStage.setScene(newScene);
            inputStage.setTitle("Grand Exchange");
            inputStage.show();
            Stage stage = (Stage) currentPane.getScene().getWindow();
            stage.close();
    }
}
