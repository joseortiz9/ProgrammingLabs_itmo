package ru.students.lab.client;

import java.util.Scanner;
/** 
 * Класс, реализующий функции интерфейса для получения входных данных и взаимодействия с пользователем 
 * @autor Хосе Ортис
 * @version 1.0
*/

public class UserInputHandler implements IHandlerInput {

    private Scanner commandReader;
    private boolean interactive;
    private String[] inputsAfterInsert;
    private int resultCode;

     /** 
     * Конструктор - создает объект класса UserInputHandler для работы со входными данными, создает сканер для считывания данных
     * @see UserInputHandler#UserInputHandler(boolean)
     */
    public UserInputHandler(boolean interactive) {
        this.commandReader = new Scanner(System.in);
        this.interactive = interactive;
        this.inputsAfterInsert = new String[8];
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
        this.resultCode = code;
        String codeResult = (this.resultCode == 0) ? "SUCCESSFUL: " : "ERROR: ";
        System.out.println(codeResult + s);
    }

    @Override
    public void printElemOfList(String s) {
        System.out.println(s);
    }

    @Override
    public boolean isInteractive() {
        return interactive;
    }

    @Override
    public String[] getInputsAfterInsert() {
        return inputsAfterInsert;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public Scanner getCommandReader() {
        return commandReader;
    }

    @Override
    public void setInputsAfterInsert(String[] inputsAfterInsert) {
        this.inputsAfterInsert = inputsAfterInsert;
    }
}
