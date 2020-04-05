package ru.students.lab.util;

import ru.students.lab.models.Dragon;

import java.io.Serializable;

public class ListEntrySerializable implements Serializable {
    private static final long serialVersionUID = -770990256082901507L;
    private int key;
    private Dragon dragon;
    public ListEntrySerializable(int key, Dragon dragon) {
        this.key = key;
        this.dragon = dragon;
    }
    public Dragon getDragon() {
        return dragon;
    }

    public int getKey() {
        return key;
    }
}
