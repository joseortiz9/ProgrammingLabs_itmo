package ru.students.lab.clientUI;

import ru.students.lab.models.Dragon;
import ru.students.lab.util.DragonEntrySerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocalCollectionManager {
    private final List<DragonEntrySerializable> localList;

    public LocalCollectionManager() {
        localList = new ArrayList<>();
    }

    public LocalCollectionManager(List<DragonEntrySerializable> list) {
        localList = list;
    }

    public DragonEntrySerializable getByID(int id) {
        return  localList.stream()
                .filter(dragonEntry -> dragonEntry.getDragon().getId() == id)
                .findAny()
                .orElse(null);
    }

    public DragonEntrySerializable getByKey(int key) {
        return  localList.stream()
                .filter(dragonEntry -> dragonEntry.getKey() == key)
                .findAny()
                .orElse(null);
    }

    public List<DragonEntrySerializable> getLocalList() {
        return localList;
    }


    public long getSumNumFields() {
        return localList.stream()
                .mapToLong(e ->
                        (long) (e.getCoordinates().getX()
                                + e.getCoordinates().getY()
                                + e.getAge()))
                .sum();
    }

    public long getSumNames() {
        return localList.stream()
                .mapToLong(e ->
                        (long) (e.getDragon().getName().length())
                                + e.getDragon().getColor().ordinal()
                                + e.getDragon().getType().ordinal()
                                + e.getDragon().getCharacter().ordinal())
                .sum() << 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalCollectionManager that = (LocalCollectionManager) o;
        return this.getSumNumFields() == that.getSumNumFields() &&
                this.getSumNames() == that.getSumNames();
    }

    @Override
    public int hashCode() {
        return Objects.hash(localList);
    }
}
