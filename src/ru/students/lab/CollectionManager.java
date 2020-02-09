package ru.students.lab;

import java.io.IOException;
import java.util.HashMap;

public class CollectionManager {

    private HashMap<Integer, Dragon> collection;
    private FileManager fileManager;

    {
        this.collection = new HashMap<Integer, Dragon>();
    }

    public CollectionManager(String dataFilePath) throws IOException {
        this.fileManager = new FileManager(dataFilePath);
        this.collection = fileManager.getCollectionFromXML();
    }


    public HashMap<Integer, Dragon> getCollection() {
        return this.collection;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
