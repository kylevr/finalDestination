/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Grand_Exchange;
import Classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.annotation.Resource;

/**
 *
 * @author piete
 */
public class LoginController implements Initializable {

    @FXML
    AnchorPane currentPane;

    Grand_Exchange GX;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GX = new Grand_Exchange();
    }

    @FXML
    TextField textfield_username;
    @FXML
    TextField textfield_password;
    @FXML
    Label label_errorMsg;

    @FXML
    public void button_loginUser() throws IOException {
        if (GX.login(textfield_username.getText(), textfield_password.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Main.fxml"));
            Parent root = loader.load();
            
            MainController controller = (MainController) loader.getController();
            controller.setUp(GX);

            Stage inputStage = new Stage();
            Scene newScene = new Scene(root);
            inputStage.setScene(newScene);
            inputStage.setTitle("Grand Exchange");
            inputStage.show();
            Stage stage = (Stage) textfield_username.getScene().getWindow();
            stage.close();
        }
        else
        {
            label_errorMsg.setVisible(true);
            label_errorMsg.setText("Failed to login. \n try a different username/password \n or \n check your internet connection");
        }
    }

    @FXML
    public void button_registerUser() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Registration.fxml"));
        Parent root = loader.load();
        newStage.setScene(new Scene(root));
        newStage.show();
        Stage stage = (Stage) currentPane.getScene().getWindow();
        stage.close();
        //Stage currentStage = (Stage)currentPane.getScene().getWindow();
        //currentStage.close();
    }
}
