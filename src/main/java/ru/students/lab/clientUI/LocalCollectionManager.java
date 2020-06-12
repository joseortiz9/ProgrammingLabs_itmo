package ru.students.lab.clientUI;

import ru.students.lab.util.DragonEntrySerializable;

import java.util.ArrayList;
import java.util.List;

public class LocalCollectionManager {
    private final List<DragonEntrySerializable> localList;

    public LocalCollectionManager() {
        localList = new ArrayList<>();
    }

    public LocalCollectionManager(List<DragonEntrySerializable> list) {
        localList = list;
    }

    public List<DragonEntrySerializable> getLocalList() {
        return localList;
    }
}
