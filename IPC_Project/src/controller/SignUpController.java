/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.EQUALS;
import javafx.scene.input.MouseEvent;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class SignUpController implements Initializable {

    private BooleanProperty validFields;
    private final int EQUALS = 0; 
    
    @FXML
    private TextField nameS;
    @FXML
    private TextField fNameS;
    @FXML
    private TextField numberS;
    @FXML
    private TextField passwS;
    @FXML
    private TextField repPasswS;
    @FXML
    private TextField cardS;
    @FXML
    private TextField profileS;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nicknameS;
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private Label lIncorrectPassword;
    @FXML
    private Label lPassDifferent;

    
    //properties to control valid fieds values. 
    private final int EQUALS = 0;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void isAccepted(MouseEvent event) {
        nicknameS.textProperty().setValue("");
        passwS.textProperty().setValue("");
        repPasswS.textProperty().setValue("");
        
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
    }

    @FXML
    private void isCanceled(MouseEvent event) {
    }
    /*
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFields = new SimpleBooleanProperty();
        validFields.setValue(Boolean.FALSE);
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
        manageError(loginErrorMessage, loginemail,loginpassword,validFields );
    else
        manageCorrect(loginErrorMessage, loginemail,loginpassword,validFields );
    }
    */
    @FXML
    private void loadSignUpTab(MouseEvent event) {
    }

    @FXML
    private void logInClicked(MouseEvent event) {
    }
    
    
    private void checkEquals(){
        if(passwS.textProperty().getValueSafe().compareTo(repPasswS.textProperty().getValueSafe()) != EQUALS){
            showErrorMessage(lPassDifferent, repPasswS);
            equalPasswords.setValue(Boolean.FALSE);
            passwS.textProperty().setValue("");
            repPasswS.textProperty().setValue("");
            passwS.requestFocus();
        }else
            manageCorrect(lPassDifferent, repPasswS, equalPasswords);
    }
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
 
    }
    
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
}
