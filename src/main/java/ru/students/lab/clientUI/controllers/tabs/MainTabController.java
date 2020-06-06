package ru.students.lab.clientUI.controllers.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.controllers.LoginRegisterController;
import ru.students.lab.clientUI.controllers.forms.AddDragonController;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.models.*;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.ListEntrySerializable;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MainTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    ObservableList<Dragon> dragonsList = FXCollections.observableArrayList();

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

    private final ClientContext clientContext;

    public MainTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCommandButtons();
        initCol();
        loadData();
    }

    private void initCommandButtons() {
        int x = 0, y = 0;
        for (String commandKey: clientContext.commandManager().getCommands().keySet()) {
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
        dragonsList.clear();
        AbsCommand command = clientContext.commandManager().getCommand("show");
        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof List) {
            List<ListEntrySerializable> list = (List<ListEntrySerializable>) response;
            for (ListEntrySerializable dragonEntry: list) {
                dragonEntry.getDragon().setKey(dragonEntry.getKey());
                dragonsList.add(dragonEntry.getDragon());
            }

            LOG.info("Successfully fetched collection: {} elements", list.size());
            clientContext.responseHandler().setReceivedObjectToNull();
        } else {
            AlertMaker.showErrorMessage("Not possible to fetch data", (String)response);
        }

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
        Dragon selectedForEdit = dragonsTableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No dragon selected", "Please select a dragon for edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/forms/add_dragon.fxml"));
            AddDragonController controller = new AddDragonController(clientContext);
            loader.setController(controller);
            Parent parent = loader.load();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Dragon");
            stage.setScene(new Scene(parent));
            stage.show();

            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            LOG.error("error trying to edit a dragon from list, ", ex);
        }
    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {

    }
}
