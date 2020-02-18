package ru.students.lab.client;

import java.util.Arrays;
import java.util.Scanner;

public class UserInputHandler implements IHandlerInput {

    private Scanner commandReader;

    public UserInputHandler() {
        commandReader = new Scanner(System.in);
    }

    public String readDragonAttr(String attr) {
        System.out.print("Dragon's " + attr + ": ");
        return this.read();
    }



    @Override
    public String read() {
        return commandReader.nextLine();
    }

    @Override
    public String readWithMessage(String s) {
        System.out.print(s);
        return this.read();
    }

    @Override
    public void printLn(String s) {
        System.out.println(s);
    }

    @Override
    public void printLn(int code, String s) {
        String codeResult = (code == 0) ? "SUCCESSFUL: " : "ERROR: ";
        System.out.println(codeResult + s);
    }

    @Override
    public void printElemOfList(String s) {
        System.out.println(s);
    }
}
