/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.util.Locale;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;


public class JavaFXMLApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("/view/Log-in (main Screen).fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
	Locale.setDefault(Locale.US);
	stage.setMinHeight(579);
	stage.setMinWidth(976);
	Image icon = new Image("/resources/images/pelota.png");        
	stage.setScene(scene);
        stage.setTitle("Login");
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Press F11 to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClubDAOException, IOException {
        launch(args);
        
    }


    
}
