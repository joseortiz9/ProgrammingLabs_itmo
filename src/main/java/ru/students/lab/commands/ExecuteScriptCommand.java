package ru.students.lab.commands;

import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.factories.DragonFactory;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.util.UserInputHandler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции считывания скрипта из указанного файла
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ExecuteScriptCommand extends AbsCommand {

    private DragonFactory factory;
    private Collection<AbsCommand> commands;

    public ExecuteScriptCommand(Collection<AbsCommand> commands) {
        this.commands = commands;
        factory = new DragonFactory();
        commandKey = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\nSyntax: execute_script file_name";
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        if (args.length < 1)
            throw new ArrayIndexOutOfBoundsException();

        ArrayList<Object> result = new ArrayList<Object>();

        String pathToFile = Paths.get(args[0]).toAbsolutePath().toString();
        String commandsStr = context.fileManager().getStrFromFile(pathToFile);

        String[] commands = commandsStr.trim().split("\n");
        for (int i = 0; i < commands.length; i++) {
            boolean dragonInputSuccess = false;
            String[] ss = commands[i].trim().split(" ");
            AbsCommand command = getCommand(ss[0]);
            command.setArgs(getCommandArgs(ss));
            if (command.requireDragonInput()) {
                String[] inputsAfterInsert = Arrays.copyOfRange(commands, i + 1, commands.length);
                command.addDragonInput(factory.generateFromScript(inputsAfterInsert));
                if (command.getDragon() == null)
                    result.add("An input was not in the correct format. Run 'man insert' to know the rules for entering correct values or The number of inputs is not correct for the amount of attrs");
                else
                    dragonInputSuccess = true;
            }

            try {
                //result.add(command.execute(executionContext));
                if (dragonInputSuccess)
                    i+=8;
            }catch (DragonFormatException ex) {
                result.add(ex.getMessage());
            } catch (NumberFormatException ex) {
                result.add("Incorrect format of the entered value");
            } catch (ArrayIndexOutOfBoundsException ex) {
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
