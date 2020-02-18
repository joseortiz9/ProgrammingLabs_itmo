package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.io.IOException;

public interface ICommand {
    String DESCRIPTION = "No Description";
    void execute(IHandlerInput userInputHandler, String[] args) throws IOException;
    String getDescription();
}
