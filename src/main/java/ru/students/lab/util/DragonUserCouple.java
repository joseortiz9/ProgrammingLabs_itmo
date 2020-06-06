package ru.students.lab.util;

import ru.students.lab.models.Dragon;

import java.io.Serializable;

public class DragonUserCouple implements Serializable {
    private static final long serialVersionUID = -4695129051537107979L;
    private final int userID;
    private final Dragon dragon;
    public DragonUserCouple(int userID, Dragon dragon) {
        this.userID = userID;
        this.dragon = dragon;
    }
    public Dragon getDragon() {
        return dragon;
    }
    public int getUserID() {
        return userID;
    }
}
