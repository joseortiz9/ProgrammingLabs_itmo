package ru.students.lab;

public class ConsoleCommander {

    CollectionManager collectionManager;

    ConsoleCommander(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public void printCollection() {
        this.getCollectionManager().getCollection().forEach((key, value) -> System.out.println(key + " " + value));
    }
}
