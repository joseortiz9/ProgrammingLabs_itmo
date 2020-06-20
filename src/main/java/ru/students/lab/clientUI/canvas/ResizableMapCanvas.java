package ru.students.lab.clientUI.canvas;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ru.students.lab.util.DragonEntrySerializable;

import java.util.ArrayList;

public class ResizableMapCanvas extends AbsResizableCanvas {

    private static final int SCREEN_START_MARGIN_ERROR_X = 10;
    private static final int SCREEN_START_MARGIN_ERROR_Y = 90;

    public ArrayList<DragonEntrySerializable> dragonsList = new ArrayList<>();
    private double scale = 0;
    private GraphicsContext gc;
    private double min;
    private final Pane wrapperMapPane;

    public ResizableMapCanvas(ArrayList<DragonEntrySerializable> dragonsList, Pane wrapperMapPane) {
        super();
        this.dragonsList = dragonsList;
        this.wrapperMapPane = wrapperMapPane;
    }

    @Override
    public Object findObj(double coordX, double coordY) throws NullPointerException {
        double min = Math.min(getWidth(), getHeight());
        double finalCoordX = (coordX - SCREEN_START_MARGIN_ERROR_X) * (scale / min) - scale / 2.0;
        double finalCoordY = scale / 2.0 - (coordY - SCREEN_START_MARGIN_ERROR_Y) * (scale / min);

        return dragonsList.stream().filter(dragon ->
                Math.abs(dragon.getDragon().getCoordinates().getX() - finalCoordX) < scale * 0.018)
                .filter(dragon ->
                        Math.abs(dragon.getDragon().getCoordinates().getY() - finalCoordY) < scale * 0.018)
                .findAny().orElse(null);
    }

    @Override
    public void setObj(Object obj) {
        dragonsList = (ArrayList<DragonEntrySerializable>) obj;
    }

    @Override
    public Object getObj() {
        return dragonsList;
    }

    @Override
    public void draw() {
        double width = getWidth();
        double height = getHeight();
        min = Math.min(width, height);

        gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        // scale the map
        double maxx = dragonsList.stream().mapToDouble(d -> d.getDragon().getCoordinates().getX()).max().orElse(getWidth());
        double minx = dragonsList.stream().mapToDouble(d -> d.getDragon().getCoordinates().getX()).min().orElse(getHeight());
        double maxy = dragonsList.stream().mapToDouble(d -> d.getDragon().getCoordinates().getY()).max().orElse(0);
        double miny = dragonsList.stream().mapToDouble(d -> d.getDragon().getCoordinates().getY()).min().orElse(0);
        scale = 2 * Math.max(maxx, Math.max(-Math.min(minx, miny), maxy));

        //draw background
        gc.setFill(Color.DARKGREY);
        gc.fillRect(0, 0, width, height);

        //draw axis
        gc.setFill(Color.BLACK);
        gc.strokeLine(0, min / 2, min, min / 2);
        gc.strokeLine(min / 2, 0, min / 2, min);
        gc.fillText("0.0", min / 2, min / 2 + 20);
        gc.fillText(String.valueOf((int)(-scale*2/2.2 / 4)), min / 4, min / 2 + 20);
        gc.fillText(String.valueOf((int)(scale*2/2.2 / 4)), min * 3.0 / 4.0, min / 2 + 20);

        // Draw dragons
        dragonsList.forEach(this::drawDragons);
    }

    private void drawDragons(DragonEntrySerializable dragon) {
            drawDragon(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (min / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (min / scale)), gc,  dragon);
    }

    public void drawDragon(double x, double y, GraphicsContext gc, DragonEntrySerializable dragon) {
        double size = setSize(dragon);
        x = x - size*120/2D;
        y = y - size*120/2D;
        gc.setFill(Color.valueOf(dragon.getColor().toString()));
        drawShape(gc, dragon, size, x, y);
        drawEyes(gc, dragon, size, x, y);
        drawCharacter(gc, dragon, size, x, y);
    }

    private double setSize(DragonEntrySerializable dragon) {
        if (dragon.getAge()<50) return 0.05D*min/400;
        if (dragon.getAge() > 1000) {
            return 1D*min/400;
        }
        return dragon.getAge()*min/400000D;
    }

