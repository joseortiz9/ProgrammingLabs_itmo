package ru.students.lab.clientUI.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.database.Credentials;
import ru.students.lab.network.CommandPacket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginRegisterController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(LoginRegisterController.class);

    @FXML public JFXTextField username;
    @FXML public JFXPasswordField password;
    @FXML public Label introTitle;

    private ClientContext clientContext;

    public LoginRegisterController(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleRegisterButtonAction(ActionEvent actionEvent) {

    }

    @FXML
    public void handleLoginButtonAction(ActionEvent actionEvent) {
        String usernameText = username.getText();
        String passwordText = password.getText();
        AbsCommand loginCommand = clientContext.commandManager().getCommand("login");
        loginCommand.addInput(new Credentials(-1, usernameText, passwordText));

        clientContext.clientChannel().sendCommand(new CommandPacket(loginCommand, clientContext.responseHandler().getCurrentUser().getCredentials()));

        Object response = clientContext.responseHandler().checkForResponse();

        if (response instanceof Credentials) {
            closeStage();
            clientContext.responseHandler().setCurrentUser((Credentials) response);
            LOG.info("User successfully logged in {}", clientContext.responseHandler().getCurrentUser().getCredentials().username);
            clientContext.responseHandler().setReceivedObjectToNull();
            loadMain();
        } else {
            setWrongCredentialsStyle();
            AlertMaker.showErrorMessage("Not possible to Log In/Register", (String)response);
        }
    }

    public void setWrongCredentialsStyle() {
        username.getStyleClass().add("wrong-credentials");
        password.getStyleClass().add("wrong-credentials");
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            loader.setController(new MainController(clientContext));
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Dragons Dashboard");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOG.error("Error loading the main dashboard", ex);
        }
    }
}
