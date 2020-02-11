package ru.students.lab;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Random;

public class TesterFiller {

    private HashMap<Integer, Dragon> collection;

    public TesterFiller() {
        this.collection = new HashMap<Integer, Dragon>();
        this.fillCollection();
    }

    public void fillCollection() {
        for (int i = 0; i < 20; i++) {
            Dragon dragon = new Dragon(
                    i,
                    "Dragon" + i,
                    new Coordinates((long) i, (float) i),
                    ZonedDateTime.now(),
                    (long) new Random().nextInt(10),
                    Color.getRand(),
                    DragonType.getRand(),
                    DragonCharacter.getRand(),
                    new DragonHead((double) i));
            this.getCollection().put(dragon.hashCode(), dragon);
        }
    }

    public void collectionToXML() {
        new FileManager("/home/joseortiz09/Documents/ProgrammingProjects/IdeaProjects/Lab5/src/main/java/data.xml").SaveCollectionInXML(this.getCollection());
    }

    public void printCollection() {
        this.getCollection().forEach((key, value) -> System.out.println(key + " " + value));
    }

    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }
}
