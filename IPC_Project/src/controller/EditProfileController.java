/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Javi
 */
public class EditProfileController implements Initializable {
    /*
    FXML objects
    */
    @FXML
    private MenuItem exitButton;
    @FXML
    private ImageView banner;
    @FXML
    private Circle profilePicContainer;
    @FXML
    private TextField nameS;
    @FXML
    private TextField fNameS;
    @FXML
    private TextField numberS;
    @FXML
    private PasswordField passwS;
    @FXML
    private PasswordField repPasswS;
    @FXML
    private TextField cardS;
    @FXML
    private TextField cscS;
    @FXML
    private Label lIncorrectPassword;
    @FXML
    private Label lPassDifferent;
    @FXML
    private Label IincorrectPhoneNumber;
    @FXML
    private Button selectprofileS;
    @FXML
    private Label paymentError;
    @FXML
    private Label incorrectName;
    @FXML
    private Label incorrectSurname;
    @FXML
    private Label incorrectImageRoute;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nicknameS;
    
    //properties to control valid fieds values. 
    private final int EQUALS = 0;
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;
    private BooleanProperty validPhone;
    private BooleanProperty validName;
    private BooleanProperty validSurname;
    private BooleanProperty validPaymentInfo;
    private BooleanProperty validImagePath;
    private Image globalAvatar;
    private int fullScreen = 1;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            globalAvatar = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword()).getImage();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profilePicContainer.setFill(new ImagePattern(globalAvatar));
        Platform.runLater(() -> {
            // Realizar el binding después de que la escena esté disponible
            banner.fitHeightProperty().bind(banner.getScene().heightProperty());
        });
        Locale locale = Locale.ENGLISH;
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        validPhone = new SimpleBooleanProperty();
        validName = new SimpleBooleanProperty();
        validSurname = new SimpleBooleanProperty();
        validPaymentInfo = new SimpleBooleanProperty();
        validImagePath = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        validPhone.setValue(Boolean.FALSE);
        validName.setValue(Boolean.FALSE);
        validSurname.setValue(Boolean.FALSE);
        validPaymentInfo.setValue(Boolean.FALSE);
        validImagePath.setValue(Boolean.FALSE);
        
        BooleanBinding validFields = Bindings.and( validPhone, validName).and(validSurname);
        acceptButton.disableProperty().bind(Bindings.not(validFields));
        
        nameS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validName.setValue(true);
        }));
        
        fNameS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validSurname.setValue(true);
        }));
        
        numberS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validPhone.setValue(true);
        }));
        passwS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validPassword.setValue(true);
        }));
        cscS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validPaymentInfo.setValue(true);
        }));
        
        cardS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            validPaymentInfo.setValue(true);
        }));
        repPasswS.textProperty().addListener( ((observable, oldVal, newVal) -> {
            equalPasswords.setValue(true);
        }));
        
        nameS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkName();
            }
        });
        fNameS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkSurname();
            }
        });
        
        passwS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkEditPassword();
            }
        });
        
        numberS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkPhone();
            }
        });
        
        cscS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkPaymentInfoCSC();
            }
        });
        
        cardS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkPaymentInfo();
            }
        });
        
        repPasswS.focusedProperty().addListener((observable, oldValue, newValue)->{
            if(!newValue){ //focus lost.
                checkEquals();
            }
        });
        
        Member myMember = null;
        try {
            myMember = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword());
        } catch (ClubDAOException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        nameS.setText(myMember.getName());
        fNameS.setText(myMember.getSurname());
        numberS.setText(myMember.getTelephone());
        if (myMember.checkHasCreditInfo()){
            cardS.setText(myMember.getCreditCard());
            cscS.setText(Integer.toString(myMember.getSvc()));
        }
        nicknameS.setText(LogInController.getMyNickname());
        nicknameS.setDisable(true);
        
    }

    @FXML
    private void signUpClicked(ActionEvent event) throws ClubDAOException, IOException {
        /*TextInputDialog dialog = new TextInputDialog(); // Default value
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Verification step");
        dialog.setContentText("Enter your current password:");
        Optional<String> result = dialog.showAndWait();
        */
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Verification step");
        dialog.setContentText("Enter your current password:");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        PasswordField pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Enter your current password:"), pwd);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return pwd.getText();
            }
            return null;
        });


        Optional<String> result = dialog.showAndWait();
        // Obtain the result (before Java 8)
        if (result.isPresent()){
            if (result.get().equals(LogInController.getMyPassword())){
                
                Member myMember = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(), LogInController.getMyPassword());
                myMember.setName(nameS.textProperty().getValueSafe());
                myMember.setSurname(fNameS.textProperty().getValueSafe());
                myMember.setTelephone(numberS.textProperty().getValueSafe());
                if (!passwS.getText().equals("")){
                    myMember.setPassword(passwS.textProperty().getValueSafe());
                }
                if (!cardS.getText().equals("") && !cscS.getText().equals("")){
                    myMember.setCreditCard(cardS.textProperty().getValueSafe());
                    myMember.setSvc(Integer.parseInt(cscS.textProperty().getValueSafe()));
                }
                myMember.setImage(globalAvatar);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Profile updated");
                alert.setHeaderText("Your information has been updated");
                alert.setContentText("Good bye " + Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword()).getName() + "!");
                alert.showAndWait();
                
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
                if(Utils.isEven(fullScreen)) {
                    stage.setFullScreen(true);
                } else {
                    stage.setFullScreen(false);
                }
                stage.setFullScreenExitHint("Press F11 to exit fullscreen");
                stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                nicknameS.getScene().getWindow().hide();
            } else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password is not correct");
                alert.showAndWait();
            }
        }
    }

     private void checkPaymentInfo(){
        if(!Utils.checkCreditCard(cardS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(paymentError, cardS,validPaymentInfo );
        else
            manageCorrect(paymentError, cardS,validPaymentInfo );
    }
    private void checkName(){
        if(!Utils.checkName(nameS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(incorrectName, nameS,validName );
        else
            manageCorrect(incorrectName, nameS,validName );
    }
    private void checkSurname(){
        if(!Utils.checkSurname(fNameS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(incorrectSurname, fNameS,validSurname );
        else
            manageCorrect(incorrectSurname, fNameS,validSurname );
    }
    
    private void checkPaymentInfoCSC(){
        if(!Utils.checkCSC(cscS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(paymentError, cscS,validPaymentInfo );
        else
            manageCorrect(paymentError, cscS,validPaymentInfo );
    }
    
    
    private void checkPhone(){
        if(!Utils.checkPhone(numberS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(IincorrectPhoneNumber, numberS,validPhone );
        else
            manageCorrect(IincorrectPhoneNumber, numberS,validPhone );
    }
    
    
    private void checkEditPassword(){
        if(!Utils.checkPassword(passwS.textProperty().getValueSafe()))
        //Incorrect email
            manageError(lIncorrectPassword, passwS,validPassword );
        else
            manageCorrect(lIncorrectPassword, passwS,validPassword );
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
    private void manageError2(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage2(errorLabel,textField); 
    }
    private void manageCorrect2(Label errorLabel1, Label errorLabel2, TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage2(errorLabel1,errorLabel2,textField);
        
    }
    
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");
        textField.setText("");
    }
    private void showErrorMessage2(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }
    
    private void hideErrorMessage2(Label errorLabel1,Label errorLabel2,TextField textField)
    {
        errorLabel1.visibleProperty().set(false);
        textField.styleProperty().setValue("-fx-background-color: #ffffff");
        errorLabel2.visibleProperty().set(false);
    }

    
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
    
    @FXML
    private void canceledClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main Window.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Log-in");
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        stage.setFullScreen(false);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void makeFullScreen(KeyEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen) && event.getCode() == KeyCode.F11){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
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
    private void searchImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterJPEG,extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            Image avatar = new Image(file.toURI().toString());
            profilePicContainer.setFill(new ImagePattern(avatar));
            globalAvatar = avatar;
        }
    }

    @FXML
    private void isAccepted(MouseEvent event) {
        passwS.textProperty().setValue("");
        repPasswS.textProperty().setValue("");
        numberS.textProperty().setValue("");
        
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        validPhone.setValue(Boolean.FALSE);
    }

    @FXML
    private void infoPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
        alert.setTitle("About us...");
        alert.setHeaderText(null);
        // or null if we do not want a header
        alert.setContentText("Developed by Cesar Gimeno Castellote, Javier García Cerdán and Cristina Rodríguez Fernández");
        alert.showAndWait();
    }

    @FXML
    private void setFullscreen(ActionEvent event) {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen)){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }
    
}
