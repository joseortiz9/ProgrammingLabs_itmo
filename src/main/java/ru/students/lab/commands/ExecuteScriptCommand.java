package ru.students.lab.commands;

import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.factories.DragonFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ExecuteScriptCommand extends AbsCommand {

    private final DragonFactory factory;
    private final List<AbsCommand> commands;

    public ExecuteScriptCommand(List<AbsCommand> commands) {
        this.commands = commands;
        factory = new DragonFactory();
        commandKey = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        if (context.DBRequestManager().credentialsNotExist(credentials))
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

        ArrayList<Object> result = new ArrayList<Object>();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = context.fileManager().getStrFromFile(pathToFile);

        String[] commands = commandsStr.trim().split("\n");
        for (int i = 0; i < commands.length; i++) {
            try {
                result.add("\nCOMMAND #" + i);
                boolean dragonInputSuccess = false;
                String[] ss = commands[i].trim().split(" ");
                AbsCommand command = getCommand(ss[0]);
                if (command == null) {
                    result.add("Not found command");
                    break;
                }
                command.setArgs(getCommandArgs(ss));
                if (command.requireInput() == ICommand.TYPE_INPUT_DRAGON) {
                    String[] inputsAfterInsert = Arrays.copyOfRange(commands, i + 1, commands.length);
                    command.addInput(factory.generateFromScript(inputsAfterInsert));
                    if (command.getInput() == null)
                        result.add("An input was not in the correct format or The number of inputs is different from the needed");
                    else
                        dragonInputSuccess = true;
                }
                result.add(command.execute(context, credentials));
                if (dragonInputSuccess)
                    i+=9;
            } catch (DragonFormatException ex) {
                result.add(ex.getMessage());
            } catch (NumberFormatException ex) {
                result.add("Incorrect format of the entered value");
            } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
                result.add("There is a problem in the amount of args passed");
            }
        }
        return result;
    }

    private AbsCommand getCommand(String s) {
        return commands.stream().filter(e -> e.getCommandKey().equals(s)).findFirst().orElse(null);
    }

    public String[] getCommandArgs(String[] fullStr) {
        String[] inputArgs = new String[2];
        inputArgs = Arrays.copyOfRange(fullStr, 1, fullStr.length);
        return inputArgs;
    }
}
