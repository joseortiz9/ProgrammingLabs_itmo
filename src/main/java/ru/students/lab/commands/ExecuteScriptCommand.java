package ru.students.lab.commands;

import ru.students.lab.managers.FileManager;

public class ExecuteScriptCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    FileManager fileManager;

    public ExecuteScriptCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            String commandsStr = this.getFileManager().getStrFromFile(args[0]);
            String[] commands = commandsStr.trim().split("\n");
            for (String command : commands) {
                System.out.println();
                //this.runCollectionMethod(command);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
