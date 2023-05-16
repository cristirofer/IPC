/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
    private Button logInButton;
    @FXML
    private Label labelWelcome;
    @FXML
    private Button availabilityButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void loadAvailability(ActionEvent event) {
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
    private void logInClicked(MouseEvent event) {
    }
    
}