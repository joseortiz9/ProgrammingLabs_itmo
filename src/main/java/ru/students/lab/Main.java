package ru.students.lab;

public class Main {

    public static void main(String[] args) {
        String pathToData = "/home/joseortiz09/Documents/ProgrammingProjects/IdeaProjects/Lab5/src/main/java/data.xml";

        //fill the xml file with random data
        new TesterFiller().collectionToXML(pathToData);

        ConsoleCommander console = new ConsoleCommander(new CollectionManager(pathToData));
        console.startInteraction();
    }
}
