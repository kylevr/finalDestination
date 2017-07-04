/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.User;
import Interfaces.IAuthorized;
import grandexchange.RegistryManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author piete
 */
public class LoginController implements Initializable {

    @FXML
    AnchorPane currentPane;

    private User user;
    private RegistryManager RM;
    private IAuthorized authorization;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RM = new RegistryManager();
        RM.getAuthorizationInterface();
        authorization = RM.getAuthorization();
        imageview_grandLogo.setImage(new Image("/Icon/scale.png"));
    }

    @FXML
    TextField textfield_username;
    @FXML
    TextField textfield_password;
    @FXML
    Label label_errorMsg;
    @FXML
    ImageView imageview_grandLogo;

    @FXML
    public void button_loginUser() throws IOException {
        //nieuwe oplossing, fix ik na unittests. (database dingen >> isVerified)
        try {

            if (!textfield_username.getText().isEmpty() || !textfield_password.getText().isEmpty()) {
                
                user = authorization.login(textfield_username.getText(), textfield_password.getText());
                System.out.println("userid " + user.getUserID());
                
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        //tijdelijke oplossing user getten
//        UserConnection userConn = new UserConnection();
//        user = userConn.getUser(textfield_username.getText(), textfield_password.getText());
        if (user != null) {
            user.setIsAuthorized(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Main.fxml"));
            Parent root = loader.load();

            MainController controller = (MainController) loader.getController();
            RM.setUser(user);
            controller.setUp(RM);

            Stage inputStage = new Stage();
            Scene newScene = new Scene(root);
            inputStage.getIcons().add(new Image("/Icon/scale.png"));
            inputStage.setScene(newScene);
            inputStage.setTitle("Grand Exchange");
            inputStage.show();
            Stage stage = (Stage) textfield_username.getScene().getWindow();
            stage.close();
        } else {
            label_errorMsg.setVisible(true);
            label_errorMsg.setText(" Our attempt to log you in failed. \n Please try again or check your connection.");
        }
    }

    public void button_exit() {
        System.exit(1);
    }

    @FXML
    public void button_registerUser() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Registration.fxml"));
        Parent root = loader.load();

        RegistrationController controller = (RegistrationController) loader.getController();
        controller.setUp(RM);

        Stage inputStage = new Stage();
        Scene newScene = new Scene(root);
        inputStage.getIcons().add(new Image("/Icon/scale.png"));
        inputStage.setScene(newScene);
        inputStage.show();
        Stage stage = (Stage) textfield_username.getScene().getWindow();
        stage.close();
    }
}
