package ru.students.lab.clientUI.controllers.tabs;

import javafx.fxml.Initializable;
import ru.students.lab.clientUI.ClientContext;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpTabController implements Initializable {

    private ClientContext clientContext;

    public HelpTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
