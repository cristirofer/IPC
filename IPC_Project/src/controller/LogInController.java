/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class LogInController implements Initializable {
    
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords; 
    private final int EQUALS = 0;  

    
    @FXML
    private TextField loginemail;
    @FXML
    private PasswordField loginpassword;
    @FXML
    private Label loginErrorMessage;
    @FXML
    private Button logInButton;
    
    private void manageError(Label errorLabel,TextField textField1,TextField textField2, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField1,textField2);
        textField1.requestFocus();
    }
    private void manageCorrect(Label errorLabel,TextField textField1,TextField textField2, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField1, textField2);
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField, TextField textField2){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    private void hideErrorMessage(Label errorLabel,TextField textField1,TextField textField2){
        errorLabel.visibleProperty().set(false);
        textField1.styleProperty().setValue("");
        textField2.styleProperty().setValue("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        BooleanBinding validFields = Bindings.and(validEmail, validPassword);
        logInButton.disableProperty().bind(Bindings.not(validFields)); 
         loginemail.focusedProperty().addListener((observable, oldValue, newValue)->{
        if(!newValue){ 
            try {
            //focus lost.
            checkEditEmail();
            } catch (ClubDAOException | IOException ex) {
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
    }    
    private void checkEditEmail() throws ClubDAOException, IOException{
    if(!Utils.checkLogInUser(loginemail.textProperty().getValueSafe(),loginpassword.textProperty().getValueSafe()))
        //Incorrect email
        manageError(loginErrorMessage, loginemail,loginpassword,validEmail );
    else
        manageCorrect(loginErrorMessage, loginemail,loginpassword,validEmail );
    }
    
    @FXML
    private void loadSignUpTab(MouseEvent event) {
    }

    @FXML
    private void logInClicked(MouseEvent event) {
    }
    
}
