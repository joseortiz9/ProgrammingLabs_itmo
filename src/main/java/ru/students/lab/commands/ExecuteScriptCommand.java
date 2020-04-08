package ru.students.lab.commands;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ExecuteScriptCommand extends AbsCommand {

    public final String description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = context.fileManager().getStrFromFile(pathToFile);

        /*userInputHandler = new UserInputHandler(false);

        String[] commands = commandsStr.trim().split("\n");
        for (int i = 0; i < commands.length; i++) {
            userInputHandler.setInputsAfterInsert(Arrays.copyOfRange(commands, i + 1, commands.length));
            this.commandManager.executeCommand(commands[i], userInputHandler);
            if (userInputHandler.getResultCode() == 0)
                i+=8;
        }*/
        return null;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
