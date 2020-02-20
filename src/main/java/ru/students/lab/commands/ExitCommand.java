package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;
 /** 
 * Класс для выполнения и получения информации о функции сохранения коллекции в файл
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ExitCommand implements ICommand {

    public static final String DESCRIPTION = "завершить программу (без сохранения в файл))";

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
