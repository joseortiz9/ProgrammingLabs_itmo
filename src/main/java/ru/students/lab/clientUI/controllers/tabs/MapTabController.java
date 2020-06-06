package ru.students.lab.clientUI.controllers.tabs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import ru.students.lab.clientUI.ClientContext;

import java.net.URL;
import java.util.ResourceBundle;

public class MapTabController implements Initializable {

    @FXML public Canvas dragonsMap;
    private ClientContext clientContext;

    public MapTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
