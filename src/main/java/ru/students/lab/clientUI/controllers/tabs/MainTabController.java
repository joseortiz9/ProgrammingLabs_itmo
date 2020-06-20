package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.controllers.MainController;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.models.*;
import ru.students.lab.util.DragonEntrySerializable;
import ru.students.lab.util.FxUtils;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    private final ObservableList<DragonEntrySerializable> dragonsList = FXCollections.observableArrayList();

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
        //dateCol.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().getCreationDate().format(dateFormatter)));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        dateCol.setCellFactory(column -> handleFormatCellDateCreation());
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        characterCol.setCellValueFactory(new PropertyValueFactory<>("character"));
        headCol.setCellValueFactory(new PropertyValueFactory<>("head"));
    }

    private TableCell<DragonEntrySerializable, ZonedDateTime> handleFormatCellDateCreation() {
        return new TableCell<DragonEntrySerializable, ZonedDateTime>() {
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)
                    setText(null);
                else
                    this.setText(FxUtils.formatZonedDateTimeValue(item));
            }
        };
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
    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {
        // Get selected row
        DragonEntrySerializable selectedForRemove = dragonsTableView.getSelectionModel().getSelectedItem();
        mainController.loadRemoveDragonDialog(selectedForRemove);
    }

    @FXML
    public void handleCommandByKeyButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        AbsCommand command = mainController.getContext().commandManager().getCommand(commandCalled);

        String arg = inputKeyDragon.getText();
        if (!inputKeyDragon.validate()) {
            AlertMaker.showSimpleAlert(bundle.getString("tab.main.alert.validation.error.title"), bundle.getString("tab.main.alert.validation.error.content"));
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
            AlertMaker.showSimpleAlert(bundle.getString("tab.main.alert.validation.error.title"), bundle.getString("tab.main.alert.validation.error.content"));
            return;
        }

        DragonEntrySerializable selectedForRemove = mainController.getContext().localCollection().getByKey(Integer.parseInt(arg));
        mainController.loadRemoveDragonDialog(selectedForRemove);
    }

    @FXML
    public void handleCommandWithFileButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        String title = MessageFormat.format(bundle.getString("dashboard.alert.commandwithfile.content"), commandCalled);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File selectedFile = fileChooser.showOpenDialog(dragonsTableView.getScene().getWindow());
        if (selectedFile != null) {
            mainController.sendRequest(commandCalled, new String[]{selectedFile.getAbsolutePath()});
        } else {
            AlertMaker.showSimpleAlert(bundle.getString("dashboard.alert.error.remove.cancelled.title"), bundle.getString("dashboard.alert.error.remove.cancelled.content"));
        }
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent actionEvent) {
        mainController.sendRequest("info", null);
    }

    @FXML
    public void handleClearButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("dashboard.alert.command.clear.title"));
        alert.setContentText(bundle.getString("dashboard.alert.command.clear.content"));
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK)
            mainController.sendRequest("clear", null);
        else
            AlertMaker.showSimpleAlert(bundle.getString("dashboard.alert.error.remove.cancelled.title"), bundle.getString("dashboard.alert.error.remove.cancelled.content"));
    }

    @FXML
    public void handleExitButtonAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
