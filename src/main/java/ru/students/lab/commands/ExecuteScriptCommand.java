package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.managers.CommandManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
 /** 
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/


public class ExecuteScriptCommand implements ICommand {

    public static final String DESCRIPTION = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    private CommandManager commandManager;
    /** 
     * Конструктор - создает объект класса ExecuteScriptCommand и экземпляр класса commandManager для последущего выполнения команд
     * @see ExecuteScriptCommand#ExecuteScriptCommand(CollectionManager)
     */
    public ExecuteScriptCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = commandManager.getFileManager().getStrFromFile(pathToFile);

        String[] commands = commandsStr.trim().split("\n");
        for (String commandString : commands) {
            this.commandManager.executeCommand(commandString, false);
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
