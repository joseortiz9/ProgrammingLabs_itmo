package ru.students.lab.clientUI.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableDragonPictureCanvas extends AbsResizableCanvas {

    public ResizableDragonPictureCanvas() {
        super();
    }

    @Override
    public void draw() {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);
    }

    @Override
    public Object findObj(double coordX, double coordY) {
        return null;
    }
}
