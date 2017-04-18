/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
    TextField textfield_bsn;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void button_registerUser() throws IOException {
        this.errorMsg = "Failed to register user:";
        this.label_errorMsg.setTextFill(Color.RED);
        try {

            if (textfield_bsn.getText().trim().isEmpty() || textfield_username.getText().trim().isEmpty() || textfield_password.getText().trim().isEmpty() || textfield_email.getText().trim().isEmpty() || textfield_alias.getText().trim().isEmpty()) {
                System.out.println("-All fields must be filled");
                this.errorMsg += "\n -All fields must be filled";
                this.label_errorMsg.setText(errorMsg);
                this.label_errorMsg.setVisible(true);
            } else {
                int bsn = Integer.parseInt(textfield_bsn.getText().trim());
                String username = textfield_username.getText().trim();
                String password = textfield_password.getText().trim();
                String alias = textfield_alias.getText().trim();
                String email = textfield_email.getText().trim();

                Connection conn = new Connection();
                boolean duplicateBSN = conn.hasDuplicateBSN(bsn);
                boolean duplicateUsername = conn.hasDuplicateUsername(username);
                boolean duplicateAlias = conn.hasDuplicateAlias(alias);
                boolean duplicateEmail = conn.hasDuplicateEmail(email);

                System.out.println("Starting registration...");

                if (duplicateBSN) {
                    this.errorMsg += "\n -BSN is already used";
                }
                if (duplicateUsername) {
                    this.errorMsg += "\n -Username is already used";
                }
                if (duplicateAlias) {
                    this.errorMsg += "\n -Alias is already used";
                }
                if (duplicateEmail) {
                    this.errorMsg += "\n -Email is already used";
                }
                this.label_errorMsg.setText(errorMsg);
                this.label_errorMsg.setVisible(true);

                if (!duplicateBSN && !duplicateUsername && !duplicateAlias && !duplicateEmail) {
                    conn.setUser_REGISTER(bsn, username, password, alias, email, null, 0);
                    this.label_errorMsg.setText("Succesfully registered new user!");
                    this.label_errorMsg.setTextFill(Color.GREEN);
                }
            }

        } catch (NumberFormatException ex) {
            System.out.println("-BSN field must constain a number");
            this.errorMsg += "\n -BSN field must constain a number";
            this.label_errorMsg.setText(errorMsg);
            this.label_errorMsg.setVisible(true);
        }
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
}