    private void drawEyes(GraphicsContext gc, DragonEntrySerializable dragon, double size, double x, double y) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        double numEyesToDraw = (dragon.getHead().getEyesCount() == null)
                ? 0D
                : ((dragon.getHead().getEyesCount() > 200)
                    ? 200D
                    : dragon.getHead().getEyesCount());
        for (int i = 0; i < numEyesToDraw; i++) {
            int xeyes = (int)(40+Math.random()*30);
            int yeyes = (int)(40+Math.random()*20);
            gc.fillOval(xeyes*size + x,yeyes*size + y,6*size,6*size);
            gc.strokeOval(xeyes*size + x,yeyes*size + y,6*size,6*size);
            gc.strokeOval((xeyes+3)*size + x, (yeyes+3)*size + y, 1*size,1*size);
        }
    }

    private void drawCharacter(GraphicsContext gc, DragonEntrySerializable dragon, double size, double x, double y) {
        String character = dragon.getCharacter().toString();
        switch (character) {
            case "WISE":
                gc.strokePolyline(new double[]{50*size + x, 65*size + x}, new double[]{74*size + y, 74*size + y}, 2);
                break;
            case "CHAOTIC":
                gc.strokePolyline(new double[]{50*size + x, 55*size + x, 60*size + x, 65*size + x}, new double[]{74*size + y, 70*size + y, 74*size + y, 70*size + y}, 4);
                break;
            case "GOOD":
                gc.strokePolyline(new double[]{50*size + x, 57*size + x, 65*size + x}, new double[]{72*size + y, 78*size + y, 72*size + y}, 3);
                break;
            case "EVIL":
                gc.strokePolyline(new double[]{50*size + x, 57*size + x, 65*size + x}, new double[]{78*size + y, 72*size + y, 78*size + y}, 3);
                break;
            case "CUNNING":
                gc.strokePolyline(new double[]{50*size + x, 57*size + x, 65*size + x, 69*size + x}, new double[]{72*size + y, 74*size + y, 68*size + y, 67*size + y}, 4);
                break;
        }
    }

    private void drawShape(GraphicsContext gc, DragonEntrySerializable dragon, double size, double x, double y) {
        gc.strokePolygon(new double[]{70 * size + x, 80 * size + x, 110 * size + x, 100 * size + x, 115 * size + x, 90 * size + x, 105 * size + x, 70 * size + x}, new double[]{110 * size + y, 70 * size + y, 65 * size + y, 75 * size + y, 80 * size + y, 88 * size + y, 98 * size + y, 110 * size + y}, 8);
        gc.strokePolygon(new double[]{45 * size + x, 35 * size + x, 5 * size + x, 15 * size + x, 0 * size + x, 25 * size + x, 10 * size + x, 45 * size + x}, new double[]{110 * size + y, 70 * size + y, 65 * size + y, 75 * size + y, 80 * size + y, 88 *size  + y, 98 * size + y, 110 * size + y}, 8);
        gc.strokePolygon(new double[]{80 * size + x, 80 * size + x, 110 * size + x, 140 * size + x, 125 * size + x}, new double[]{125 * size + y, 115 * size + y, 105 * size + y, 80 * size + y, 110 * size + y}, 5);
        gc.strokePolygon(new double[]{40 * size + x, 56 * size + x, 48 * size + x}, new double[]{48 * size + y, 48 * size + y, 33 * size + y}, 3);
        gc.strokePolygon(new double[]{60 * size + x, 76 * size + x, 68 * size + x}, new double[]{48 * size + y, 48 * size + y, 33 * size + y}, 3);
        gc.strokeOval(32 * size + x, 70 * size + y, 50 * size, 70 * size);
        gc.strokeOval(32 * size + x, 110 * size + y, 20 * size, 40 * size);
        gc.strokeOval(65 * size + x, 110 * size + y, 20 * size, 40 * size);
        gc.strokeOval(32 * size + x, 40 * size + y, 50 * size, 40 * size);

        gc.fillOval(32 * size + x, 40 * size + y, 50 * size, 40 * size);
        gc.fillPolygon(new double[]{40 * size + x, 56 * size + x, 48 * size + x}, new double[]{48 * size + y, 48 * size + y, 33 * size + y}, 3);
        gc.fillPolygon(new double[]{60 * size + x, 76 * size + x, 68 * size + x}, new double[]{48 * size + y, 48 * size + y, 33 * size + y}, 3);
        gc.fillPolygon(new double[]{80 * size + x, 80 * size + x, 110 * size + x, 140 * size + x, 125 * size + x}, new double[]{125 * size + y, 115 * size + y, 105 * size + y, 80 * size + y, 110 * size + y}, 5);
        gc.fillOval(32 * size + x, 70 * size + y, 50 * size, 70 * size);
        gc.fillOval(32 * size + x, 110 * size + y, 20 * size, 40 * size);
        gc.fillOval(65 * size + x, 110 * size + y, 20 * size, 40 * size);
        gc.fillPolygon(new double[]{70 * size + x, 80 * size + x, 110 * size + x, 100 * size + x, 115 * size + x, 90 * size + x, 105 * size + x, 70 * size + x}, new double[]{110 * size + y, 70 * size + y, 65 * size + y, 75 * size + y, 80 * size + y, 88 * size + y, 98 * size + y, 110 * size + y}, 8);
        gc.fillPolygon(new double[]{45 * size + x, 35 * size + x, 5 * size + x, 15 * size + x, 0 * size + x, 25 * size + x, 10 * size + x, 45 * size + x}, new double[]{110 * size + y, 70 * size + y, 65 * size + y, 75 * size + y, 80 * size + y, 88 * size + y, 98 * size + y, 110 * size + y}, 8);
    }

    @Override
    public void animateEntry(DragonEntrySerializable dragon) {
        double x = ((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (min / scale));
        double y = ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (min / scale));
        Circle circle = new Circle(x, y, setSize(dragon) * 120, Color.valueOf(dragon.getColor().toString()));
        wrapperMapPane.getChildren().add(circle);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), circle);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.play();

        fadeOut.setOnFinished(e -> {
            wrapperMapPane.getChildren().remove(circle);
            drawDragons(dragon);
        });
    }
}
