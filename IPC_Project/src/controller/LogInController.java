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
    
    private BooleanProperty validFields;
    private final int EQUALS = 0;  

    
    @FXML
    private TextField loginemail;
    @FXML
    private PasswordField loginpassword;
    @FXML
    private Label loginErrorMessage;
    @FXML
    private Button logInButton;
    
    private void manageError(Label errorLabel,TextField textField1,PasswordField textField2, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField1,textField2);
        textField1.requestFocus();
    }
    private void manageCorrect(Label errorLabel,TextField textField1,PasswordField textField2, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField1, textField2);
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField, PasswordField textField2){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    private void hideErrorMessage(Label errorLabel,TextField textField1,PasswordField textField2){
        errorLabel.visibleProperty().set(false);
        textField1.styleProperty().setValue("");
        textField2.styleProperty().setValue("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFields = new SimpleBooleanProperty();
        validFields.setValue(Boolean.FALSE);
        logInButton.disableProperty().bind(Bindings.not(validFields)); 
        loginemail.focusedProperty().addListener((observable, oldValue, newValue)->{
        if(!newValue){ 
            //focus lost.
            checkEditedFields();
        }
        });
    }
    private void checkEditedFields() {
    if((loginpassword.textProperty().getValueSafe().compareTo("") != EQUALS) && (loginemail.textProperty().getValueSafe().compareTo("") != EQUALS)){
        validFields.setValue(Boolean.TRUE);
    }else
        validFields.setValue(Boolean.FALSE);
    }    
    private void checkEditEmail() throws ClubDAOException, IOException{
    if(!Utils.checkLogInUser(loginemail.textProperty().getValueSafe(),loginpassword.textProperty().getValueSafe()))
        //Incorrect email
        manageError(loginErrorMessage, loginemail,loginpassword,validFields );
    else
        manageCorrect(loginErrorMessage, loginemail,loginpassword,validFields );
    }
    
    @FXML
    private void loadSignUpTab(MouseEvent event) {
    }

    @FXML
    private void logInClicked(MouseEvent event) throws ClubDAOException, IOException {
        checkEditEmail();
    }
    
}
