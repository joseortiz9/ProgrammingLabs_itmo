package ru.students.lab.clientUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ru.students.lab.models.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML public GridPane commandsBtnGrid;
    @FXML public TableView<Dragon> dragonsTableView;
    @FXML public TableColumn<Dragon, Integer> idCol;
    @FXML public TableColumn<Dragon, Integer> keyCol;
    @FXML public TableColumn<Dragon, String> nameCol;
    @FXML public TableColumn<Dragon, Coordinates> coordinatesCol;
    @FXML public TableColumn<Dragon, ZonedDateTime> dateCol;
    @FXML public TableColumn<Dragon, Long> ageCol;
    @FXML public TableColumn<Dragon, Color> colorCol;
    @FXML public TableColumn<Dragon, DragonType> typeCol;
    @FXML public TableColumn<Dragon, DragonCharacter> characterCol;
    @FXML public TableColumn<Dragon, DragonHead> headCol;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*while( z1 < 4){    //add 4 buttons
            addButton();
            z1++;
        }*/
    }

    private void addButton() {

    }

    @FXML
    public void handleRefresh(ActionEvent actionEvent) {

    }

    @FXML
    public void handleDragonEdit(ActionEvent actionEvent) {

    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {

    }
}
