package ru.students.lab.client;

import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.CommandManager;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleHandler {

    private String[] fullInputCommand;
    String[] inputArgs;

    public ConsoleHandler() {
        this.fullInputCommand = new String[3];
        this.inputArgs = new String[2];
    }

    public void readCommandLine() {
        try(Scanner commandReader = new Scanner(System.in)) {
            System.out.println("\nWrite a command: ");
            String input = commandReader.nextLine();
            fullInputCommand = input.trim().split(" ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getCommandKey() {
        return this.fullInputCommand[0];
    }

    public String[] getCommandArgs() {
        try {
            setInputArgs(Arrays.copyOfRange(fullInputCommand, 1, fullInputCommand.length));
        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        if (fullInputCommand.length == 1)
            return new String[2];

        return inputArgs;
    }

    public void setInputArgs(String[] inputArgs) {
        this.inputArgs = inputArgs;
    }
}
