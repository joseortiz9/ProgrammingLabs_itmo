package ru.students.lab.clientUI.controllers.forms;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.models.*;
import ru.students.lab.network.CommandPacket;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDragonController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(AddDragonController.class);

    public interface Func<T, V> {
        boolean compare(T t, V v);
    }

    @FXML public StackPane rootPane;
    @FXML public Label idLabel;
    @FXML public JFXTextField keyTextField;
    @FXML public JFXTextField nameTextField;
    @FXML public JFXTextField coordinateX;
    @FXML public JFXTextField coordinateY;
    @FXML public JFXTextField ageTextField;
    @FXML public JFXComboBox<Label> colorBox;
    @FXML public JFXComboBox<Label> typeBox;
    @FXML public JFXComboBox<Label> characterBox;
    @FXML public JFXTextField headTextField;

    private final ClientContext clientContext;
    private boolean editMode = false;
    private int editingID = -1;

    public AddDragonController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Color color: Color.values())
            colorBox.getItems().add(new Label(color.name()));

        for (DragonType type: DragonType.values())
            typeBox.getItems().add(new Label(type.name()));

        for (DragonCharacter character: DragonCharacter.values())
            characterBox.getItems().add(new Label(character.name()));
    }

    @FXML
    private void cancelOperation(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public boolean validationGetsError() {
        return false;
    }

    @FXML
    public void addDragon(ActionEvent actionEvent) {
        if (validationGetsError()) {
            return;
        }

        DragonHead head = new DragonHead(
                (headTextField.getText().equals("null") || Double.parseDouble(headTextField.getText()) == 0)
                    ? null
                    : (Double.parseDouble(headTextField.getText()))) ;
        Dragon dragon = new Dragon(
                editingID,
                nameTextField.getText(),
                new Coordinates(Long.parseLong(coordinateX.getText()), Float.parseFloat(coordinateY.getText())),
                Long.parseLong(ageTextField.getText()),
                Color.valueOf(colorBox.getSelectionModel().getSelectedItem().getText()),
                DragonType.valueOf(typeBox.getSelectionModel().getSelectedItem().getText()),
                DragonCharacter.valueOf(characterBox.getSelectionModel().getSelectedItem().getText()),
                head);

        if (editMode) {
            handleRequestSent(dragon, dragon.getId().toString(), "update");
            editMode = false;
        } else
            handleRequestSent(dragon, keyTextField.getText(), "insert");
    }

    public void handleRequestSent(Dragon input, String arg, String commandKey) {
        AbsCommand command = clientContext.commandManager().getCommand(commandKey);
        command.setArgs(new String[]{arg});
        command.addInput(input);

        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof String) {
            AlertMaker.showSimpleAlert("Result of the request", (String)response);
            clientContext.responseHandler().setReceivedObjectToNull();
            cleanEntries();
            cancelOperation(new ActionEvent());
            LOG.info("Result of the insert/update process: {}", (String) response);
        }
    }

    public void cleanEntries() {
        idLabel.setText("ID = ?");
        keyTextField.setText("");
        nameTextField.setText("");
        coordinateX.setText("");
        coordinateY.setText("");
        ageTextField.setText("");
        colorBox.getSelectionModel().clearSelection();
        typeBox.getSelectionModel().clearSelection();
        characterBox.getSelectionModel().clearSelection();
        headTextField.setText("");
    }

    public void inflateUI(Dragon dragon) {
        String headText = (dragon.getHead().getEyesCount() != null) ? dragon.getHead().getEyesCount().toString() : "null";
        idLabel.setText("ID= " + dragon.getId().toString());
        editingID = dragon.getId();
        keyTextField.setText(dragon.getKey().toString());
        nameTextField.setText(dragon.getName());
        coordinateX.setText(dragon.getCoordinates().getX().toString());
        coordinateY.setText(dragon.getCoordinates().getY().toString());
        ageTextField.setText(dragon.getAge().toString());
        headTextField.setText(headText);
        autoSelectComboBoxValue(colorBox, dragon.getColor(), (color, colorBoxVal) -> color.equals(Enum.valueOf(Color.class, colorBoxVal)));
        autoSelectComboBoxValue(typeBox, dragon.getType(), (type, typeBoxVal) -> type.equals(Enum.valueOf(DragonType.class, typeBoxVal)));
        autoSelectComboBoxValue(characterBox, dragon.getCharacter(), (character, characterBoxVal) -> character.equals(Enum.valueOf(DragonCharacter.class, characterBoxVal)));
        keyTextField.setEditable(false);
        editMode = true;
    }

    public <T> void autoSelectComboBoxValue(JFXComboBox<Label> comboBox, T value, Func<T, String> f) {
        for (Label t : comboBox.getItems())
            if (f.compare(value, t.getText()))
                comboBox.setValue(t);
    }
}
