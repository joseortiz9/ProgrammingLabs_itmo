package ru.students.lab.clientUI;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertMaker {
    public static void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }

    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //stage.getIcons().add(new Image("media/dialog_icon.png"));
        //TODO: change image of alert

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getClassLoader().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }
}
