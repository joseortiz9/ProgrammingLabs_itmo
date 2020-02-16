package ru.students.lab.managers;

import org.jetbrains.annotations.NotNull;
import ru.students.lab.models.Dragon;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {

    private HashMap<Integer, Dragon> collection;
    private Date collectionCreationDate;

    public CollectionManager(HashMap<Integer, Dragon> collection) {
        this.collection = collection;
        this.collectionCreationDate = new Date();
    }

    public void clear() {
        this.getCollection().clear();
    }

    public List<Map.Entry<Integer, Dragon>> sortByKey()
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }
    public List<Map.Entry<Integer, Dragon>> sortById()
    {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(x -> x.getValue().getId()))
                .collect(Collectors.toList());
    }
    public List<Map.Entry<Integer, Dragon>> sortByName() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted((x, y) -> x.getValue().getName().compareTo(y.getValue().getName()))
                .collect(Collectors.toList());
    }
    public List<Map.Entry<Integer, Dragon>> sortByCreationDate() {

        return this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }

    public Object insert(Integer key, Dragon newDragon) {
        return this.getCollection().putIfAbsent(key, newDragon);
    }

    public Object update(Integer id, Dragon newDragon)
    {
        Optional<Map.Entry<Integer, Dragon>> oldDragonKey =
                this.getCollection()
                .entrySet()
                .stream()
                .filter(dragonEntry -> dragonEntry.getValue().getId().equals(id))
                .findFirst();

        return oldDragonKey.map(integerDragonEntry ->
                this.getCollection().replace(integerDragonEntry.getKey(), newDragon)).orElse(null);
    }

    public Object removeKey(Integer key)
    {
        return this.getCollection().remove(key);
    }


    public Object replaceIfLower(Integer key, Dragon dragon)
    {
        if (!this.getCollection().containsKey(key))
            return null;

        //is newer
        if (dragon.compareTo(this.getCollection().get(key)) > 0)
            return this.getCollection().replace(key, dragon);
        return null;
    }

    public void removeGreaterKey(Integer key)
    {
        this.getCollection()
                .entrySet()
                .removeIf(dragonEntry -> dragonEntry.getKey() > key);
    }

    public void removeLowerKey(Integer key)
    {
        this.getCollection()
                .entrySet()
                .removeIf(dragonEntry -> key > dragonEntry.getKey());
    }


    public List<Map.Entry<Integer, Dragon>> filterContainsName(String name)
    {
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Map.Entry<Integer, Dragon>> filterStartsWithName(String name)
    {
        String regex = "^("+name+").*$";
        return this.getCollection()
                .entrySet()
                .stream()
                .filter(dragon -> dragon.getValue().getName().matches(regex))
                .collect(Collectors.toList());
    }


    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }
    public Date getColCreationDate() {
        return collectionCreationDate;
    }

    @Override
    public int hashCode() {
        int result = 25;
        result += this.getColCreationDate().hashCode();
        result >>= 4;
        result += (this.getCollection().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CollectionManager)) return false;
        if (obj == this) return true;
        CollectionManager objCManager = (CollectionManager) obj;
        return this.getCollection().equals(objCManager.getCollection()) &&
                this.getColCreationDate().equals(objCManager.getColCreationDate());
    }

    @Override
    public String toString() {
        return "Type of Collection: " + this.getCollection().getClass() +
                "\nCreation Date: " + collectionCreationDate.toString() +
                "\nAmount of elements: " + this.getCollection().size();
    }

}
