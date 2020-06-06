package ru.students.lab.clientUI.controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.controllers.tabs.HelpTabController;
import ru.students.lab.clientUI.controllers.tabs.MainTabController;
import ru.students.lab.clientUI.controllers.tabs.MapTabController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @FXML private JFXTabPane mainTabPane;
    @FXML private Tab mainTab, mapTab, helpTab;
    private final ClientContext clientContext;

    public MainController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComponents();
    }

    private void loadComponents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tabs/main_tab.fxml"));
            loader.setController(new MainTabController(clientContext));
            Parent root = loader.load();
            mainTab.setContent(root);

            FXMLLoader mapLoader = new FXMLLoader(getClass().getResource("/views/tabs/map_tab.fxml"));
            mapLoader.setController(new MapTabController(clientContext));
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

    @FXML
    public void handleRefresh(ActionEvent actionEvent) {

    }

    @FXML
    public void handleDragonEdit(ActionEvent actionEvent) {

    }

    @FXML
    public void handleDragonRemove(ActionEvent actionEvent) {

    }
}
