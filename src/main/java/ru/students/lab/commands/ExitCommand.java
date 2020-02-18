package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

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
