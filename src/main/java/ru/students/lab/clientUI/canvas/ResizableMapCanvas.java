package ru.students.lab.clientUI.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.students.lab.util.DragonEntrySerializable;

import java.util.ArrayList;
import java.util.List;

public class ResizableMapCanvas extends AbsResizableCanvas {

    private static final int SCREEN_START_MARGIN_ERROR_X = 10;
    private static final int SCREEN_START_MARGIN_ERROR_Y = 90;

    private ArrayList<DragonEntrySerializable> dragonsList = new ArrayList<>();
    private int lastID = -1;
    private int actualUserID = -1;
    private double scale = 0;

    public ResizableMapCanvas(ArrayList<DragonEntrySerializable> dragonsList, int actualUserID) {
        super();
        this.dragonsList = dragonsList;
        this.actualUserID = actualUserID;
    }

    @Override
    public void draw() {
        double width = getWidth();
        double height = getHeight();
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
        dragonsList.stream().sorted((o1, o2) -> o1.getDragon().getUserID() - o2.getDragon().getUserID())
                .forEach(d -> drawDragons(gc, d, min));
    }

    private void drawDragons(GraphicsContext gc, DragonEntrySerializable dragon, double width) {
        if (lastID != dragon.getDragon().getUserID()) {
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        }
        if (actualUserID == dragon.getDragon().getUserID()) {
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
        lastID = dragon.getDragon().getUserID();
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
}
