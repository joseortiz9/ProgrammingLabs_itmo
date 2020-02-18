package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.util.Map;

public class ManDescriptorCommand implements ICommand {

    public static final String DESCRIPTION = "Describe a command by its key";
    private Map<String, ICommand> commandsDictionary;

    public ManDescriptorCommand(Map<String, ICommand> commandsDictionary) {
        this.commandsDictionary = commandsDictionary;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        if (this.commandsDictionary.containsKey(args[0]))
            userInputHandler.printLn("Command: " + args[0] + "\n" + this.commandsDictionary.get(args[0]).getDescription());
        else
            userInputHandler.printLn(1,"Command: '" + args[0] + "' doesn't exist");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
