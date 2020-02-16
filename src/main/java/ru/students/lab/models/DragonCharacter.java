package ru.students.lab.models;

import java.util.Random;

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
