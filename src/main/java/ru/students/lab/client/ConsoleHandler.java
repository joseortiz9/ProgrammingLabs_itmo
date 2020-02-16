package ru.students.lab.client;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleHandler {

    private String[] fullInputCommand;
    private String[] inputArgs;
    private Scanner commandReader;

    public ConsoleHandler() {
        this.fullInputCommand = new String[2];
        this.inputArgs = new String[2];
        commandReader = new Scanner(System.in);
    }

    public void readCommandLine() {
        System.out.println("\nWrite a command: ");
        String input = commandReader.nextLine();
        fullInputCommand = input.trim().split(" ");
    }

    public String getCommandKey() {
        return this.fullInputCommand[0];
    }

    public String[] getCommandArgs() {
        try {
            inputArgs = Arrays.copyOfRange(fullInputCommand, 1, fullInputCommand.length);
        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        if (fullInputCommand.length == 1)
            return new String[2];

        return inputArgs;
    }

    public String readDragonAttr(String attr) {
        System.out.print("Dragon's " + attr + ": ");
        return commandReader.nextLine();
    }

}
