package ru.students.lab.util;

import javafx.scene.paint.Color;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FxUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM HH:mm z");

    public static String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }
    private static String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public static String formatZonedDateTimeValue(ZonedDateTime time) {
        return dateFormatter.format(time);
    }
}
