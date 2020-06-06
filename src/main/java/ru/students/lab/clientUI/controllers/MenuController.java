package ru.students.lab.clientUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import ru.students.lab.clientUI.ClientContext;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private ClientContext clientContext;

    public void init(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleMenuLogOut(ActionEvent actionEvent) {

    }

    public void handleMenuAddDragon(ActionEvent actionEvent) {

    }

    public void changeLanguageToEnglish(ActionEvent actionEvent) {

    }

    public void changeLanguageToRussian(ActionEvent actionEvent) {

    }

    public void changeLanguageToSerbian(ActionEvent actionEvent) {

    }

    public void changeLanguageToCroatian(ActionEvent actionEvent) {

    }

    public void changeLanguageToSpanish(ActionEvent actionEvent) {

    }

    public void handleAboutMenu(ActionEvent actionEvent) {

    }



}
