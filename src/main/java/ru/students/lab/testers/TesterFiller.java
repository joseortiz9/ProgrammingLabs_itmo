package ru.students.lab.testers;

import ru.students.lab.managers.FileManager;
import ru.students.lab.models.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
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
        for (int i = 0; i < 50; i++) {
            Dragon dragon = new Dragon(
                    "Dragon" + i,
                    new Coordinates((long) i, (float) i),
                    (long) new Random().nextInt(10),
                    Color.getRand(),
                    DragonType.getRand(),
                    DragonCharacter.getRand(),
                    new DragonHead((double) i));
            dragon.setId(i);
            this.getCollection().put(dragon.hashCode(), dragon);
        }
    }

    public void collectionToXML(String pathToData) throws IOException, JAXBException {
        new FileManager(pathToData).SaveCollectionInXML(this.getCollection());
    }

    public void printCollection() {
        this.getCollection().forEach((key, value) -> System.out.println(key + " " + value));
    }

    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }
}
