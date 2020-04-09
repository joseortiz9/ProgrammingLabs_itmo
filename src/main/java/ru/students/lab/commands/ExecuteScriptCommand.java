package ru.students.lab.commands;

import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.util.UserInputHandler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

/**
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ExecuteScriptCommand extends AbsCommand {

    private Collection<AbsCommand> commands;

    public ExecuteScriptCommand(Collection<AbsCommand> commands) {
        this.commands = commands;
        commandKey = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = context.fileManager().getStrFromFile(pathToFile);

        String[] commands = commandsStr.trim().split("\n");
        for (int i = 0; i < commands.length; i++) {
            String[] inputsAfterInsert = Arrays.copyOfRange(commands, i + 1, commands.length);
            //getCommand()
            /*if (userInputHandler.getResultCode() == 0)
                i+=8;*/
            /*try {
                AbsCommand command = this.commandManager.getCommand(commands[i]);
                responseExecution = command.execute(executionContext);
            }catch (DragonFormatException ex) {
                responseExecution = ex.getMessage();
                LOG.error(ex.getMessage(), ex);
            } catch (NumberFormatException ex) {
                responseExecution = "Incorrect format of the entered value";
                LOG.error("Incorrect format of the entered value", ex);
            } catch (ArrayIndexOutOfBoundsException ex) {
                responseExecution = "There is a problem in the amount of args passed";
                LOG.error("There is a problem in the amount of args passed", ex);
            }*/
        }
        return null;
    }

    private AbsCommand getCommand(String s) {
        return commands.stream().filter(e -> e.getCommandKey().equals(s)).findFirst().orElse(null);
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
