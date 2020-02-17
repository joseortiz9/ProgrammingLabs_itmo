package ru.students.lab.commands;

import ru.students.lab.managers.FileManager;

import java.util.Arrays;
import java.util.HashMap;

public class ExecuteScriptCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    FileManager fileManager;
    private HashMap<String, ICommand> commsDictionary;

    public ExecuteScriptCommand(FileManager fileManager, HashMap<String, ICommand> commandsDictionary) {
        this.fileManager = fileManager;
        this.commsDictionary = commandsDictionary;
    }

    @Override
    public void execute(String[] args) {
        try {
            String commandsStr = this.fileManager.getStrFromFile(args[0]);
            String[] commands = commandsStr.trim().split("\n");
            for (String commandStr : commands) {
                String[] comFragmented = commandStr.trim().split(" ");
                System.out.println("\nRUNNING COMMAND: " + comFragmented[0]);
                if (this.commsDictionary.containsKey(comFragmented[0])) {
                    ICommand command = this.commsDictionary.get(comFragmented[0]);
                    try {
                        command.execute(getCommandArgs(comFragmented));
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
                        command.setResultExecution(1, "arguments expected NO passed!");
                    } finally {
                        System.out.println(command.getResultExecution());
                    }
                }
                else
                    System.out.println("Unknown: '" + commandStr + "'");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String[] getCommandArgs(String[] fullLine) {
        String[] args = new String[2];
        try {
            args = Arrays.copyOfRange(fullLine, 1, fullLine.length);
        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        if (fullLine.length == 1) {
            args = new String[2];
            args[0] = "";
        }

        return args;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
