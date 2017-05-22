/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Grand_Exchange;
import Classes.User;
import Database.Connection;
import javafx.scene.paint.Color;
import static java.awt.SystemColor.text;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gebruiker
 */
public class RegistrationController implements Initializable {

    private Database.Connection conn = new Database.Connection();
    @FXML
    AnchorPane currentPane; //id you've given to the backgroundpane of the .FXML scene

    @FXML
    TextField textfield_username;
    @FXML
    TextField textfield_password;
    @FXML
    TextField textfield_email;
    @FXML
    TextField textfield_alias;
    @FXML
    Label label_errorMsg;

    String errorMsg;
    Grand_Exchange GX;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void button_registerUser() throws IOException {
            this.label_errorMsg.setText(this.GX.registerUser(textfield_username.getText(), textfield_password.getText(), textfield_alias.getText(), textfield_email.getText()));
            this.label_errorMsg.setVisible(true);
    }

    @FXML
    public void button_goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        Stage stage = (Stage) currentPane.getScene().getWindow();
        stage.close();
    }
    
    public void Setup(Grand_Exchange GX)
    {
        this.GX = GX;
    }
}
