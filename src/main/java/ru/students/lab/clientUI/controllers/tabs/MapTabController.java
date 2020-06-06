package ru.students.lab.clientUI.controllers.tabs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.ResizableCanvas;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.DragonUserCouple;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    //@FXML public Canvas dragonsMapCanvas;
    @FXML public Pane wrapperMapPane;
    @FXML public AnchorPane anchorMapPane;

    private final ClientContext clientContext;
    private ArrayList<DragonUserCouple> dragonsList = new ArrayList<>();

    public MapTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    private void loadData() {
        dragonsList.clear();
        AbsCommand command = clientContext.commandManager().getCommand("fetch_map_elements");
        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof List) {
            dragonsList = (ArrayList<DragonUserCouple>) response;

            LOG.info("Successfully fetched collection for the map: {} elements", dragonsList.size());
            clientContext.responseHandler().setReceivedObjectToNull();
        } else {
            AlertMaker.showErrorMessage("Not possible to fetch data", (String)response);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();

        int actualUserID = clientContext.responseHandler().getCurrentUser().getCredentials().id;
        ResizableCanvas dragonsMapCanvas = new ResizableCanvas(dragonsList, actualUserID);
        wrapperMapPane.getChildren().add(dragonsMapCanvas);

        // Bind canvas size to stack pane size.
        dragonsMapCanvas.widthProperty().bind(wrapperMapPane.widthProperty());
        dragonsMapCanvas.heightProperty().bind(wrapperMapPane.heightProperty());
        dragonsMapCanvas.setOnMouseClicked(event -> {
            DragonUserCouple dragon = dragonsMapCanvas.findDragon(event.getSceneX(), event.getSceneY());
            System.out.println((dragon != null) ? dragon.getDragon().getId() : "Нет такого дракона!");
        });
    }
}

