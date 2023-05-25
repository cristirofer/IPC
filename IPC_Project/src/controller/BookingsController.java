/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import extra.Utils;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Javi
 */
public class BookingsController implements Initializable {
    
    private int fullScreen = 1;

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBarCenter;
    @FXML
    private MenuItem infoButton;
    @FXML
    private MenuItem exitButton;
    @FXML
    private VBox vBoxCenter;
    @FXML
    private Button backButton;
    @FXML
    private TableView<Booking> bookingTableView;
    @FXML
    private TableColumn<Booking, LocalDate> dayColumn;
    @FXML
    private TableColumn<Booking, Court> courtColumn;
    @FXML
    private TableColumn<Booking, LocalTime> dateColumn;
    @FXML
    private TableColumn<Booking, Boolean> paidColumn;
    @FXML
    private TableColumn<Void, Void> cancelCol;
    
    private String login;
    private ObservableList<Booking> obsList;
    @FXML
    private Circle profilePicContainer1;
    
    

    
    private void initializeModel() throws ClubDAOException, IOException {
        Image im = new Image("/resources/images/noprofile.jpg",false);
        try {
            im = Club.getInstance().getMemberByCredentials(LogInController.getMyNickname(),LogInController.getMyPassword()).getImage();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profilePicContainer1.setFill(new ImagePattern(im));
        
        bookingTableView.setEditable(true);
        
        List<Booking> bookingArrayList = new ArrayList<Booking>();
        login = LogInController.getMyNickname();
        
        bookingArrayList = Club.getInstance().getUserBookings(login);
        int startIndex = Math.max(0, bookingArrayList.size() - 10);
        List<Booking> lastTenBookings = bookingArrayList.subList(startIndex, bookingArrayList.size());
        obsList = FXCollections.observableArrayList(lastTenBookings);
        
        dayColumn.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            LocalDate madeForDay = booking.getMadeForDay();
            return new SimpleObjectProperty<>(madeForDay);
        });
        
        courtColumn.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            Court court = booking.getCourt();
            return new SimpleObjectProperty<>(court);
        });
        
        dateColumn.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            LocalTime date = booking.getFromTime();
            return new SimpleObjectProperty<>(date);
        });
        
        paidColumn.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            Boolean paid = false;
            try {
                paid = isItPaid(login);
            } catch (ClubDAOException ex) {
                Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new SimpleBooleanProperty(paid);
        });
        
        bookingTableView.setItems(obsList);
    }

    @FXML
    private void setFullscreen(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen)){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }
    
    class DateTableCell extends TableCell<Booking,LocalTime> {
        @Override
        protected void updateItem(LocalTime item, boolean empty) {
            super.updateItem(item, empty);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            if (empty || item == null) {
                setText(null);
            } else {
                setAlignment(Pos.CENTER);
                String firstHour = item.format(formatter);
                String lastHour = item.plusMinutes(60).format(formatter); 
                String res = "From: " + firstHour + " until " + lastHour;
                setText(res);
            }
        }
    } 
    
    class CourtTableCell extends TableCell<Booking,Court> {
        @Override
        protected void updateItem(Court item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setAlignment(Pos.CENTER);
                System.out.println(item.toString());
                char numero = item.getName().charAt(item.getName().length() - 1);
                String textDisplayed = "Court " + numero;
                setText(textDisplayed);
            }
        }
    } 
    
    class DayTableCell extends TableCell<Booking,LocalDate> {
        @Override
        protected void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setAlignment(Pos.CENTER);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = item.format(formatter);
                setText(formattedDate);
            }
        }
    } 
    
    class PaidTableCell extends TableCell<Booking,Boolean> {
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                String res="";
                if(item) {
                    res = "Paid";
                } else {
                    res = "Pending payment";
                }
                setText(res);
            }
        }
    } 
    /*
    public void initBooking(String login) {
        this.login = login;
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            initializeModel();
        } catch (ClubDAOException ex) {
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dateColumn.setCellFactory(c -> new DateTableCell());
        courtColumn.setCellFactory(c -> new CourtTableCell());
        dayColumn.setCellFactory(c -> new DayTableCell());
        paidColumn.setCellFactory(c -> new PaidTableCell());
        
        cancelCol.setCellFactory(column -> {
            return new TableCell<Void, Void>() {
                private final Button button = new Button("CANCEL");
                {
                    button.setMaxWidth(Double.MAX_VALUE);
                    button.setPrefWidth(Double.MAX_VALUE);
                }

                {
                    button.setOnAction(event -> {
                        TableRow<?> row = getTableRow();
                        int rowIndex = row.getIndex();
                        System.out.println(": "+ rowIndex);
                        
                        List<Booking> bookingArrayList = new ArrayList<Booking>();
                        login = LogInController.getMyNickname();
                        ObservableList<Booking> listaElementos = bookingTableView.getItems();
                        Booking removedBooking = listaElementos.get(rowIndex);
                        
                        LocalTime bookingTime = removedBooking.getFromTime();
                        LocalTime nowTime = LocalTime.now();
                        if(bookingTime.compareTo(nowTime) > 0) {
                            try {
                            Club.getInstance().removeBooking(removedBooking);
                            bookingTableView.getItems().remove(removedBooking);
                            bookingTableView.refresh();
                            } catch (ClubDAOException ex) {
                            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            // or AlertType.WARNING or AlertType.ERROR or AlertType.CONFIRMATION
                            alert.setTitle("Cancelation Not Allowed");
                            alert.setHeaderText(null);
                            // or null if we do not want a header
                            alert.setContentText("Cancelation not allowed for past bookings.");
                            alert.showAndWait();
                        }
                        
                        
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });
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
    private void returnToMain(ActionEvent event) throws IOException {
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
        backButton.getScene().getWindow().hide();
    }

    @FXML
    private void makeFullScreen(KeyEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        fullScreen++;
        if(Utils.isEven(fullScreen) && event.getCode() == KeyCode.F11){
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press F11 to exit fullscreen");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
        }
    }
  
    private boolean isItPaid(String login) throws ClubDAOException, IOException {
        return Club.getInstance().hasCreditCard(login);
    } 
}
