package ru.students.lab.managers;

import ru.students.lab.models.Dragon;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {

    private Integer nextIDToAdd = 1;
    private HashMap<Integer, Dragon> collection;
    private Date collectionCreationDate;

    public CollectionManager() {
        this.collection = new HashMap<>();
        this.collectionCreationDate = new Date();
    }

    public CollectionManager(HashMap<Integer, Dragon> collection) {
        this.collection = collection;
        this.collectionCreationDate = new Date();
        this.nextIDToAdd = collection.size() + 1;
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
        newDragon.setId(nextIDToAdd);
        nextIDToAdd += 1;
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

        newDragon.setId(nextIDToAdd);
        nextIDToAdd += 1;

        return oldDragonKey.map(integerDragonEntry ->
                this.getCollection().replace(integerDragonEntry.getKey(), newDragon)).orElse(null);
    }

    public Object removeKey(Integer key)
    {
        return this.getCollection().remove(key);
    }


    public Object replaceIfLower(Integer key, Dragon newDragon)
    {
        if (!this.getCollection().containsKey(key))
            return null;

        //is newer
        if (newDragon.compareTo(this.getCollection().get(key)) > 0) {
            newDragon.setId(nextIDToAdd);
            nextIDToAdd += 1;
            return this.getCollection().replace(key, newDragon);
        }
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
