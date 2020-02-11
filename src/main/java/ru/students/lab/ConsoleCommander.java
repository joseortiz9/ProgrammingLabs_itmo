package ru.students.lab;

import java.util.Scanner;

public class ConsoleCommander {

    private CollectionManager collectionManager;
    private String fullInputCommand;

    ConsoleCommander(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.fullInputCommand = "";
    }

    public void startInteraction() {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!fullInputCommand.equals("exit")) {
                System.out.println("\nWrite a command: ");
                fullInputCommand = commandReader.nextLine();
                this.getManager().runCollectionMethod(fullInputCommand);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public CollectionManager getManager() {
        return collectionManager;
    }
}
