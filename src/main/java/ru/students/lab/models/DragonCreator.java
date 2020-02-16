package ru.students.lab.models;

import java.time.ZonedDateTime;
import java.util.Random;

public class DragonCreator {
    public DragonCreator() {

    }

    public Dragon generateDragon() {
        return new Dragon(
                1000,
                "Dragon" + 1000,
                new Coordinates((long) 132, (float) 321),
                ZonedDateTime.now(),
                (long) new Random().nextInt(10),
                Color.getRand(),
                DragonType.getRand(),
                DragonCharacter.getRand(),
                new DragonHead((double) 5));
    }
}
