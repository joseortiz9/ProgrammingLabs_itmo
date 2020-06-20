package ru.students.lab.clientUI.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.AlertMaker;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.canvas.AbsResizableCanvas;
import ru.students.lab.clientUI.canvas.ResizableDragonPictureCanvas;
import ru.students.lab.clientUI.canvas.ResizableMapCanvas;
import ru.students.lab.clientUI.controllers.MainController;
import ru.students.lab.clientUI.controllers.forms.AddDragonController;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.models.Dragon;
import ru.students.lab.models.DragonType;
import ru.students.lab.network.CommandPacket;
import ru.students.lab.util.DragonEntrySerializable;
import ru.students.lab.util.FxUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MapTabController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainTabController.class);

    @FXML public Pane wrapperMapPane, dragonPicturePane;
    @FXML public JFXButton editDragonButton, removeDragonButton;
    @FXML public GridPane dragonDetailsGrid;
    private AbsResizableCanvas dragonsMapCanvas;
    private AbsResizableCanvas dragonPictureCanvas;
    private ResourceBundle bundle;

    private final MainController mainController;
    private final ArrayList<DragonEntrySerializable> dragonsList = new ArrayList<>();
    private DragonEntrySerializable selectedDragon = null;

    public MapTabController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        // Map canvas init
        dragonsMapCanvas = new ResizableMapCanvas(dragonsList, wrapperMapPane);
        wrapperMapPane.getChildren().add(dragonsMapCanvas);
        dragonsMapCanvas.widthProperty().bind(wrapperMapPane.widthProperty());
        dragonsMapCanvas.heightProperty().bind(wrapperMapPane.heightProperty());
        dragonsMapCanvas.setOnMouseClicked(event -> {
            selectedDragon = (DragonEntrySerializable)dragonsMapCanvas.findObj(event.getSceneX(), event.getSceneY());
            handleDetailDragon();
        });

        // dragon canvas picture init
        dragonPictureCanvas = new ResizableDragonPictureCanvas();
        dragonPicturePane.getChildren().add(dragonPictureCanvas);
        dragonPictureCanvas.widthProperty().bind(dragonPicturePane.widthProperty());
        dragonPictureCanvas.heightProperty().bind(dragonPicturePane.heightProperty());

        refreshData();
    }

    public void refreshData() {
        if (needAddDragon() && dragonsList.size() > 0)
            checkForNewDragons();
        else {
            dragonsList.clear();
            dragonsList.addAll(mainController.getContext().localCollection().getLocalList());
            dragonsMapCanvas.setObj(mainController.getContext().localCollection().getLocalList());
            dragonsMapCanvas.draw();
        }
        if (selectedDragon != null)
            updateDragonDetails();
    }

    public boolean needAddDragon() {
        return dragonsList.size() < mainController.getContext().localCollection().getLocalList().size();
    }

    public void checkForNewDragons() {
        List<DragonEntrySerializable> diff = new ArrayList<>();
        outer: for (DragonEntrySerializable fetched : mainController.getContext().localCollection().getLocalList()) {
            for (DragonEntrySerializable elemMap : dragonsList)
                if (fetched.equals(elemMap))
                    continue outer;
            diff.add(fetched);
        }

        for (DragonEntrySerializable newElem : diff) {
            System.out.println(newElem.toString());
            dragonsList.add(newElem);
            ((List<DragonEntrySerializable>) dragonsMapCanvas.getObj()).add(newElem);
            dragonsMapCanvas.animateEntry(newElem);
        }
    }

    public void updateDragonDetails() {
        int oldSelectedDragonID = selectedDragon.getDragon().getId();
        selectedDragon = mainController.getContext().localCollection().getByID(oldSelectedDragonID);
        if (selectedDragon != null)
            handleDetailDragon();
        else {
            dragonDetailsGrid.getChildren().clear();
            dragonPictureCanvas.setObj(null);
            dragonPictureCanvas.draw();
        }
    }

    public void handleDetailDragon() {
        if (selectedDragon == null)
            return;

        changeStatusActionButtons(selectedDragon.getDragon().getUserID());

        try {
            loadingDragonPicture();
            loadingDragonFields();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            AlertMaker.showErrorMessage(bundle.getString("tab.map.error.fetching"), ex.getMessage());
        }
    }

    private void changeStatusActionButtons(int userID) {
        int currentUserID = mainController.getContext().responseHandler().getCurrentUser().getCredentials().id;
        if (currentUserID != 1 && currentUserID != userID) {
            editDragonButton.setDisable(true);
            removeDragonButton.setDisable(true);
        } else {
            editDragonButton.setDisable(false);
            removeDragonButton.setDisable(false);
        }
    }

    private void loadingDragonPicture() {
        dragonPictureCanvas.setObj(selectedDragon.getDragon());
        dragonPictureCanvas.draw();

        //TODO: OPTIMIZE BACKGROUND PAINTING
        DragonType type = selectedDragon.getType();
        if (type.equals(DragonType.UNDERGROUND)) {
            dragonPicturePane.setStyle("-fx-background-color: " + FxUtils.toHexString(Color.PERU));
        }
        else if (type.equals(DragonType.WATER)) {
            dragonPicturePane.setStyle("-fx-background-color: " + FxUtils.toHexString(Color.AQUA));
        }
        else if (type.equals(DragonType.AIR)){
            dragonPicturePane.setStyle("-fx-background-color: " + FxUtils.toHexString(Color.LIGHTGRAY));
        }
        else if (type.equals(DragonType.FIRE)) {
            dragonPicturePane.setStyle("-fx-background-color: " + FxUtils.toHexString(Color.MAROON));
        }

        dragonPictureCanvas.animateEntry(selectedDragon);
    }

    private void loadingDragonFields() throws IllegalAccessException {
        dragonDetailsGrid.getChildren().clear();
        int x = 0, y = 0;
        for (Field dragonField: selectedDragon.getDragon().getClass().getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(dragonField.getModifiers()))
                continue;

            dragonField.setAccessible(true);
            String title = bundle.getString("tab.main.table.col." + dragonField.getName());
            String content = dragonField.get(selectedDragon.getDragon()).toString();
            if (dragonField.getName().equalsIgnoreCase("creationDate"))
                content = FxUtils.formatZonedDateTimeValue((ZonedDateTime) dragonField.get(selectedDragon.getDragon()));
            final Label attrTemp = new Label(title + ": " + content);
            attrTemp.getStyleClass().add("detail-dragon");
            if (x == 2) {
                y++;
                x = 0;
            }
            dragonDetailsGrid.add(attrTemp, x, y);  //x is column index and 0 is row index
            x++;
        }
    }

    @FXML
    public void handleEditDragonButtonAction(ActionEvent actionEvent) {
        mainController.loadEditDragonDialog(selectedDragon, true, false);
    }

    @FXML
    public void handleRemoveDragonButtonAction(ActionEvent actionEvent) {
        mainController.loadRemoveDragonDialog(selectedDragon);
    }
}

