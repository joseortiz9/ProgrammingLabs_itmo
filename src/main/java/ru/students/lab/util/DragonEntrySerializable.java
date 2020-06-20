package ru.students.lab.util;

import ru.students.lab.models.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for sending back the entries of the map serialized
 * */
public class DragonEntrySerializable implements Serializable {
    private static final long serialVersionUID = -770990256082901507L;
    private final int key;
    private final Dragon dragon;
    public DragonEntrySerializable(int key, Dragon dragon) {
        this.key = key;
        this.dragon = dragon;
    }
    public Dragon getDragon() {
        return dragon;
    }

    public int getKey() {
        return key;
    }

    /*TO USE IN THE TABLEVIEW */
    public Integer getId() {
        return dragon.getId();
    }
    public String getName() {
        return dragon.getName();
    }
    public Coordinates getCoordinates() {
        return dragon.getCoordinates();
    }
    public ZonedDateTime getCreationDate() {
        return dragon.getCreationDate();
    }
    public Long getAge() {
        return dragon.getAge();
    }
    public Color getColor() {
        return dragon.getColor();
    }
    public DragonHead getHead() {
        return dragon.getHead();
    }
    public DragonType getType() {
        return dragon.getType();
    }
    public DragonCharacter getCharacter() {
        return dragon.getCharacter();
    }
    public Integer getUserID() {
        return dragon.getUserID();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DragonEntrySerializable)) return false;
        if (obj == this) return true;
        DragonEntrySerializable objDragon = (DragonEntrySerializable) obj;
        return this.getKey() == objDragon.getKey() && this.getDragon().equals(objDragon.getDragon());
    }
}
