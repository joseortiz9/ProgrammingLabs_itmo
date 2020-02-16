package ru.students.lab.models;

import java.util.Random;

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
