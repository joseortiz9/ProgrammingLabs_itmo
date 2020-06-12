package ru.students.lab.clientUI.controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.controllers.forms.AddDragonController;
import ru.students.lab.clientUI.controllers.tabs.HelpTabController;
import ru.students.lab.clientUI.controllers.tabs.MainTabController;
import ru.students.lab.clientUI.controllers.tabs.MapTabController;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.models.Dragon;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.DragonEntrySerializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @FXML private JFXTabPane mainTabPane;
    @FXML private AnchorPane rootAnchorPane;
    @FXML private Tab mainTab, mapTab, helpTab;
    @FXML private Label currentUserLabel;
    private final ClientContext clientContext;

    public MainController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUserLabel.setText("Current User: " + clientContext.responseHandler().getCurrentUser().getCredentials().username);
        refreshLocalCollection();
        loadComponents();
    }

    private void loadComponents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tabs/main_tab.fxml"));
            loader.setController(new MainTabController(this));
            Parent root = loader.load();
            mainTab.setContent(root);

            FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("/views/tabs/map_tab.fxml"));
            mapLoader.setController(new MapTabController(this));
            Parent mapRoot = mapLoader.load();
            mapTab.setContent(mapRoot);

            /*mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                if(newTab == mapTab) {
                }
            });*/

            loader = new FXMLLoader(getClass().getResource("/views/tabs/help_tab.fxml"));
            loader.setController(new HelpTabController(clientContext));
            root = loader.load();
            helpTab.setContent(root);


            /* loader = new FXMLLoader(getClass().getResource("/views/menu/main_menu.fxml"));
            loader.setController(new MenuController(this));
             root = loader.load();
            menuPane.set(root);*/
        }
        catch(IOException ex) {
            LOG.error("unable to load tabs", ex);
        }
    }


    public void refreshLocalCollection() {
        clientContext.localCollection().getLocalList().clear();
        AbsCommand command = clientContext.commandManager().getCommand("show");
        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof List) {
            clientContext.localCollection().getLocalList().addAll((List<DragonEntrySerializable>) response);

            LOG.info("Successfully fetched collection: {} elements", clientContext.localCollection().getLocalList().size());
            clientContext.responseHandler().setReceivedObjectToNull();
        } else {
            AlertMaker.showErrorMessage("Not possible to fetch data, please check logs", (String)response);
        }
    }


    /**
     *
     * If the button is pressed from the main window command section, gets a DragonEntrySerializable with the
     * key set but the dragon null and editMode in false, so the checks are to fill the received key in the form.
     * When is trying to insert receives the DragonEntrySerializable totally null and ediMode is false
     *
     * @param selectedForEdit dragon to edit
     * @param editMode trying to update the dragon
     */
    public void loadEditDragonDialog(DragonEntrySerializable selectedForEdit, boolean editMode) {
        if (selectedForEdit != null) {
            if (selectedForEdit.getDragon() == null && editMode) {
                AlertMaker.showErrorMessage("No dragon selected", "Please select a dragon for edit.");
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/forms/add_dragon.fxml"));
            AddDragonController controller = new AddDragonController(clientContext, editMode);
            loader.setController(controller);
            Parent parent = loader.load();
            if (editMode || selectedForEdit.getDragon() == null)
                controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle((editMode) ? "Edit Dragon" : "Insert Dragon");
            stage.setScene(new Scene(parent));
            stage.show();

            /*stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });*/

        } catch (IOException ex) {
            LOG.error("error trying to edit/insert a dragon, ", ex);
        }
    }


    public void loadRemoveDragonDialog(DragonEntrySerializable selectedForRemove) {
        if (selectedForRemove == null) {
            AlertMaker.showErrorMessage("No dragon selected", "Please select a dragon to remove or pass a correct key.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing Dragon");
        alert.setContentText("Are you sure want to remove the dragon {ID="+ selectedForRemove.getId() +"} ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            AbsCommand command = clientContext.commandManager().getCommand("remove_key");
            command.setArgs(new String[]{String.valueOf(selectedForRemove.getKey())});

            clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

            Object response = clientContext.responseHandler().checkForResponse();

            if (response instanceof String) {
                AlertMaker.showSimpleAlert("Result of the request", (String)response);
                //loadData();
                clientContext.responseHandler().setReceivedObjectToNull();
                LOG.info("Result of the deleting process: {}", (String) response);
            }
        } else {
            AlertMaker.showSimpleAlert("Remove cancelled", "Remove process cancelled");
        }
    }


    public ClientContext getContext() {
        return clientContext;
    }


    private void closeWindow() {
        clientContext.responseHandler().setCurrentUser(new Credentials(-1, UserModel.DEFAULT_USERNAME, ""));
        Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMenuLogOut(ActionEvent actionEvent) {
        try {
            closeWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login_register.fxml"));
            loader.setController(new LoginRegisterController(clientContext));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Dragons World");
            stage.setScene(new Scene(root));
            stage.show();
            LOG.info("Logged out!");
        } catch (IOException ex) {
            LOG.error("Error logging out!", ex);
        }
    }


    @FXML
    public void handleMenuAddDragon(ActionEvent actionEvent) {
        loadEditDragonDialog(null, false);
    }

    @FXML
    public void changeLanguageToEnglish(ActionEvent actionEvent) {

    }

    @FXML
    public void changeLanguageToRussian(ActionEvent actionEvent) {

    }

    @FXML
    public void changeLanguageToSerbian(ActionEvent actionEvent) {

    }

    @FXML
    public void changeLanguageToCroatian(ActionEvent actionEvent) {

    }

    @FXML
    public void changeLanguageToSpanish(ActionEvent actionEvent) {

    }

    @FXML
    public void handleAboutMenu(ActionEvent actionEvent) {

    }
}
