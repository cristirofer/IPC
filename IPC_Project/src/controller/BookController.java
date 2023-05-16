/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Crist
 */
public class BookController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBarCenter;
    @FXML
    private MenuItem exitButton;
    @FXML
    private VBox vBoxCenter;
    @FXML
    private Circle profilePicContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void isExited(ActionEvent event) {
    }

    @FXML
    private void makeFullScreen(KeyEvent event) {
    }
    
}
