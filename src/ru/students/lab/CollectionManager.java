package ru.students.lab;

import java.util.Date;
import java.util.HashMap;

public class CollectionManager {

    private HashMap<Integer, Dragon> collection;
    private FileManager fileManager;
    private Date collectionCreationDate;
    private HashMap<String, String> helpCommands;

    {
        helpCommands = new HashMap<>();
        helpCommands.put("insert","добавить новый элемент с заданным ключом");
    }

    public CollectionManager(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.collection = fileManager.getCollectionFromFile();
        this.collectionCreationDate = new Date();
    }

    public void help() {
        System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void show() {
        this.getCollection().forEach((key, value) -> System.out.println("key: " + key + " " + value));
    }

    public void clear() {
        this.getCollection().clear();
        System.out.println("All elems deleted successfully!");
        this.printRunSave();
    }

    public void save() {
        this.getFileManager().SaveCollectionInXML(this.getCollection());
        System.out.println("All elems saved successfully!");
    }

    public void print_descending() {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void insert(Integer key, String newDragon) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void update(Integer id, String newDragon) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void remove_key(Integer key) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void execute_script(String fileName) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void replace_if_lower(Integer key, String newDragon) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void remove_greater_key(Integer key) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void remove_lower_key(Integer key) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }


    public void filter_contains_name(String name) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }

    public void filter_starts_with_name(String name) {
        //System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
    }


    public void printRunSave() {
        System.out.println("Run 'save' to save all the changes in the file");
    }

    public HashMap<String, String> getHelpCommands() {
        return helpCommands;
    }
    public FileManager getFileManager() {
        return fileManager;
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

    @Override
    public String toString() {
        return "Type of Collection: " + this.getCollection().getClass() +
                "\nCreation Date: " + collectionCreationDate.toString() +
                "\nAmount of elements: " + this.getCollection().size();
    }

}
