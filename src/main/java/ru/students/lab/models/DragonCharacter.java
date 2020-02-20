package ru.students.lab.models;

import java.util.Random;
 /** 
 * Класс для хранения доступных значений поля DragonCharacter класса Dragon и работы с ним
 * @autor Хосе Ортис
 * @version 1.0
*/
public enum DragonCharacter {
    CUNNING,
    WISE,
    EVIL,
    GOOD,
    CHAOTIC;

    @Override
    public String toString() {
        return this.name();
    }

    public static DragonCharacter getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}
