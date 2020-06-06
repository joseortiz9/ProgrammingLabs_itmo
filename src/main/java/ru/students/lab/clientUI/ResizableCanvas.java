package ru.students.lab.clientUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.students.lab.util.DragonUserCouple;

import java.util.ArrayList;

public class ResizableCanvas extends Canvas {

    private static final int SCREEN_START_MARGIN_ERROR_X = 10;
    private static final int SCREEN_START_MARGIN_ERROR_Y = 90;
    private ArrayList<DragonUserCouple> dragonsList = new ArrayList<>();
    private int lastID = -1;
    private int actualUserID = -1;
    private double scale = 0;

    public ResizableCanvas(ArrayList<DragonUserCouple> dragonsList, int actualUserID) {
        this.dragonsList = dragonsList;
        this.actualUserID = actualUserID;

        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        System.out.println(width+"  "+height);
        double min = Math.min(width, height);

        GraphicsContext gc = getGraphicsContext2D();
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
        gc.fillText(String.valueOf((int)(-scale / 4)), min / 4, min / 2 + 20);
        gc.fillText(String.valueOf((int)(scale / 4)), min * 3.0 / 4.0, min / 2 + 20);

        // Draw dragons
        dragonsList.stream().sorted((o1, o2) -> o1.getUserID() - o2.getUserID())
                .forEach(d -> drawDragons(gc, d, min));
    }

    private void drawDragons(GraphicsContext gc, DragonUserCouple dragon, double width) {
        if (lastID != dragon.getUserID()) {
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        }
        if (actualUserID == dragon.getUserID()) {
            gc.fillOval(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), 11, 11);
            gc.strokeOval(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), 11,  11);
            gc.strokeOval(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale) + 4.25),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale) +4.25 ), 2,  2);
        }
        else {
            gc.strokeOval(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), 8,  8);
            gc.fillOval(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), 8, 8);
        }
        lastID = dragon.getUserID();
    }

    public DragonUserCouple findDragon(double coordX, double coordY) throws NullPointerException {
        double min = Math.min(getWidth(), getHeight());
        double finalCoordX = coordX * (scale / (getWidth() - SCREEN_START_MARGIN_ERROR_X)) - scale / 2.0;
        double finalCoordY = scale / 2.0 - coordY * (scale / (getWidth() - SCREEN_START_MARGIN_ERROR_Y));

        return dragonsList.stream().filter(dragon ->
                Math.abs(dragon.getDragon().getCoordinates().getX() - finalCoordX) < scale * 0.03)
                .filter(dragon ->
                        Math.abs(dragon.getDragon().getCoordinates().getY() - finalCoordY) < scale * 0.03)
                .findAny().orElse(null);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
