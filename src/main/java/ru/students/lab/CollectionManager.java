package ru.students.lab;

import org.jetbrains.annotations.NotNull;

import java.util.*;

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
        helpCommands.put("print_descending", "вывести элементы коллекции в порядке убывания.\nSyntax: print_descending -{k/i/n/d} где: -k=key / -i=id / -n=name / -d=creation_date");
    }

    public CollectionManager(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.collection = fileManager.getCollectionFromFile();
        this.collectionCreationDate = new Date();
    }


    public void runCollectionMethod(@NotNull String fullInputCommand) {
        try {
            String[] userCommand = fullInputCommand.trim().split(" ", 3);
            switch (userCommand[0]) {
                case "": break;
                case "help":
                    this.help();
                    break;
                case "man":
                    this.man(userCommand[1]);
                    break;
                case "info":
                    System.out.println(this.toString());
                    break;
                case "show":
                    this.show();
                    break;
                case "clear":
                    this.clear();
                    break;
                case "save":
                case "exit":
                    this.save();
                    break;
                case "print_descending":
                    this.print_descending(userCommand[1]);
                    break;
                case "insert":
                    this.insert(Integer.valueOf(userCommand[1]), userCommand[2]);
                    break;
                case "update":
                    this.update(Integer.valueOf(userCommand[1]), userCommand[2]);
                    break;
                case "remove_key":
                    this.remove_key(Integer.valueOf(userCommand[1]));
                    break;
                case "execute_script":
                    this.execute_script(userCommand[1]);
                    break;
                case "replace_if_lower":
                    this.replace_if_lower(Integer.valueOf(userCommand[1]), userCommand[2]);
                    break;
                case "remove_greater_key":
                    this.remove_greater_key(Integer.valueOf(userCommand[1]));
                    break;
                case "remove_lower_key":
                    this.remove_lower_key(Integer.valueOf(userCommand[1]));
                    break;
                case "filter_contains_name":
                    this.filter_contains_name(userCommand[1]);
                    break;
                case "filter_starts_with_name":
                    this.filter_starts_with_name(userCommand[1]);
                    break;
                default:
                    System.out.println("What are u writing? type 'help' for the available commands");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }
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


    public void sortByKey(){
        System.out.println("Sorting by key...");
        this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
    public void sortById(){
        System.out.println("Sorting by ID...");
        this.getCollection()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(x -> x.getValue().getId()))
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
    public void sortByName(){
        System.out.println("Sorting by Name...");
        this.getCollection()
                .entrySet()
                .stream()
                .sorted((x, y) -> x.getValue().getName().compareTo(y.getValue().getName()))
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
    public void sortByCreationDate(){
        System.out.println("Sorting by Creation Date...");
        this.getCollection()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }

    public void print_descending(@NotNull String arg) {
        switch (arg) {
            case "":
            case "-k":
                this.sortByKey();
                break;
            case "-i":
                this.sortById();
                break;
            case "-n":
                this.sortByName();
                break;
            case "-d":
                this.sortByCreationDate();
                break;
            default:
                System.out.println("This option is not available. Correct= -{k/i/n/d}");
        }
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
            for (HashMap.Entry<Integer, Dragon> dragonEntry : this.getCollection().entrySet()) {
                if (dragonEntry.getValue().getId().equals(id)) {
                    foundKey = dragonEntry.getKey();
                    break;
                }
            }

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
        try {
            String commandsStr = this.getFileManager().getStrFromFile(fileName);
            String[] commands = commandsStr.trim().split("\n");
            for (String command : commands) {
                System.out.println();
                this.runCollectionMethod(command);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void replace_if_lower(Integer key, String newDragonXml) {
        try {
            Dragon newDragon = this.getFileManager().getDragonFromStr(newDragonXml);
            //is newer
            if (newDragon.compareTo(this.getCollection().get(key)) > 0) {
                this.getCollection().put(key, newDragon);
                System.out.println("Successfully Replaced!");
            } else
                System.out.println("Is not old enough!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void remove_greater_key(Integer key) {
        try {
            int initialSize = this.getCollection().size();

            this.getCollection()
                    .entrySet()
                    .removeIf(dragonEntry -> dragonEntry.getKey() > key);

            int finalSize = this.getCollection().size();

            if (initialSize == finalSize)
                System.out.println("No Dragons removed");
            else
                System.out.println("A total of " + (initialSize - finalSize) + " were removed");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void remove_lower_key(Integer key) {
        try {
            int initialSize = this.getCollection().size();

            this.getCollection()
                    .entrySet()
                    .removeIf(dragonEntry -> key > dragonEntry.getKey());

            int finalSize = this.getCollection().size();

            if (initialSize == finalSize)
                System.out.println("No Dragons removed");
            else
                System.out.println("A total of " + (initialSize - finalSize) + " were removed");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void filter_contains_name(String name) {
        try {
            Optional<Dragon> foundDragon = this.getCollection()
                    .values()
                    .stream()
                    .filter(dragon -> dragon.getName().equals(name))
                    .findFirst();

            if (foundDragon.isEmpty())
                throw new Exception("The dragon with the name '" + name + "' doesn't exist");
            else
                System.out.println("There you are! " + foundDragon.get().toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void filter_starts_with_name(String name) {
        try {
            String regex = "^("+name+").*$";

            Dragon foundDragon = this.getCollection()
                    .values()
                    .stream()
                    .filter(dragon -> dragon.getName().matches(regex))
                    .findFirst()
                    .orElse(null);

            if (foundDragon == null)
                throw new Exception("The dragon with the name '" + name + "' doesn't exist");
            else
                System.out.println("There you are! " + foundDragon.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
