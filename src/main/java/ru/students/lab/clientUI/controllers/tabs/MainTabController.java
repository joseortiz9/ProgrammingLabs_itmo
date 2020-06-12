package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

    private final MainController mainController;

    public MainTabController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        loadFilteringOption();
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

    @FXML
    public void handleCommandByKeyButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        AbsCommand command = mainController.getContext().commandManager().getCommand(commandCalled);
        String arg = inputKeyDragon.getText();

        if (command.requireInput() == ICommand.TYPE_INPUT_DRAGON) {
            mainController.loadEditDragonDialog(new DragonEntrySerializable(Integer.parseInt(arg), null), false);
            return;
        }

        command.setArgs(new String[]{arg});

        mainController.getContext().clientChannel().sendCommand(new CommandPacket(command, mainController.getContext().responseHandler().getCurrentUser().getCredentials()));
        Object response = mainController.getContext().responseHandler().checkForResponse();

        if (response instanceof String) {
            AlertMaker.showSimpleAlert("Result of the request", (String)response);
            mainController.getContext().responseHandler().setReceivedObjectToNull();
            LOG.info("Result of commandWithKey process: {}", (String) response);
        }
    }

    @FXML
    public void handleRemoveDragonButtonAction(ActionEvent actionEvent) {
        String arg = inputKeyDragon.getText();
        DragonEntrySerializable selectedForRemove = mainController.getContext().localCollection().getByKey(Integer.parseInt(arg));
        mainController.loadRemoveDragonDialog(selectedForRemove);
    }

    @FXML
    public void handleCommandWithFileButtonAction(ActionEvent actionEvent) {
        String commandCalled = ((Control)actionEvent.getSource()).getId();
        TextInputDialog dialog = new TextInputDialog("example.txt");
        dialog.setTitle(commandCalled);
        dialog.setHeaderText("Running" + commandCalled);
        dialog.setContentText("Please specify the files name");
        Optional<String> answer = dialog.showAndWait();
        if (answer.isPresent()) {
            AbsCommand command = mainController.getContext().commandManager().getCommand(commandCalled);
            mainController.getContext().clientChannel().sendCommand(new CommandPacket(command, mainController.getContext().responseHandler().getCurrentUser().getCredentials()));
            command.setArgs(new String[]{answer.get()});
            Object response = mainController.getContext().responseHandler().checkForResponse();

            if (response instanceof String) {
                AlertMaker.showSimpleAlert("Result of the request", (String)response);
                mainController.getContext().responseHandler().setReceivedObjectToNull();
                LOG.info("Result of the fileRelatedCommand process: {}", (String) response);
            }
        } else {
            AlertMaker.showSimpleAlert("Remove cancelled", "Remove process cancelled");
        }
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent actionEvent) {
        AbsCommand command = mainController.getContext().commandManager().getCommand("info");
        mainController.getContext().clientChannel().sendCommand(new CommandPacket(command, mainController.getContext().responseHandler().getCurrentUser().getCredentials()));
        Object response = mainController.getContext().responseHandler().checkForResponse();

        if (response instanceof String) {
            AlertMaker.showSimpleAlert("Result of the request", (String)response);
            mainController.getContext().responseHandler().setReceivedObjectToNull();
            LOG.info("Result of the info request: {}", (String) response);
        }
    }

    @FXML
    public void handleClearButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing ALL Dragon");
        alert.setContentText("Are you sure want to remove ALL dragons ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            AbsCommand command = mainController.getContext().commandManager().getCommand("clear");
            mainController.getContext().clientChannel().sendCommand(new CommandPacket(command, mainController.getContext().responseHandler().getCurrentUser().getCredentials()));
            Object response = mainController.getContext().responseHandler().checkForResponse();

            if (response instanceof String) {
                AlertMaker.showSimpleAlert("Result of the request", (String)response);
                //loadData();
                mainController.getContext().responseHandler().setReceivedObjectToNull();
                LOG.info("Result of the clearing process: {}", (String) response);
            }
        } else {
            AlertMaker.showSimpleAlert("Remove cancelled", "Remove process cancelled");
        }
    }

    @FXML
    public void handleExitButtonAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
