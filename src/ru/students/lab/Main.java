package ru.students.lab;

import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        TesterFiller tester = new TesterFiller();
        //tester.collectionToXML();
        //tester.printCollection();

        String pathToData = "/home/joseortiz09/Documents/ProgrammingProjects/IdeaProjects/Lab5/src/data.xml";
        ConsoleCommander console = new ConsoleCommander(new CollectionManager(pathToData));
        //console.startInteraction();
        console.printCollection();

        /*Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }*/
    }
}
