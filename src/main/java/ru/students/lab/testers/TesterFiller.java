package ru.students.lab.testers;

import ru.students.lab.managers.FileManager;
import ru.students.lab.models.*;

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

    public void collectionToXML(String pathToData) {
        new FileManager(pathToData).SaveCollectionInXML(this.getCollection());
    }

    public void printCollection() {
        this.getCollection().forEach((key, value) -> System.out.println(key + " " + value));
    }

    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }
}
