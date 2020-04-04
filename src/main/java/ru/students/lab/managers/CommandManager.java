package ru.students.lab.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.*;
import ru.students.lab.commands.collectionhandlers.*;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.udp.CommandReader;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;

/**
 * Класс для управления командами
 * @autor Хосе Ортис
 * @version 1.0
*/
public class CommandManager {
    private static final Logger LOG = LogManager.getLogger(CommandManager.class);
    private Map<String, AbsCommand> commands;
    private IHandlerInput userInputHandler;
    /** 
     * Конструктор - создает объект класса CommandManager
     * @param userInputHandler - экземпляр класса для работы с вводимыми в консоль данными
     */
    public CommandManager(IHandlerInput userInputHandler) {
        this.userInputHandler = userInputHandler;
        this.commands = new HashMap<>();
        initCommands();
    }

    public void initCommands() {
        //commands.put("help", new HelpCommand(this.getKeysCommands()));
        //commands.put("man", new ManDescriptorCommand(this.getCommands()));
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        /*commands.put("insert", new InsertCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        //commands.put("save", new SaveColCommand(this.getCollectionManager(), this.getFileManager()));
        //commands.put("exit", new ExitCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand());
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commands.put("remove_lower_key", new RemoveLowerKeyCommand());
        commands.put("filter_contains_name", new FilterColByNameCommand());
        commands.put("filter_starts_with_name", new FilterColByNearNameCommand());
        commands.put("print_descending", new PrintDescendingCommand());*/
    }

    /**
     * Функция выполнения команды
     * @param commandStr - строка, содержащая ключ команды
     */
    public AbsCommand getCommand(String commandStr) {
        try {
            String[] cmd = getCommandFromStr(commandStr);
            AbsCommand command = this.getCommandFromMap(cmd[0]);
            command.setArgs(this.getCommandArgs(cmd));
            return command;
        } catch (NoSuchCommandException ex) {
            userInputHandler.printLn(1, ex.getMessage());
            return null;
        } catch (NumberFormatException ex) {
            userInputHandler.printLn(1,"Incorrect format of the entered value");
            return null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            userInputHandler.printLn(1,"There is a problem in the amount of args passed");
            return null;
        } catch (SecurityException ex) {
            userInputHandler.printLn(1,"Security problems trying to access to the file (Can not be read or edited)");
            return null;
        }
    }
    /**
     * Функция разделения строки на слова
     * @param s - строка входных данных
     * @return возвращает массив слов из входных данных
     */

    public String[] getCommandFromStr(String s) {
        return s.trim().split(" ");
    }
    /**
     * Функция получения аргументов команды из входных данных
     * @param fullStr - строка входных данных
     * @return возвращает массив аргументов команды
     */
    public String[] getCommandArgs(String[] fullStr) {
        String[] inputArgs = new String[2];
        inputArgs = Arrays.copyOfRange(fullStr, 1, fullStr.length);
        return inputArgs;
    }

    public AbsCommand getCommandFromMap(String key) throws NoSuchCommandException {
        if (!commands.containsKey(key)) {
            throw new NoSuchCommandException("What are u writing? type 'help' for the available commands. \nUnknown: '" + key + "'");
        }
        return commands.getOrDefault(key, null);
    }

    public Set<String> getKeysCommands() {
        return this.getCommands().keySet();
    }

    public Map<String, AbsCommand> getCommands() {
        return this.commands;
    }
}
