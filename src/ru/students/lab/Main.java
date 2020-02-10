package ru.students.lab;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //fill the xml file with random data
        new TesterFiller().collectionToXML();

        String pathToData = "/home/joseortiz09/Documents/ProgrammingProjects/IdeaProjects/Lab5/src/data.xml";
        ConsoleCommander console = new ConsoleCommander(new CollectionManager(pathToData));
        console.startInteraction();

        /*Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }*/
    }
}
