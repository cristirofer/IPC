/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import controller.BookController;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Collections;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.DateCell;
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
    private DatePicker day;
    @FXML
    private ComboBox<String> court;
    private int fullScreen = 1;
    
    private ToggleButton[] list = new ToggleButton[13];
    private int index;
    
    @FXML
    private MenuItem infoButton;
    @FXML
    private Label slotSelected;
    
    private BooleanProperty isBooked1 = new SimpleBooleanProperty();
    private BooleanProperty isBooked2 = new SimpleBooleanProperty();
    private BooleanProperty isBooked3 = new SimpleBooleanProperty();
    private BooleanProperty isBooked4 = new SimpleBooleanProperty();
    private BooleanProperty isBooked5 = new SimpleBooleanProperty();
    private BooleanProperty isBooked6 = new SimpleBooleanProperty();
    private BooleanProperty isBooked7 = new SimpleBooleanProperty();
    private BooleanProperty isBooked8 = new SimpleBooleanProperty();
    private BooleanProperty isBooked9 = new SimpleBooleanProperty();
    private BooleanProperty isBooked10 = new SimpleBooleanProperty();
    private BooleanProperty isBooked11 = new SimpleBooleanProperty();
    private BooleanProperty isBooked12 = new SimpleBooleanProperty();
    private BooleanProperty isBooked13 = new SimpleBooleanProperty();
    
    

    private boolean isSelected = false;
    private final LocalTime firstSlotStart = LocalTime.of(9, 0);
    private final Duration slotLength = Duration.ofMinutes(60);
    private final LocalTime lastSlotStart = LocalTime.of(22, 0);
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
    private List<TimeSlot> timeSlots = new ArrayList<>(); //Para varias columnas List<List<TimeSolt>>
    private ObjectProperty<TimeSlot> timeSlotSelected;
    
    private String selectedCourt = "Court 1";
    private LocalDate daySelected;
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
    private Button bookButton;
    @FXML
    private GridPane grid;
    @FXML
    private Label labelCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        list[0] = fil1;
        list[1] = fil2;
        list[2] = fil3;
        list[3] = fil4;
        list[4] = fil5;
        list[5] = fil6;
        list[6] = fil7;
        list[7] = fil8;
        list[8] = fil9;
        list[9] = fil10;
        list[10] = fil11;
        list[11] = fil12;
        list[12] = fil3;
        
       
        //BooleanBinding validFields = Binding(slotSelected);
        //bookButton.disableProperty().bind(Bindings.not(isSelected)); 
        Image im = new Image("/resources/images/noprofile.jpg",false);
        try {
            im = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword()).getImage();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profilePicContainer.setFill(new ImagePattern(im));
   
        timeSlotSelected = new SimpleObjectProperty<>();
        // Create a Locale object for English
        Locale locale = Locale.ENGLISH;

        // Set the default locale of your application to English
        Locale.setDefault(locale);
        //---------------------------------------------------------------------
        //cambia los SlotTime al cambiar de dia
        day.valueProperty().addListener((a, b, c) -> {
            try {
                displayCourtAvailability();
            } catch (ClubDAOException | IOException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //labelCol.setText(c.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        });
        
        //---------------------------------------------------------------------
        //inicializa el DatePicker al dia actual
        day.setValue(LocalDate.now());
        day.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();
            setDisable(empty || date.compareTo(today) < 0 );
             }
             };
        });

        //---------------------------------------------------------------------
        // pinta los SlotTime en el grid
        // setTimeSlotsGrid(day.getValue());
        
        //---------------------------------------------------------------------
        // enlazamos timeSlotSelected con el label para mostrar la seleccion
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E MMM d");
        timeSlotSelected.addListener((a, b, c) -> {
            if (c == null) {
                slotSelected.setText("");
            } else {
                myTime = c.getTime();
                daySelected = c.getDate();
                slotSelected.setText(daySelected.format(dayFormatter)
                        + "-"
                        + c.getStart().format(timeFormatter));
            }
        });
        
        //ObservableList<String> list = FXCollections.observableArrayList("Court 1", "Court 2", "Court 3", "Court 4", "Court 5", "Court 6");
        //court.setItems(list);
        
        
       //Platform.runLater(() -> {
            // Realizar el binding después de que la escena esté disponible
            isBooked1.setValue(Boolean.FALSE);
            isBooked2.setValue(Boolean.FALSE);
            isBooked3.setValue(Boolean.FALSE);
            isBooked4.setValue(Boolean.FALSE);
            isBooked5.setValue(Boolean.FALSE);
            isBooked6.setValue(Boolean.FALSE);
            isBooked7.setValue(Boolean.FALSE);
            isBooked8.setValue(Boolean.FALSE);
            isBooked9.setValue(Boolean.FALSE);
            isBooked10.setValue(Boolean.FALSE);
            isBooked11.setValue(Boolean.FALSE);
            isBooked12.setValue(Boolean.FALSE);
            isBooked13.setValue(Boolean.FALSE);


            fil1.disableProperty().bind(isBooked1);
            fil2.disableProperty().bind(isBooked2);
            fil3.disableProperty().bind(isBooked3);
            fil4.disableProperty().bind(isBooked4);
            fil5.disableProperty().bind(isBooked5);
            fil6.disableProperty().bind(isBooked6);
            fil7.disableProperty().bind(isBooked7);
            fil8.disableProperty().bind(isBooked8);
            fil9.disableProperty().bind(isBooked9);
            fil10.disableProperty().bind(isBooked10);
            fil11.disableProperty().bind(isBooked11);
            fil12.disableProperty().bind(isBooked12);
            fil13.disableProperty().bind(isBooked13);
            
        //});
            fil1.getStyleClass().clear();fil1.getStyleClass().add("toggle-button");
            fil2.getStyleClass().clear();fil2.getStyleClass().add("toggle-button");
            fil3.getStyleClass().clear();fil3.getStyleClass().add("toggle-button");
            fil4.getStyleClass().clear();fil4.getStyleClass().add("toggle-button");
            fil5.getStyleClass().clear();fil5.getStyleClass().add("toggle-button");
            fil6.getStyleClass().clear();fil6.getStyleClass().add("toggle-button");
            fil7.getStyleClass().clear();fil7.getStyleClass().add("toggle-button");
            fil8.getStyleClass().clear();fil8.getStyleClass().add("toggle-button");
            fil9.getStyleClass().clear();fil9.getStyleClass().add("toggle-button");
            fil10.getStyleClass().clear();fil10.getStyleClass().add("toggle-button");
            fil11.getStyleClass().clear();fil11.getStyleClass().add("toggle-button");
            fil12.getStyleClass().clear();fil12.getStyleClass().add("toggle-button");
            fil13.getStyleClass().clear();fil13.getStyleClass().add("toggle-button");
            try {
            displayCourtAvailability();
            } catch (ClubDAOException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

/*    private void setTimeSlotsGrid(LocalDate date) {
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
            //grid.add(timeSlot.getView(), 1, slotIndex);
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
            
        });   
    }
    */
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
                if (index == 0){
                        if (list[index + 1].getText().equals("Booked") && list[index + 2].getText().equals("Booked")){
                            showErrorMessage();
                        } else{
                            showCorrectMessage();
                        }
                } else if (index == 12){
                        if (list[index - 1].getText().equals("Booked") && list[index - 2].getText().equals("Booked")){
                            showErrorMessage();
                        } else{
                            showCorrectMessage();
                        }
                }else if (index == 1){
                    if ((list[index + 1].getText().equals("Booked"))&&(list[index + 2].getText().equals("Booked") || list[index - 1].getText().equals("Booked"))){
                            showErrorMessage();
                    } else{
                            showCorrectMessage();
                    }
                } else if (index == 11){
                     if ((list[index - 1].getText().equals("Booked"))&&(list[index - 2].getText().equals("Booked") || list[index + 1].getText().equals("Booked"))){
                            showErrorMessage();
                    } else{
                            showCorrectMessage();
                    } 
                }else {
                        if((list[index + 1].getText().equals("Booked"))&&(list[index + 2].getText().equals("Booked") || list[index - 1].getText().equals("Booked"))){
                            showErrorMessage(); 
                        } else if ((list[index - 1].getText().equals("Booked"))&&(list[index - 2].getText().equals("Booked") || list[index + 1].getText().equals("Booked"))){
                            showErrorMessage();
                        } else{
                            showCorrectMessage();
                        }

                }
            displayCourtAvailability();
            
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
    public void showCorrectMessage() throws ClubDAOException, IOException{
        Member myMember = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(), LogInController.getMyPassword());
        if(myMember.checkHasCreditInfo()){
            LocalDateTime datetime = LocalDateTime.now();
            boolean isPaid = Club.getInstance().hasCreditCard(LogInController.getMyNickname());
            Court selected = getMyCourt();
            Club.getInstance().registerBooking(datetime, day.getValue(),myTime,isPaid,selected ,myMember);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
            alert.setTitle("Booking sucessfully created!");
            alert.setHeaderText("Transaction already paid");
            // or null if we do not want a header
            alert.setContentText("You can see the details in My Bookings");
            alert.showAndWait();
        } else{
            LocalDateTime datetime = LocalDateTime.now();
            boolean isPaid = Club.getInstance().hasCreditCard(LogInController.getMyNickname());
            Court selected = getMyCourt();
            Club.getInstance().registerBooking(datetime, day.getValue(),myTime,isPaid,selected ,myMember);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
            alert.setTitle("Booking sucessfully created!");
            alert.setHeaderText("Transaction is not paid");
            // or null if we do not want a header
            alert.setContentText("You can see the details in My Bookings");
            alert.showAndWait();
        }
    }
    public void showErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        // or null if we do not want a header
        alert.setContentText("You cannot book more than two consecutive hours");
        alert.showAndWait();
        
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
        List<Court> courtList = Club.getInstance().getCourts();
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
        displayCourtAvailability();
    }
    private void displayCourtAvailability() throws ClubDAOException, IOException{
        //to do
        fil1.setText("Free");
        fil2.setText("Free");
        fil3.setText("Free");
        fil4.setText("Free");
        fil5.setText("Free");
        fil6.setText("Free");
        fil7.setText("Free");
        fil8.setText("Free");
        fil9.setText("Free");
        fil10.setText("Free");
        fil11.setText("Free");
        fil12.setText("Free");
        fil13.setText("Free");        
        
        isBooked1.setValue(Boolean.FALSE);
        isBooked2.setValue(Boolean.FALSE);
        isBooked3.setValue(Boolean.FALSE);
        isBooked4.setValue(Boolean.FALSE);
        isBooked5.setValue(Boolean.FALSE);
        isBooked6.setValue(Boolean.FALSE);
        isBooked7.setValue(Boolean.FALSE);
        isBooked8.setValue(Boolean.FALSE);
        isBooked9.setValue(Boolean.FALSE);
        isBooked10.setValue(Boolean.FALSE);
        isBooked11.setValue(Boolean.FALSE);
        isBooked12.setValue(Boolean.FALSE);
        isBooked13.setValue(Boolean.FALSE);
        
        fil1.getStyleClass().clear();fil1.getStyleClass().add("toggle-button");
        fil2.getStyleClass().clear();fil2.getStyleClass().add("toggle-button");
        fil3.getStyleClass().clear();fil3.getStyleClass().add("toggle-button");
        fil4.getStyleClass().clear();fil4.getStyleClass().add("toggle-button");
        fil5.getStyleClass().clear();fil5.getStyleClass().add("toggle-button");
        fil6.getStyleClass().clear();fil6.getStyleClass().add("toggle-button");
        fil7.getStyleClass().clear();fil7.getStyleClass().add("toggle-button");
        fil8.getStyleClass().clear();fil8.getStyleClass().add("toggle-button");
        fil9.getStyleClass().clear();fil9.getStyleClass().add("toggle-button");
        fil10.getStyleClass().clear();fil10.getStyleClass().add("toggle-button");
        fil11.getStyleClass().clear();fil11.getStyleClass().add("toggle-button");
        fil12.getStyleClass().clear();fil12.getStyleClass().add("toggle-button");
        fil13.getStyleClass().clear();fil13.getStyleClass().add("toggle-button");
        
        
        List<Booking> b = Club.getInstance().getCourtBookings(getMyCourt().getName(), day.getValue());
        for (Booking bi : b){
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String t = bi.getFromTime().format(timeFormatter);
            switch(t){
                case "09:00" :
                    fil1.setText("Booked");
                    isBooked1.setValue(Boolean.TRUE);
                    fil1.getStyleClass().clear();fil1.getStyleClass().add("toggle-button-occupied");
                    break;
                case "10:00" :
                    fil2.setText("Booked");
                    isBooked2.setValue(Boolean.TRUE);
                    fil2.getStyleClass().clear();fil2.getStyleClass().add("toggle-button-occupied");
                    break;
                case "11:00" :
                    fil3.setText("Booked");
                    isBooked3.setValue(Boolean.TRUE);
                    fil3.getStyleClass().clear();fil3.getStyleClass().add("toggle-button-occupied");
                    break;
                case "12:00" :
                    fil4.setText("Booked");
                    isBooked4.setValue(Boolean.TRUE);
                    fil4.getStyleClass().clear();fil4.getStyleClass().add("toggle-button-occupied");
                    break;
                case "13:00" :
                    fil5.setText("Booked");
                    isBooked5.setValue(Boolean.TRUE);
                    fil5.getStyleClass().clear();fil5.getStyleClass().add("toggle-button-occupied");
                    break;
                case "14:00" :
                    fil6.setText("Booked");
                    isBooked6.setValue(Boolean.TRUE);
                    fil6.getStyleClass().clear();fil6.getStyleClass().add("toggle-button-occupied");
                    break;
                case "15:00" :
                    fil7.setText("Booked");
                    isBooked7.setValue(Boolean.TRUE);
                    fil7.getStyleClass().clear();fil7.getStyleClass().add("toggle-button-occupied");
                    break;
                case "16:00" :
                    fil8.setText("Booked");
                    isBooked8.setValue(Boolean.TRUE);
                    fil8.getStyleClass().clear();fil8.getStyleClass().add("toggle-button-occupied");
                    break;
                case "17:00" :
                    fil9.setText("Booked");
                    isBooked9.setValue(Boolean.TRUE);
                    fil9.getStyleClass().clear();fil9.getStyleClass().add("toggle-button-occupied");
                    break;
                case "18:00" :
                    fil10.setText("Booked");
                    isBooked10.setValue(Boolean.TRUE);
                    fil10.getStyleClass().clear();fil10.getStyleClass().add("toggle-button-occupied");
                    break;
                case "19:00" :
                    fil11.setText("Booked");
                    isBooked11.setValue(Boolean.TRUE);
                    fil11.getStyleClass().clear();fil11.getStyleClass().add("toggle-button-occupied");
                    break;
                case "20:00" :
                    fil12.setText("Booked");
                    isBooked12.setValue(Boolean.TRUE);
                    fil12.getStyleClass().clear();fil12.getStyleClass().add("toggle-button-occupied");
                    break;
                case "21:00" :
                    fil13.setText("Booked");
                    isBooked13.setValue(Boolean.TRUE);
                    fil13.getStyleClass().clear();fil13.getStyleClass().add("toggle-button-occupied");
                    break;
            }
        }
    }

    @FXML
    private void fil1clicked(ActionEvent event) {
        isSelected = true;
        index = 0;
        myTime = LocalTime.of(9, 0);
        fil1.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil2clicked(ActionEvent event) {
        isSelected = true;
        index = 1;
        myTime = LocalTime.of(10, 0);
        fil2.setSelected(true);
        fil1.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil3clicked(ActionEvent event) {
        isSelected = true;
        index = 2;
        myTime = LocalTime.of(11, 0);
        fil3.setSelected(true);
        fil2.setSelected(false);
        fil1.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil4clicked(ActionEvent event) {
        isSelected = true;
        index = 3;
        myTime = LocalTime.of(12, 0);
        fil4.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil1.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil5clicked(ActionEvent event) {
        isSelected = true;
        index = 4;
        myTime = LocalTime.of(13, 0);
        fil5.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil1.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil6clicked(ActionEvent event) {
        isSelected = true;
        index = 5;
        myTime = LocalTime.of(14, 0);
        fil6.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil1.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil7clicked(ActionEvent event) {
        isSelected = true;
        index = 6;
        myTime = LocalTime.of(15, 0);
        fil7.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil1.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil8clicked(ActionEvent event) {
        isSelected = true;
        index = 7;
        myTime = LocalTime.of(16, 0);
        fil8.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil1.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil11clicked(ActionEvent event) {
        isSelected = true;
        index = 10;
        myTime = LocalTime.of(19, 0);
        fil11.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil1.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil9clicked(ActionEvent event) {
        isSelected = true;
        index = 8;
        myTime = LocalTime.of(17, 0);
        fil9.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil1.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil10clicked(ActionEvent event) {
        isSelected = true;
        index = 9;
        myTime = LocalTime.of(18, 0);
        fil10.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil1.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil12clicked(ActionEvent event) {
        isSelected = true;
        index = 11;
        myTime = LocalTime.of(20, 0);
        fil12.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil1.setSelected(false);
        fil13.setSelected(false);
    }

    @FXML
    private void fil13clicked(ActionEvent event) {
        isSelected = true;
        index = 12;
        myTime = LocalTime.of(21, 0);
        fil13.setSelected(true);
        fil2.setSelected(false);
        fil3.setSelected(false);
        fil4.setSelected(false);
        fil5.setSelected(false);
        fil6.setSelected(false);
        fil7.setSelected(false);
        fil8.setSelected(false);
        fil9.setSelected(false);
        fil10.setSelected(false);
        fil11.setSelected(false);
        fil12.setSelected(false);
        fil1.setSelected(false);
    }

    @FXML
    private void setFullscreen(ActionEvent event) {
        Stage stage = (Stage) bookButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen)){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
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
