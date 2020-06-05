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
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginRegisterController implements Initializable {

    @FXML
    public JFXTextField username;
    @FXML
    public JFXPasswordField password;
    @FXML
    public Label intro_title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleRegisterButtonAction(ActionEvent actionEvent) {

    }

    public void handleLoginButtonAction(ActionEvent actionEvent) {
        if (checkCredentials()) {
            closeStage();
            loadMain();
            //LOGGER.log(Level.INFO, "User successfully logged in {}", uname);
        }
        else {
            setWrongCredentialsStyle();
        }
    }

    public void setWrongCredentialsStyle() {
        username.getStyleClass().add("wrong-credentials");
        password.getStyleClass().add("wrong-credentials");
    }

    private boolean checkCredentials() {
        String uname = username.getText();
        String pword = password.getText();
        return uname.equals("root") && pword.equals("root");
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Dragons Dashboard");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            //LOGGER.log(Level.ERROR, "{}", ex);
        }
    }
}
