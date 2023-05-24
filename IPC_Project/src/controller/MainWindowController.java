/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import static controller.LogInController.getMyNickname;
import extra.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class MainWindowController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBarCenter;
    @FXML
    private MenuItem exitButton;
    @FXML
    private VBox vBoxCenter;
    
    private int fullScreen = 1;
    @FXML
    private Circle profilePicContainer;
    @FXML
    private Label labelWelcome;
    private Button availabilityButton;
    @FXML
    private Button configurationButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button myBookingsButton;
    @FXML
    private MenuItem infoButton;
    private String login;
    
    public void initBooking(String login) {
        this.login = login;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image im = new Image("/resources/images/noprofile.jpg",false);
        try {
            im = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword()).getImage();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profilePicContainer.setFill(new ImagePattern(im));
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
    private void makeFullScreen(KeyEvent event) {
        Stage stage = (Stage) availabilityButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen) && event.getCode() == KeyCode.F11){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }


    @FXML
    private void loadBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Book Court.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setTitle("Book a Court");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        bookButton.getScene().getWindow().hide();
    }

    @FXML
    private void loadBookings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Bookings.fxml"));
        Parent root = loader.load();
        
        //BookingsController bookingsController = loader.<BookingsController>getController();
        //bookingsController.initBooking(login);
        //System.out.println("Nickname: " + login);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setTitle("Bookings");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        bookButton.getScene().getWindow().hide();
    }

    @FXML
    private void loadConfig(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Edit Profile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinHeight(579);
	stage.setMinWidth(976);
        Image icon = new Image("/resources/images/pelota.png");
        stage.getIcons().add(icon);
        stage.setTitle("Edit configuration");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        bookButton.getScene().getWindow().hide();
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
        bookButton.getScene().getWindow().hide();
    }   
}
