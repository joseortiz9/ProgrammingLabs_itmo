package ru.students.lab.models;

import java.util.Random;
 /** 
 * Класс для хранения доступных значений поля DragonType класса Dragon и работы с ним
 * @autor Хосе Ортис
 * @version 1.0
*/
public enum DragonType {
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    @Override
    public String toString() {
        return this.name();
    }

    public static DragonType getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}
