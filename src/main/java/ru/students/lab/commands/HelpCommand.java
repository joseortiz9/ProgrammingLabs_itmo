package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.util.Set;

public class HelpCommand implements ICommand {

    public static final String DESCRIPTION = "вывести справку по доступным командам";
    private Set<String> keysCommands;

    public HelpCommand(Set<String> keysCommands) {
        this.keysCommands = keysCommands;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        userInputHandler.printLn("Some Commands for you! \n" + this.keysCommands.toString() + "\n Write man {key} to have some details");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
