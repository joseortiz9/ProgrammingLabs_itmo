package ru.students.lab.client;

import java.util.NoSuchElementException;
import java.util.Scanner;
/** 
 * Класс, реализующий функции интерфейса для получения входных данных и взаимодействия с пользователем 
 * @autor Хосе Ортис
 * @version 1.0
*/

public class UserInputHandler implements IHandlerInput {

    private Scanner commandReader;
     /** 
     * Конструктор - создает объект класса UserInputHandler для работы со входными данными, создает сканер для считывания данных
     * @see UserInputHandler#UserInputHandler()
     */

    public UserInputHandler() {
        commandReader = new Scanner(System.in);
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
