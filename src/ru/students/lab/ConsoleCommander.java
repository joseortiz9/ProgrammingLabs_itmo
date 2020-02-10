package ru.students.lab;

import java.util.Scanner;

public class ConsoleCommander {

    private CollectionManager collectionManager;
    private String fullInputCommand;

    {
        this.fullInputCommand = "";
    }

    ConsoleCommander(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void startInteraction() {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!fullInputCommand.equals("exit")) {
                System.out.println("\nWrite a command: ");
                fullInputCommand = commandReader.nextLine();
                String[] userCommand = fullInputCommand.trim().split(" ", 3);
                switch (userCommand[0]) {
                    case "": break;
                    case "help":
                        this.getManager().help();
                        break;
                    case "info":
                        System.out.println(this.getManager().toString());
                        break;
                    case "show":
                        this.getManager().show();
                        break;
                    case "clear":
                        this.getManager().clear();
                        break;
                    case "save":
                    case "exit":
                        this.getManager().save();
                        break;
                    case "print_descending":
                        this.getManager().print_descending();
                        break;
                    case "insert":
                        this.getManager().insert(Integer.valueOf(userCommand[1]), userCommand[2]);
                        break;
                    case "update":
                        this.getManager().update(Integer.valueOf(userCommand[1]), userCommand[2]);
                        break;
                    case "remove_key":
                        this.getManager().remove_key(Integer.valueOf(userCommand[1]));
                        break;
                    case "execute_script":
                        this.getManager().execute_script(userCommand[1]);
                        break;
                    case "replace_if_lower":
                        this.getManager().replace_if_lower(Integer.valueOf(userCommand[1]), userCommand[2]);
                        break;
                    case "remove_greater_key":
                        this.getManager().remove_greater_key(Integer.valueOf(userCommand[1]));
                        break;
                    case "remove_lower_key":
                        this.getManager().remove_lower_key(Integer.valueOf(userCommand[1]));
                        break;
                    case "filter_contains_name":
                        this.getManager().filter_contains_name(userCommand[1]);
                        break;
                    case "filter_starts_with_name":
                        this.getManager().filter_starts_with_name(userCommand[1]);
                        break;
                    default:
                        System.out.println("What are u writing? type 'help' for the available commands");
                }
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
