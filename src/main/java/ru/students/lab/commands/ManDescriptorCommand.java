package ru.students.lab.commands;

public class ManDescriptorCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "Describe a command by its key";

    @Override
    public void execute(String[] args) {
        //System.out.println("Command: " + args[0] + "\n" + this.getHelpCommands().get(commandKey));
    }

}
