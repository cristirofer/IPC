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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class LogInController implements Initializable {
    
    private BooleanProperty validFields;
    private final int EQUALS = 0;  
    private int fullScreen = 1;

    
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
    private ImageView banner;  
    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBarCenter;
    @FXML
    private VBox vBoxCenter;
    @FXML
    private MenuItem infoButton;
    
    private boolean lightMode = true;
    @FXML
    private HBox hbox1;
    @FXML
    private VBox vbox1;
    @FXML
    private Label accountLabel;
    
    
    private static String myString1;
    private static String myString2;
    
    public static String getMyNickname(){
        return myString1;
    }
    public static String getMyPassword(){
        return myString2;
    }
   
    
    private void manageNicknameError(Label errorLabel,TextField textField1,PasswordField textField2){
        showNicknameErrorMessage(errorLabel,textField1, textField2);
        textField1.requestFocus();
    }
    private void managePasswordError(Label errorLabel,PasswordField textField1){
        showPasswordErrorMessage(errorLabel,textField1);
        textField1.requestFocus();
    }
    private void manageCorrect(Label errorLabel,TextField textField1,PasswordField textField2) throws IOException{
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
        textField1.styleProperty().setValue("-fx-background-color: #ffffff");
        textField2.styleProperty().setValue("-fx-background-color: #ffffff");   
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFields = new SimpleBooleanProperty();
        validFields.setValue(Boolean.FALSE);
        logInButton.disableProperty().bind(Bindings.not(validFields)); 
        loginpassword.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validFields.setValue(true);
        }));
        loginpassword.focusedProperty().addListener((observable, oldValue, newValue)->{
        if(!newValue){ 
            //focus lost.
            checkEditedFields();
        }
        });
        Platform.runLater(() -> {
            // Realizar el binding después de que la escena esté disponible
            banner.fitHeightProperty().bind(banner.getScene().heightProperty());
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
            if(validFields.get()){
            if(checkEditEmail()){
                //load next screen
                myString1 = loginemail.textProperty().getValueSafe();
                myString2 = loginpassword.textProperty().getValueSafe();
                Club.getInstance().getMemberByCredentials(loginemail.textProperty().getValueSafe(), loginpassword.textProperty().getValueSafe());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main Window.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setMinHeight(579);
                stage.setMinWidth(976);
                Image icon = new Image("/resources/images/pelota.png");
                stage.getIcons().add(icon);
                stage.setTitle("Main Window");
                stage.setFullScreen(false);
                stage.setFullScreenExitHint("Press F11 to exit fullscreen");
                stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                linktoSignUp.getScene().getWindow().hide();
            }else{//do nothing, user must re-try
            }
        }
    }
    
    @FXML
    private void signUpClicked(ActionEvent event) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sign-Up.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Sign-Up");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
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
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setTitle("Availability");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
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
    private void enterPressedNick(KeyEvent event) throws ClubDAOException, IOException {
        if(validFields.get() && event.getCode() == KeyCode.ENTER){
            if(checkEditEmail()){
                //load next screen
                myString1 = loginemail.textProperty().getValueSafe();
                myString2 = loginpassword.textProperty().getValueSafe();
                Club.getInstance().getMemberByCredentials(loginemail.textProperty().getValueSafe(), loginpassword.textProperty().getValueSafe());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main Window.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setMinHeight(579);
                stage.setMinWidth(976);
                Image icon = new Image("/resources/images/pelota.png");
                stage.getIcons().add(icon);
                stage.setTitle("Main Window");
                stage.setFullScreen(false);
                stage.setFullScreenExitHint("Press F11 to exit fullscreen");
                stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                linktoSignUp.getScene().getWindow().hide();
            }else{//do nothing, user must re-try
            }
        }
    }

    @FXML
    private void enterPressedPass(KeyEvent event) throws ClubDAOException, IOException {
        if(validFields.get() && event.getCode() == KeyCode.ENTER){
            if(checkEditEmail()){
                //load next screen
                myString1 = loginemail.textProperty().getValueSafe();
                myString2 = loginpassword.textProperty().getValueSafe();
                Club.getInstance().getMemberByCredentials(loginemail.textProperty().getValueSafe(), loginpassword.textProperty().getValueSafe());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main Window.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setMinHeight(579);
                stage.setMinWidth(976);
                Image icon = new Image("/resources/images/pelota.png");
                stage.getIcons().add(icon);
                stage.setTitle("Main Window");
                stage.setFullScreen(false);
                stage.setFullScreenExitHint("Press F11 to exit fullscreen");
                stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                linktoSignUp.getScene().getWindow().hide();
            }else{//do nothing, user must re-try
            }
        }
    }
    
    

    @FXML
    private void makeFullScreen(KeyEvent event) {
        Stage stage = (Stage) linktoSignUp.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen) && event.getCode() == KeyCode.F11){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }

    @FXML
    private void infoPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
        alert.setTitle("About us...");
        alert.setHeaderText(null);
        // or null if we do not want a header
        alert.setContentText("Developed by Cesar Gimeno Castellote, Javier García Cerdán and Cristina Rodríguez Fernández");
        alert.showAndWait();
    }

    @FXML
    private void changeMode(ActionEvent event) {
        lightMode = !lightMode;
        if(lightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }
    
    public void setLightMode() {
        accountLabel.setStyle("-fx-text-fill: black;");
        vBox.getStylesheets().remove("/resources/css/darkMode.css");
        vBox.getStylesheets().add("/resources/css/lightMode.css");
    }
    
    public void setDarkMode() {
        accountLabel.setStyle("-fx-text-fill: white;");
        vBox.getStylesheets().remove("/resources/css/lightMode.css");
        vBox.getStylesheets().add("/resources/css/darkMode.css");
    }

}
