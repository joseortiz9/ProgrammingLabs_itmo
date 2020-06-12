package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.canvas.AbsResizableCanvas;
import ru.students.lab.clientUI.canvas.ResizableDragonPictureCanvas;
import ru.students.lab.clientUI.canvas.ResizableMapCanvas;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.DragonEntrySerializable;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    @FXML public Pane wrapperMapPane, dragonPicturePane;
    @FXML public JFXButton editDragonButton, removeDragonButton;
    @FXML public GridPane dragonDetailsGrid;

    private final ClientContext clientContext;
    private ArrayList<DragonEntrySerializable> dragonsList = new ArrayList<>();

    public MapTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    private void loadData() {
        dragonsList.clear();
        AbsCommand command = clientContext.commandManager().getCommand("show");
        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof List) {
            dragonsList = (ArrayList<DragonEntrySerializable>) response;

            LOG.info("Successfully fetched collection for the map: {} elements", dragonsList.size());
            clientContext.responseHandler().setReceivedObjectToNull();
        } else {
            AlertMaker.showErrorMessage("Not possible to fetch data", (String)response);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();

        // Map canvas init
        int actualUserID = clientContext.responseHandler().getCurrentUser().getCredentials().id;
        AbsResizableCanvas dragonsMapCanvas = new ResizableMapCanvas(dragonsList, actualUserID);
        wrapperMapPane.getChildren().add(dragonsMapCanvas);

        dragonsMapCanvas.widthProperty().bind(wrapperMapPane.widthProperty());
        dragonsMapCanvas.heightProperty().bind(wrapperMapPane.heightProperty());
        dragonsMapCanvas.setOnMouseClicked(event -> {
            //DragonUserCouple dragon = (DragonUserCouple)dragonsMapCanvas.findObj(event.getSceneX(), event.getSceneY());
            //handleDetailDragon(dragon);
            handleDetailDragon(dragonsList.get(1));
        });

        // dragon canvas picture init
        AbsResizableCanvas dragonPictureCanvas = new ResizableDragonPictureCanvas();
        dragonPicturePane.getChildren().add(dragonPictureCanvas);
        dragonPictureCanvas.widthProperty().bind(dragonPicturePane.widthProperty());
        dragonPictureCanvas.heightProperty().bind(dragonPicturePane.heightProperty());
    }

    public void handleDetailDragon(DragonEntrySerializable dragon) {
        if (dragon == null)
            return;

        changeStatusActionButtons(dragon.getDragon().getUserID());

        try {
            loadingDragonFields(dragon);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            AlertMaker.showErrorMessage("Problems fetching attrs of dragon", ex.getMessage());
        }
    }

    private void changeStatusActionButtons(int userID) {
        if (clientContext.responseHandler().getCurrentUser().getCredentials().id != userID) {
            editDragonButton.setDisable(true);
            removeDragonButton.setDisable(true);
        } else {
            editDragonButton.setDisable(false);
            removeDragonButton.setDisable(false);
        }
    }

    private void loadingDragonFields(DragonEntrySerializable dragon) throws IllegalAccessException {
        int x = 0, y = 0;
        for (Field dragonField: dragon.getDragon().getClass().getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(dragonField.getModifiers()))
                continue;

            dragonField.setAccessible(true);
            final Label attrTemp = new Label(dragonField.getName() + ": " + dragonField.get(dragon.getDragon()).toString());
            attrTemp.getStyleClass().add("simple-text");
            if (x == 2) {
                y++;
                x = 0;
            }
            dragonDetailsGrid.add(attrTemp, x, y);  //x is column index and 0 is row index
            x++;
        }
    }

    @FXML
    public void handleEditDragonButtonAction(ActionEvent event) {

    }

    @FXML
    public void handleRemoveDragonButtonAction(ActionEvent event) {

    }
}

