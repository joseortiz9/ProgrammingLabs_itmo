package ru.students.lab.client;

public interface IHandlerInput {
    String read();
    String readWithMessage(String s);
    void printLn(String s);
    void printLn(int code, String s);
    void printElemOfList(String s);
}
