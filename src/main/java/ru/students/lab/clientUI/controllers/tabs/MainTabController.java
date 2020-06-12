package ru.students.lab.clientUI.controllers.tabs;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.controllers.MainController;
import ru.students.lab.clientUI.controllers.forms.AddDragonController;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.models.*;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.DragonEntrySerializable;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    private final ObservableList<DragonEntrySerializable> dragonsList = FXCollections.observableArrayList();

    @FXML public GridPane commandsBtnGrid;
    @FXML public TableView<DragonEntrySerializable> dragonsTableView;
    @FXML public TableColumn<DragonEntrySerializable, Integer> idCol;
    @FXML public TableColumn<DragonEntrySerializable, Integer> keyCol;
    @FXML public TableColumn<DragonEntrySerializable, String> nameCol;
    @FXML public TableColumn<DragonEntrySerializable, Coordinates> coordinatesCol;
    @FXML public TableColumn<DragonEntrySerializable, ZonedDateTime> dateCol;
    @FXML public TableColumn<DragonEntrySerializable, Long> ageCol;
    @FXML public TableColumn<DragonEntrySerializable, Color> colorCol;
    @FXML public TableColumn<DragonEntrySerializable, DragonType> typeCol;
    @FXML public TableColumn<DragonEntrySerializable, DragonCharacter> characterCol;
    @FXML public TableColumn<DragonEntrySerializable, DragonHead> headCol;

    private final MainController mainController;

    public MainTabController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCommandButtons();
        initCol();
        loadData();
    }

    private void initCommandButtons() {
        int x = 0, y = 0;
        for (String commandKey: mainController.getContext().commandManager().getCommands().keySet()) {
            final Button btnTemp = new Button(commandKey);
            btnTemp.setId(commandKey);
            btnTemp.setOnAction(this::handleButtonCommandPressed);
            if (x == 6) {
                y++;
                x = 0;
            }
            commandsBtnGrid.add(btnTemp, x, y);  //x is column index and 0 is row index
            x++;
        }
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinatesCol.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        characterCol.setCellValueFactory(new PropertyValueFactory<>("character"));
        headCol.setCellValueFactory(new PropertyValueFactory<>("head"));
    }

    private void loadData() {
        //mainController.refreshLocalCollection();
        dragonsList.clear();
        dragonsList.addAll(mainController.getContext().localCollection().getLocalList());
        dragonsTableView.setItems(dragonsList);
    }

    @FXML
    public void handleButtonCommandPressed(ActionEvent actionEvent) {
        String buttonClicked = ((Control)actionEvent.getSource()).getId();
        System.out.println(buttonClicked);
    }

    @FXML
    public void handleRefresh(ActionEvent actionEvent) {
        loadData();
    }

    @FXML
    public void handleDragonEdit(ActionEvent actionEvent) {
        // Get selected row
        DragonEntrySerializable selectedForEdit = dragonsTableView.getSelectionModel().getSelectedItem();
        mainController.loadEditDragonDialog(selectedForEdit, true);
    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {
        // Get selected row
        DragonEntrySerializable selectedForRemove = dragonsTableView.getSelectionModel().getSelectedItem();
        mainController.loadRemoveDragonDialog(selectedForRemove);
    }
}
