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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    @FXML
    private Hyperlink linktoSignUp;
    
    private void manageNicknameError(Label errorLabel,TextField textField1,PasswordField textField2){
        showNicknameErrorMessage(errorLabel,textField1, textField2);
        textField1.requestFocus();
    }
    private void managePasswordError(Label errorLabel,PasswordField textField1){
        showPasswordErrorMessage(errorLabel,textField1);
        textField1.requestFocus();
    }
    private void manageCorrect(Label errorLabel,TextField textField1,PasswordField textField2){
        hideErrorMessage(errorLabel);
    }
    
    private void showNicknameErrorMessage(Label errorLabel,TextField textField1, PasswordField textField2){
        errorLabel.setText("Error, incorrect or not" + "\n"+ "registered Nickname.");
        textField1.setText("");
        textField2.setText("");
        textField1.styleProperty().setValue("-fx-background-color: #FCE5E0");
        textField2.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }
    
    private void showPasswordErrorMessage(Label errorLabel,PasswordField textField1){
        errorLabel.setText("Error, incorrect password.");
        textField1.setText("");
        textField1.styleProperty().setValue("-fx-background-color: #FCE5E0");

    }
    private void hideErrorMessage(Label errorLabel){
        errorLabel.setText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFields = new SimpleBooleanProperty();
        validFields.setValue(Boolean.FALSE);
        logInButton.disableProperty().bind(Bindings.not(validFields)); 
        loginpassword.focusedProperty().addListener((observable, oldValue, newValue)->{
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
    private boolean checkEditEmail() throws ClubDAOException, IOException{
        if(Utils.checkLogInUser(loginemail.textProperty().getValueSafe(),loginpassword.textProperty().getValueSafe()) == 0){
            //nickname error
            manageNicknameError(loginErrorMessage, loginemail, loginpassword);
            return false;
        } else if(Utils.checkLogInUser(loginemail.textProperty().getValueSafe(),loginpassword.textProperty().getValueSafe()) == 1){
            //password error
            managePasswordError(loginErrorMessage, loginpassword );
            return false;
        }else{
            manageCorrect(loginErrorMessage, loginemail,loginpassword);
            return true;
        }
    }
    /*
    private void loadSignUpTab(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sign-Up.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sign-Up");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        loginemail.getScene().getWindow().hide();
        
    }
*/
    
    @FXML
    private void logInClicked(MouseEvent event) throws ClubDAOException, IOException {
        if(checkEditEmail()){
            //load next screen
        }else{//do nothing, user must re-try
            
        }
    }
    
    @FXML
    private void signUpClicked(ActionEvent event) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sign-Up.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sign-Up");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        linktoSignUp.getScene().getWindow().hide();
    }
    
}
