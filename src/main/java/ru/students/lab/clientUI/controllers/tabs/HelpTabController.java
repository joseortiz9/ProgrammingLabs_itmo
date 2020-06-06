package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.models.Dragon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelpTabController implements Initializable {

    ObservableList<Label> commandsList = FXCollections.observableArrayList();

    @FXML public JFXTextField inputCommandKey;
    @FXML public JFXListView<Label> commandKeysListView;
    @FXML public Label detailsTitle;
    @FXML public Label detailsText;

    private final ClientContext clientContext;

    public HelpTabController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detailsText.setWrapText(true);

        List<Label> commands = clientContext.commandManager().getKeysCommands().stream().map(Label::new).collect(Collectors.toList());
        commandsList.addAll(commands);
        commandKeysListView.setItems(commandsList);

        /*FilteredList<String> filteredData = new FilteredList<>(commandsList.stream().map(Labeled::getText).collect(Collectors.toList()), s -> true);

        inputCommandKey.textProperty().addListener(obs -> {
            String filter = inputCommandKey.getText();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });*/

        commandKeysListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedItem = commandKeysListView.getSelectionModel().getSelectedItem().getText();
            String desc = clientContext.commandManager().getCommand(selectedItem).getDescription();
            detailsText.setText(desc);
        });
    }
}
