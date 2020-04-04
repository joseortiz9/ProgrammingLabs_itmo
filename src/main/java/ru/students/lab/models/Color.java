package ru.students.lab.models;

import java.io.Serializable;
import java.util.Random;
 /** 
 * Класс для хранения доступных значений поля color класса Dragon и работы с ним
 * @autor Хосе Ортис
 * @version 1.0
*/

public enum Color {
    RED,
    BLUE,
    YELLOW,
    ORANGE,
    WHITE;

    @Override
    public String toString() {
        return this.name();
    }

    public static Color getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}
