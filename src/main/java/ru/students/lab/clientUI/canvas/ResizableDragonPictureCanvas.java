package ru.students.lab.clientUI.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.students.lab.models.Dragon;

public class ResizableDragonPictureCanvas extends AbsResizableCanvas {

    private Dragon dragon = null;

    public ResizableDragonPictureCanvas() {
        super();
    }

    @Override
    public Object findObj(double coordX, double coordY) {
        return null;
    }

    @Override
    public void setObj(Object obj) {
        dragon = (Dragon) obj;
    }

    @Override
    public void draw() {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
        if (dragon != null)
            drawDragon(gc);
        else {
            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);
        }
    }

    private void drawDragon(GraphicsContext gc) {
        gc.setFill(Color.valueOf(dragon.getColor().toString()));
        drawShape(gc);
        drawEyes(gc);
        drawCharacter(gc);
    }

    private void drawEyes(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        double numEyesToDraw = (dragon.getHead().getEyesCount() == null)
                ? 0D
                : ((dragon.getHead().getEyesCount() > 200)
                    ? 200D
                    : dragon.getHead().getEyesCount());
        for (int i = 0; i < numEyesToDraw; i++) {
            int x = (int)(40+Math.random()*30);
            int y = (int)(40+Math.random()*20);
            gc.fillOval(x,y,6,6);
            gc.strokeOval(x,y,6,6);
            gc.strokeOval(x+3, y+3, 1,1);
        }
    }

    private void drawCharacter(GraphicsContext gc) {
        String character = dragon.getCharacter().toString();
        switch (character) {
            case "WISE":
                gc.strokePolyline(new double[]{50, 65}, new double[]{74, 74}, 2);
                break;
            case "CHAOTIC":
                gc.strokePolyline(new double[]{50, 55, 60, 65}, new double[]{74, 70, 74, 70}, 4);
                break;
            case "GOOD":
                gc.strokePolyline(new double[]{50, 57, 65}, new double[]{72, 78, 72}, 3);
                break;
            case "EVIL":
                gc.strokePolyline(new double[]{50, 57, 65}, new double[]{78, 72, 78}, 3);
                break;
            case "CUNNING":
                gc.strokePolyline(new double[]{50, 57, 65, 69}, new double[]{72, 74, 68, 67}, 4);
                break;
        }
    }

    private void drawShape(GraphicsContext gc) {
        gc.strokePolygon(new double[]{70, 80, 110, 100, 115, 90, 105, 70}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
        gc.strokePolygon(new double[]{45, 35, 5, 15, 0, 25, 10, 45}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
        gc.strokePolygon(new double[]{45, 35, 5, 15, 0, 25, 10, 45}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
        gc.strokePolygon(new double[]{80, 80, 110, 140, 125}, new double[]{125, 115, 105, 80, 110}, 5);
        gc.strokePolygon(new double[]{40, 56, 48}, new double[]{48, 48, 33}, 3);
        gc.strokePolygon(new double[]{60, 76, 68}, new double[]{48, 48, 33}, 3);
        gc.strokeOval(32, 70, 50, 70);
        gc.strokeOval(32, 110, 20, 40);
        gc.strokeOval(65, 110, 20, 40);
        gc.strokeOval(32, 40, 50, 40);

        gc.fillOval(32, 40, 50, 40);
        gc.fillPolygon(new double[]{40, 56, 48}, new double[]{48, 48, 33}, 3);
        gc.fillPolygon(new double[]{60, 76, 68}, new double[]{48, 48, 33}, 3);
        gc.fillPolygon(new double[]{80, 80, 110, 140, 125}, new double[]{125, 115, 105, 80, 110}, 5);
        gc.fillOval(32, 70, 50, 70);
        gc.fillOval(32, 110, 20, 40);
        gc.fillOval(65, 110, 20, 40);
        gc.fillPolygon(new double[]{70, 80, 110, 100, 115, 90, 105, 70}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
        gc.fillPolygon(new double[]{45, 35, 5, 15, 0, 25, 10, 45}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
        gc.fillPolygon(new double[]{45, 35, 5, 15, 0, 25, 10, 45}, new double[]{110, 70, 65, 75, 80, 88, 98, 110}, 8);
    }
}
