package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import ru.students.lab.commands.ICommand;
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
    @FXML public JFXTextField inputDragonSearch;
    @FXML public JFXTextField inputKeyDragon;
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
    private ResourceBundle bundle;

    private final MainController mainController;

    public MainTabController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        initCol();
        refreshData();
        loadFilteringOption();

        inputKeyDragon.getValidators().addAll(new NumberValidator(), new RequiredFieldValidator());
    }

    public void refreshData() {
        dragonsList.clear();
        dragonsList.addAll(mainController.getContext().localCollection().getLocalList());
        dragonsTableView.setItems(dragonsList);
        loadFilteringOption();
    }

    @FXML
    public void handleRefresh(ActionEvent actionEvent) {
        refreshData();
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

    public void loadFilteringOption() {
        // Filter commands by name functionality
        FilteredList<DragonEntrySerializable> filteredData = new FilteredList<>(dragonsList, s -> true);
        inputDragonSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(dragonEntry -> {
                if (newVal == null || newVal.length() == 0)
                    return true;

                String writtenText = newVal.toLowerCase();
                if (String.valueOf(dragonEntry.getKey()).contains(writtenText))
                    return true;
                else if (String.valueOf(dragonEntry.getId()).contains(writtenText))
                    return true;
                else if (dragonEntry.getName().toLowerCase().contains(writtenText))
                    return true;
                else if (dragonEntry.getColor().toString().toLowerCase().contains(writtenText))
                    return true;
                else
                    return false;
            });
        });
        SortedList<DragonEntrySerializable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(dragonsTableView.comparatorProperty());
        dragonsTableView.setItems(sortedData);
    }

    @FXML
    public void handleDragonEdit(ActionEvent actionEvent) {
        // Get selected row
        DragonEntrySerializable selectedForEdit = dragonsTableView.getSelectionModel().getSelectedItem();
        mainController.loadEditDragonDialog(selectedForEdit, true, false);
        refreshData();
    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {
        // Get selected row
        DragonEntrySerializable selectedForRemove = dragonsTableView.getSelectionModel().getSelectedItem();
        mainController.loadRemoveDragonDialog(selectedForRemove);
        refreshData();
    }

    @FXML
    public void handleCommandByKeyButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        AbsCommand command = mainController.getContext().commandManager().getCommand(commandCalled);

        String arg = inputKeyDragon.getText();
        if (!inputKeyDragon.validate()) {
            AlertMaker.showSimpleAlert("Validation Error!", "The input is not a valid number");
            return;
        }

        if (command.requireInput() == ICommand.TYPE_INPUT_DRAGON) {
            if (commandCalled.equals("update")) {
                DragonEntrySerializable selectedToEdit = mainController.getContext().localCollection().getByKey(Integer.parseInt(arg));
                mainController.loadEditDragonDialog(selectedToEdit, true, false);
            } else {
                mainController.loadEditDragonDialog(new DragonEntrySerializable(Integer.parseInt(arg), null), false, true);
            }
            return;
        }

        mainController.sendRequest(commandCalled, new String[]{arg});
        refreshData();
    }

    @FXML
    public void handleRemoveDragonButtonAction(ActionEvent actionEvent) {
        String arg = inputKeyDragon.getText();
        if (!inputKeyDragon.validate()) {
            AlertMaker.showSimpleAlert("Validation Error!", "The input is not a valid number");
            return;
        }

        DragonEntrySerializable selectedForRemove = mainController.getContext().localCollection().getByKey(Integer.parseInt(arg));
        mainController.loadRemoveDragonDialog(selectedForRemove);
        refreshData();
    }

    @FXML
    public void handleCommandWithFileButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        TextInputDialog dialog = new TextInputDialog("example.txt");
        dialog.setTitle(commandCalled);
        dialog.setHeaderText("Executing '" + commandCalled + "'");
        dialog.setContentText("Please specify the files name");
        Optional<String> answer = dialog.showAndWait();
        if (answer.isPresent()) {
            mainController.sendRequest(commandCalled, new String[]{answer.get()});
            refreshData();
        } else {
            AlertMaker.showSimpleAlert("Remove cancelled", "Remove process cancelled");
        }
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent actionEvent) {
        mainController.sendRequest("info", null);
    }

    @FXML
    public void handleClearButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing ALL Dragon");
        alert.setContentText("Are you sure want to remove ALL dragons ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            mainController.sendRequest("clear", null);
            refreshData();
        } else {
            AlertMaker.showSimpleAlert("Remove cancelled", "Remove process cancelled");
        }
    }

    @FXML
    public void handleExitButtonAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
