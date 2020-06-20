package ru.students.lab.clientUI.controllers.forms;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import ru.students.lab.util.DragonEntrySerializable;

import java.net.URL;
import java.util.MissingResourceException;
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
    private ResourceBundle bundle;

    private final ClientContext clientContext;
    private boolean editMode = false;
    private int editingID = -1;

    public AddDragonController(ClientContext clientContext, boolean editMode) {
        this.clientContext = clientContext;
        this.editMode = editMode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        for (Color color: Color.values())
            colorBox.getItems().add(new Label(color.name()));

        for (DragonType type: DragonType.values())
            typeBox.getItems().add(new Label(type.name()));

        for (DragonCharacter character: DragonCharacter.values())
            characterBox.getItems().add(new Label(character.name()));

        initValidators();
    }


    @FXML
    private void cancelOperation(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


    public boolean validationGetsError() {
        return !(keyTextField.validate() && nameTextField.validate()
                && coordinateX.validate() && ageTextField.validate()
                && coordinateY.validate() && colorBox.validate()
                && typeBox.validate() && characterBox.validate() && headTextField.validate());
    }


    @FXML
    public void addDragon(ActionEvent actionEvent) {
        if (validationGetsError()) {
            return;
        }

        try {
            DragonHead head = new DragonHead(
                    (headTextField.getText().equals("null") || headTextField.getText().length() == 0)
                            ? null
                            : (Double.parseDouble(headTextField.getText())));

            Dragon dragon = new Dragon(
                    editingID,
                    Integer.parseInt(keyTextField.getText()),
                    nameTextField.getText(),
                    new Coordinates(Long.parseLong(coordinateX.getText()), Float.parseFloat(coordinateY.getText())),
                    Long.parseLong(ageTextField.getText()),
                    Color.valueOf(colorBox.getSelectionModel().getSelectedItem().getText()),
                    DragonType.valueOf(typeBox.getSelectionModel().getSelectedItem().getText()),
                    DragonCharacter.valueOf(characterBox.getSelectionModel().getSelectedItem().getText()),
                    head);

            if (editMode) {
                sendRequest(dragon, dragon.getId().toString(), "update");
                editMode = false;
            } else
                sendRequest(dragon, keyTextField.getText(), "insert");
        } catch (NumberFormatException ex) {
            AlertMaker.showErrorMessage(bundle.getString("tab.main.alert.validation.error.title"), bundle.getString("tab.main.alert.validation.error.content"));
        }
    }


    public void sendRequest(Dragon input, String arg, String commandKey) {
        AbsCommand command = clientContext.commandManager().getCommand(commandKey);
        command.setArgs(new String[]{arg});
        command.addInput(input);

        clientContext.clientChannel().sendCommand(new CommandPacket(command, clientContext.responseHandler().getCurrentUser().getCredentials(), bundle.getLocale()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof String) {
            AlertMaker.showSimpleAlert(bundle.getString("dashboard.alert.request.result"), (String)response);
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


    public void inflateUI(DragonEntrySerializable dragon) {
        if (dragon.getDragon() == null) {
            keyTextField.setText(String.valueOf(dragon.getKey()));
            keyTextField.setEditable(false);
            return;
        }
        String headText = (dragon.getDragon().getHead().getEyesCount() != null)
                ? dragon.getDragon().getHead().getEyesCount().toString()
                : "null";
        idLabel.setText("ID= " + dragon.getDragon().getId().toString());
        editingID = dragon.getDragon().getId();
        keyTextField.setText(String.valueOf(dragon.getKey()));
        nameTextField.setText(dragon.getDragon().getName());
        coordinateX.setText(dragon.getDragon().getCoordinates().getX().toString());
        coordinateY.setText(dragon.getDragon().getCoordinates().getY().toString());
        ageTextField.setText(dragon.getDragon().getAge().toString());
        headTextField.setText(headText);
        autoSelectComboBoxValue(colorBox, dragon.getDragon().getColor(), (color, colorBoxVal) -> color.equals(Enum.valueOf(Color.class, colorBoxVal)));
        autoSelectComboBoxValue(typeBox, dragon.getDragon().getType(), (type, typeBoxVal) -> type.equals(Enum.valueOf(DragonType.class, typeBoxVal)));
        autoSelectComboBoxValue(characterBox, dragon.getDragon().getCharacter(), (character, characterBoxVal) -> character.equals(Enum.valueOf(DragonCharacter.class, characterBoxVal)));
        keyTextField.setEditable(false);
    }

    public <T> void autoSelectComboBoxValue(JFXComboBox<Label> comboBox, T value, Func<T, String> f) {
        for (Label t : comboBox.getItems())
            if (f.compare(value, t.getText()))
                comboBox.setValue(t);
    }


    public void initValidators() {
        NumberValidator numValidator = new NumberValidator(bundle.getString("form.add.validation.format.msg"));
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator(bundle.getString("form.add.validation.required.msg"));
        RegexValidator ageValidator = new RegexValidator(bundle.getString("form.add.validation.age.msg"));
        ageValidator.setRegexPattern("^[1-9]\\d*$");
        RegexValidator coordXValidator = new RegexValidator(bundle.getString("form.add.validation.coord.x.msg"));
        coordXValidator.setRegexPattern("^-(32[0-7]|3[0-1]\\d|[1-2]\\d\\d|\\d{1,2})|\\d+$");

        keyTextField.getValidators().addAll(numValidator, requiredValidator);
        nameTextField.getValidators().addAll(requiredValidator);
        coordinateX.getValidators().addAll(numValidator, requiredValidator, coordXValidator);
        coordinateY.getValidators().addAll(numValidator, requiredValidator);
        ageTextField.getValidators().addAll(numValidator, requiredValidator, ageValidator);
        colorBox.getValidators().addAll(requiredValidator);
        typeBox.getValidators().addAll(requiredValidator);
        characterBox.getValidators().addAll(requiredValidator);
        headTextField.getValidators().addAll(numValidator);
    }

}
