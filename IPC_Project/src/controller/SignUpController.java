/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import static javafx.scene.input.KeyCode.EQUALS;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class SignUpController implements Initializable {
    
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
    @FXML
    private TextField profileS;
    @FXML
    private Button selectprofileS;
    @FXML
    private Circle profilePicContainer;
    @FXML
    private MenuItem exitButton;
    @FXML
    private RadioMenuItem amazonOption;
    @FXML
    private ToggleGroup buyGroup;
    @FXML
    private RadioMenuItem ebayOption;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords);
        acceptButton.disableProperty().bind(Bindings.not(validFields));
        
        nicknameS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkEditEmail();
            }
        });
        
        
        passwS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkEditPassword();
            }
        });
        
        repPasswS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkEquals();
            }
        });
        
        Image im ;
        im = new Image("/resources/images/noprofile.jpg",false);
        profilePicContainer.setFill(new ImagePattern(im));
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
    private void checkEditEmail(){
        if(!Utils.checkEmail(nicknameS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(lIncorrectEmail, nicknameS,validEmail );
        else
            manageCorrect(lIncorrectEmail, nicknameS,validEmail );
    }
    
    
    private void checkEditPassword(){
        if(!Utils.checkPassword(passwS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(lIncorrectPassword, passwS,validPassword );
        else
            manageCorrect(lIncorrectPassword, passwS,validPassword );
    }
/*
    @FXML
    private void isCanceled(MouseEvent event) {
    }
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

    @FXML
    private void canceledClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Log-in (main Screen).fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(540);
        stage.setMinWidth(960);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setTitle("Log-in");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        cancelButton.getScene().getWindow().hide();
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
}


/*
    @FXML
    private void isCanceled(MouseEvent event) {
    }
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
