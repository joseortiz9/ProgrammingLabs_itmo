package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции сохранения коллекции в файл
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ExitCommand extends AbsCommand {

    public static final String DESCRIPTION = "завершить программу (без сохранения в файл))";

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        return null;
    }
    /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        System.exit(0);
    }*/

}
