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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import static javafx.scene.AccessibleRole.NODE;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
    @FXML
    private MenuItem exitButton;
    @FXML
    private RadioMenuItem amazonOption;
    @FXML
    private ToggleGroup buyGroup;
    @FXML
    private RadioMenuItem ebayOption;
    
    private void manageNicknameError(Label errorLabel,TextField textField1,PasswordField textField2){
        showNicknameErrorMessage(errorLabel,textField1, textField2);
        textField1.requestFocus();
    }
    private void managePasswordError(Label errorLabel,PasswordField textField1){
        showPasswordErrorMessage(errorLabel,textField1);
        textField1.requestFocus();
    }
    private void manageCorrect(Label errorLabel,TextField textField1,PasswordField textField2){
        hideErrorMessage(errorLabel,textField1,textField2);
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
    private void hideErrorMessage(Label errorLabel,TextField textField1,PasswordField textField2){
        errorLabel.setText("");
        textField1.styleProperty().setValue("-fx-background-color: #2d752f");
        textField2.styleProperty().setValue("-fx-background-color: #2d752f");   
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

    @FXML
    private void loadAvailability(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Availability.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Availability");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        linktoSignUp.getScene().getWindow().hide();
    }

    @FXML
    private void isExited(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to leave the program");
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("OK");
            Platform.exit();
        } else {
            System.out.println("CANCEL");
        }
    }

    @FXML
    private void amazonChosen(ActionEvent event) {
    }

    @FXML
    private void ebayChosen(ActionEvent event) {
    }

    @FXML
    private void enterPressedNick(KeyEvent event) throws ClubDAOException, IOException {
        if(validFields.get() && event.getCode() == KeyCode.ENTER){
            if(checkEditEmail()){
                //load next screen
            }else{//do nothing, user must re-try
            }
        }
    }

    @FXML
    private void enterPressedPass(KeyEvent event) throws ClubDAOException, IOException {
        if(validFields.get() && event.getCode() == KeyCode.ENTER){
            if(checkEditEmail()){
                //load next screen
            }else{//do nothing, user must re-try
            }
        }
    }
    
}
