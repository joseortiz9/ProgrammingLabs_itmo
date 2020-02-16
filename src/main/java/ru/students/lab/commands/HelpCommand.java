package ru.students.lab.commands;

import java.util.Set;

public class HelpCommand extends AbsCommand {

    public static final String DESCRIPTION = "вывести справку по доступным командам";
    private Set<String> keysCommands;

    public HelpCommand(Set<String> keysCommands) {
        this.keysCommands = keysCommands;
    }

    @Override
    public void execute(String[] args) {
        setResultExecution("Some Commands for you! \n" + this.keysCommands.toString() + "\n Write man {key} to have some details");
    }
}
