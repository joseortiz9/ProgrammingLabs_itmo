package ru.students.lab.commands;

import ru.students.lab.models.Dragon;

import java.util.HashMap;

public class ManDescriptorCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "Describe a command by its key";
    private HashMap<String, ICommand> commandsDictionary;

    public ManDescriptorCommand(HashMap<String, ICommand> commandsDictionary) {
        this.commandsDictionary = commandsDictionary;
    }

    @Override
    public void execute(String[] args) {
        if (this.commandsDictionary.containsKey(args[0]))
            setResultExecution(1,"Command: " + args[0] + "\n" + this.commandsDictionary.get(args[0]).getDescription());
        else
            setResultExecution(0,"Command: '" + args[0] + "' doesn't exist");
    }

}
