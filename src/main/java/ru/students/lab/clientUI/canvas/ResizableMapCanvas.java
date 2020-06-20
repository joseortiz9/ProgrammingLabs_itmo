package ru.students.lab.clientUI.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.students.lab.util.DragonEntrySerializable;

import java.util.ArrayList;
import java.util.Comparator;
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
        gc.fillText(String.valueOf((int)(-scale*2/2.2 / 4)), min / 4, min / 2 + 20);
        gc.fillText(String.valueOf((int)(scale*2/2.2 / 4)), min * 3.0 / 4.0, min / 2 + 20);

        // Draw dragons
        dragonsList.stream().sorted(Comparator.comparingInt(o -> o.getDragon().getUserID()))
                .forEach(d -> drawDragons(gc, d, min));
    }

    private void drawDragons(GraphicsContext gc, DragonEntrySerializable dragon, double width) {
        if (actualUserID == dragon.getDragon().getUserID()) {
            drawDragon(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), gc,  dragon, width);
        }
        else {
            drawDragon(((dragon.getDragon().getCoordinates().getX() + scale / 2.0) * (width / scale)),
                    ((scale / 2.0 - dragon.getDragon().getCoordinates().getY()) * (width / scale)), gc,  dragon, width);
        }
        lastID = dragon.getDragon().getUserID();
    }

    public void drawDragon(double x, double y, GraphicsContext gc, DragonEntrySerializable dragon, double width) {
        double size = setSize(dragon, width);
        x = x - size*120/2D;
        y = y - size*120/2D;
        gc.setFill(Color.valueOf(dragon.getColor().toString()));
        drawShape(gc, dragon, size, x, y);
        drawEyes(gc, dragon, size, x, y);
        drawCharacter(gc, dragon, size, x, y);
    }
    private Double setSize(DragonEntrySerializable dragon, double width) {
        if (dragon.getAge()<50) return 0.05D*width/400;
        if (dragon.getAge() > 1000){
            return 1D*width/400;
        }
        return dragon.getAge()*width/400000D;
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
}
