package ru.students.lab.commands;

import ru.students.lab.database.Credentials;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции сохранения коллекции в файл
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ExitCommand extends AbsCommand {

    public ExitCommand() {
        commandKey = "exit";
        description = "завершить программу (без сохранения в файл))";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        return null;
    }

}
