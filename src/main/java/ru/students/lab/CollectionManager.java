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
        helpCommands.put("help", "вывести справку по доступным командам");
        helpCommands.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        helpCommands.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        helpCommands.put("insert", "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}");
        helpCommands.put("update", "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}");
        helpCommands.put("remove_key", "удалить элемент из коллекции по его ключу.\nSyntax: remove_key key");
        helpCommands.put("clear", "очистить коллекцию");
        helpCommands.put("save", "сохранить коллекцию в файл");
        helpCommands.put("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name");
        helpCommands.put("exit", "завершить программу (без сохранения в файл))");
        helpCommands.put("replace_if_lower", "заменить значение по ключу, если новое значение меньше старого.\nSyntax: replace_if_lower key {element}");
        helpCommands.put("remove_greater_key", "удалить из коллекции все элементы, ключ которых превышает заданный\nSyntax: remove_greater_key key");
        helpCommands.put("remove_lower_key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный.\nSyntax: remove_lower_key key");
        helpCommands.put("filter_contains_name", "вывести элементы, значение поля name которых содержит заданную.\nSyntax: filter_contains_name name");
        helpCommands.put("filter_starts_with_name", "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name");
        helpCommands.put("print_descending", "вывести элементы коллекции в порядке убывания.\nSyntax: print_descending -{k/i/n} где: -k=key / -i=id / -n=name");
    }

    public CollectionManager(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.collection = fileManager.getCollectionFromFile();
        this.collectionCreationDate = new Date();
    }

    public void help() {
        System.out.println("Some Commands for you! \n" + this.getHelpCommands().keySet());
        System.out.println("Write man {key} to have some details");
    }

    public void man(String commandKey) {
        System.out.println("Command: " + commandKey + "\n" + this.getHelpCommands().get(commandKey));
    }

    public void show() {
        this.getCollection().forEach((key, value) -> System.out.println("key:" + key + " -> " + value));
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

    public void insert(Integer key, String newDragonXml) {
        try {
            Dragon newDragon = this.getFileManager().getDragonFromStr(newDragonXml);
            // If it doesn't exist and it successfully put it, so it returns null
            if (this.getCollection().putIfAbsent(key, newDragon) == null) {
                System.out.println(newDragon.toString() + " Successfully saved!");
                this.printRunSave();
            } else
                throw new Exception("The key '" + key + "' already exist");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update(Integer id, String newDragonXml) {
        try {
            Dragon newDragon = this.getFileManager().getDragonFromStr(newDragonXml);

            int foundKey = 0;
            for (HashMap.Entry<Integer, Dragon> dragonEntry : this.getCollection().entrySet())
                if (dragonEntry.getValue().getId().equals(id))
                    foundKey = dragonEntry.getKey();

            if (foundKey == 0)
                throw new Exception("The ID '" + id + "' doesn't exist");
            else {
                this.getCollection().put(foundKey, newDragon);
                System.out.println(newDragon.toString() + " Successfully updated!");
                this.printRunSave();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void remove_key(Integer key) {
        try {
            if (this.getCollection().remove(key) == null) {
                System.out.println("k:" + key + " Successfully removed!");
                this.printRunSave();
            } else
                throw new Exception("The key '" + key + "' doesn't exist");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
