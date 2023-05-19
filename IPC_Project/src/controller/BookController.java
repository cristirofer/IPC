/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;
import controller.LogInController;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;

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
    @FXML
    private Button cancelButton;
    @FXML
    private Button bookButton;
    @FXML
    private GridPane grid;
    @FXML
    private Label labelCol;
    @FXML
    private DatePicker day;
    @FXML
    private ComboBox<String> court;
    private int fullScreen = 1;
    @FXML
    private MenuItem infoButton;
    @FXML
    private Label slotSelected;

    private boolean isSelected;
    private final LocalTime firstSlotStart = LocalTime.of(9, 0);
    private final Duration slotLength = Duration.ofMinutes(60);
    private final LocalTime lastSlotStart = LocalTime.of(22, 0);
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
    private List<TimeSlot> timeSlots = new ArrayList<>(); //Para varias columnas List<List<TimeSolt>>
    private ObjectProperty<TimeSlot> timeSlotSelected;
    private LocalDate daySelected;
    private String selectedCourt = "Court 1";
    
    
    private LocalTime myTime;
    @FXML
    private ToggleButton fil1;
    @FXML
    private ToggleButton fil2;
    @FXML
    private ToggleButton fil3;
    @FXML
    private ToggleButton fil4;
    @FXML
    private ToggleButton fil5;
    @FXML
    private ToggleButton fil6;
    @FXML
    private ToggleButton fil7;
    @FXML
    private ToggleButton fil8;
    @FXML
    private ToggleButton fil9;
    @FXML
    private ToggleButton fil10;
    @FXML
    private ToggleButton fil11;
    @FXML
    private ToggleButton fil12;
    @FXML
    private ToggleButton fil13;
    @FXML
    private Label test1;
    @FXML
    private Label test2;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
        //BooleanBinding validFields = Binding(slotSelected);
        //bookButton.disableProperty().bind(Bindings.not(isSelected)); 
        Image im ;
        im = new Image("/resources/images/noprofile.jpg",false);
        profilePicContainer.setFill(new ImagePattern(im));
   
        timeSlotSelected = new SimpleObjectProperty<>();
        // Create a Locale object for English
        Locale locale = Locale.ENGLISH;

        // Set the default locale of your application to English
        Locale.setDefault(locale);
        //---------------------------------------------------------------------
        //cambia los SlotTime al cambiar de dia
        day.valueProperty().addListener((a, b, c) -> {
            setTimeSlotsGrid(c);
            labelCol.setText(c.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        });
        
        //---------------------------------------------------------------------
        //inicializa el DatePicker al dia actual
        day.setValue(LocalDate.now());        

        //---------------------------------------------------------------------
        // pinta los SlotTime en el grid
        setTimeSlotsGrid(day.getValue());
        
        //---------------------------------------------------------------------
        // enlazamos timeSlotSelected con el label para mostrar la seleccion
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E MMM d");
        timeSlotSelected.addListener((a, b, c) -> {
            if (c == null) {
                slotSelected.setText("");
            } else {
                myTime = c.getTime();
                slotSelected.setText(c.getDate().format(dayFormatter)
                        + "-"
                        + c.getStart().format(timeFormatter));
            }
        });
        
        ObservableList<String> list = FXCollections.observableArrayList("Court 1", "Court 2", "Court 3", "Court 4", "Court 5", "Court 6");
        court.setItems(list);
        //displayCourtAvailability(selectedCourt,day.getValue());*/
        String courtName = null;
        try {
            courtName = Club.getInstance().getCourts().get(1).getName();
        } catch (ClubDAOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelCol.setText(courtName);
    }

    private void setTimeSlotsGrid(LocalDate date) {
        //actualizamos la seleccion
        timeSlotSelected.setValue(null);

        //--------------------------------------------        
        //borramos los SlotTime del grid
        ObservableList<Node> children = grid.getChildren();
        for (TimeSlot timeSlot : timeSlots) {
            children.remove(timeSlot.getView());
        }
        timeSlots = new ArrayList<>();

        //----------------------------------------------------------------------------------
        // desde la hora de inicio y hasta la hora de fin creamos slotTime segun la duracion
        int slotIndex = 1;
        for (LocalDateTime startTime = date.atTime(firstSlotStart);
                !startTime.isAfter(date.atTime(lastSlotStart));
                startTime = startTime.plus(slotLength)) {

            //---------------------------------------------------------------------------------------
            // creamos el SlotTime, lo anyadimos a la lista de la columna y asignamos sus manejadores
            TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
            timeSlots.add(timeSlot);
            registerHandlers(timeSlot);
            //-----------------------------------------------------------
            // lo anyadimos al grid en la posicion x= 1, y= slotIndex
            grid.add(timeSlot.getView(), 1, slotIndex);
            slotIndex++;
        }
    }

    private void registerHandlers(TimeSlot timeSlot) {

        timeSlot.getView().setOnMousePressed((MouseEvent event) -> {
            //---------------------------------------------slot----------------------------
            //solamente puede estar seleccionado un slot dentro de la lista de slot
            timeSlots.forEach(slot -> {
                slot.setSelected(slot == timeSlot);
            });

            //----------------------------------------------------------------
            //actualizamos el label Dia-Hora, esto es ad hoc,  para mi diseño
            timeSlotSelected.setValue(timeSlot);
            //----------------------------------------------------------------
            // si es un doubleClik  vamos a mostrar una alerta y cambiar el estilo de la celda
            /*
            if (event.getClickCount() > 1) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("SlotTime");
                alerta.setHeaderText("Confirma la selecció");
                alerta.setContentText("Has seleccionat: "
                        + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                        + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                Optional<ButtonType> result = alerta.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ObservableList<String> styles = timeSlot.getView().getStyleClass();
                    if (styles.contains("time-slot")) {
                        styles.remove("time-slot");
                        styles.add("time-slot-libre");
                    } else {
                        styles.remove("time-slot-libre");
                        styles.add("time-slot");
                    }
                }
            }
            */
        });   
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
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen) && event.getCode() == KeyCode.F11){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }
    

    @FXML
    private void cancelPressed(ActionEvent event) throws IOException {
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
    private void bookPressed(ActionEvent event) throws ClubDAOException, IOException {
        if(isSelected){
            /*LocalDate date = LocalDate.now();
            comboBox.getItems().addAll(todos los courts);
            LocalDateTime datetime = LocalDateTime.of(date, myTime);
            Member myMember = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(), LogInController.getMyPassword());
            boolean isPaid = Club.getInstance().hasCreditCard(LogInController.getMyNickname());
            Club c = Club.getInstance();
            //Court selected = c.getCourt(TableList1.getSelectionModel().getSelectedItem().getCourt());
            //Booking b = c.registerBooking(datetime, date, myTime, isPaid, selected , myMember);
            */
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
            alert.setTitle("Done");
            alert.setHeaderText("Your booking has been sucessfully created!");
            // or null if we do not want a header
            alert.setContentText("You can see the details in My Bookings");
            alert.showAndWait();
            /*} else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
            alert.setTitle("Error");
            alert.setHeaderText(null);
            // or null if we do not want a header
            alert.setContentText("You already have a booking at that time");
            alert.showAndWait();
            }
            */
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
            alert.setTitle("Error");
            alert.setHeaderText(null);
            // or null if we do not want a header
            alert.setContentText("Please select something...");
            alert.showAndWait();
        }
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


    private void fil1Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil2Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil3Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil4Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil5Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil6Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil7Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil8Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil9Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil10Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil11Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil12Clicked(MouseEvent event) {
        isSelected = true;
    }

    private void fil13Clicked(MouseEvent event) {
        isSelected = true;
    }
    
    public Court getMyCourt() throws ClubDAOException, IOException{
        switch(selectedCourt){
            case "Court 1" :
                return returnCourt("Pista 1");
            case "Court 2" :
                return returnCourt("Pista 2");
            case "Court 3" :
                return returnCourt("Pista 3");
            case "Court 4" :
                return returnCourt("Pista 4");
            case "Court 5" :
                return returnCourt("Pista 5");
            case "Court 6" :
                return returnCourt("Pista 6");
        }
        return null;
    }
    
    private Court returnCourt(String nombrePista) throws ClubDAOException, IOException{
        ArrayList<Court> courtList = (ArrayList<Court>) Club.getInstance().getCourts();
        for(int i = 0; i < courtList.size(); i++){
            if(courtList.get(i).getName().equals(nombrePista)){
                return courtList.get(i);
            } 
        }
        return null;
    }
    
    @FXML
    private void selectCourt(ActionEvent event) throws ClubDAOException, IOException {
        selectedCourt = court.getSelectionModel().getSelectedItem().toString();
    }
    
    private void displayCourtAvailability(String court, LocalDate dateSelected) throws ClubDAOException, IOException{
        //to do
        ArrayList<Booking> b = (ArrayList<Booking>) Club.getInstance().getCourtBookings(selectedCourt, daySelected);
        for (Booking bi : b){
            
        }
    }
    
    
    public class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        protected final Pane view;
        
        
        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            view.getStyleClass().add("time-slot");
            // ---------------------------------------------------------------
            // de esta manera cambiamos la apariencia del TimeSlot cuando los seleccionamos
            selectedProperty().addListener((obs, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalTime getTime() {
            return start.toLocalTime();
        }

        public LocalDate getDate() {
            return start.toLocalDate();
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public Node getView() {
            return view;
        }
    } 
}
