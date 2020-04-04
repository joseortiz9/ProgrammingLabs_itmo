package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.client.UserInputHandler;
import ru.students.lab.managers.CommandManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

/**
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ExecuteScriptCommand extends AbsCommand {

    public static final String DESCRIPTION = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";


    @Override
    public Object execute(ExecutionContext context) throws IOException {
        return null;
    }
    /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = commandManager.getFileManager().getStrFromFile(pathToFile);

        userInputHandler = new UserInputHandler(false);

        String[] commands = commandsStr.trim().split("\n");
        for (int i = 0; i < commands.length; i++) {
            userInputHandler.setInputsAfterInsert(Arrays.copyOfRange(commands, i + 1, commands.length));
            this.commandManager.executeCommand(commands[i], userInputHandler);
            if (userInputHandler.getResultCode() == 0)
                i+=8;
        }
    }*/

}
