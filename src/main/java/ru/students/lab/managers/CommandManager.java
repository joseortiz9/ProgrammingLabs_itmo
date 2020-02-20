package ru.students.lab.managers;

import com.thoughtworks.xstream.XStreamException;
import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.*;
import ru.students.lab.commands.collectionhandlers.*;
import ru.students.lab.exceptions.NoSuchCommandException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
 /** 
 * Класс для управления командами
 * @autor Хосе Ортис
 * @version 1.0
*/
public class CommandManager {
    private Map<String, ICommand> commands;
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private IHandlerInput userInputHandler;
    /** 
     * Конструктор - создает объект класса CommandManager
     * @param fileManager - экземпляр класса для работы с файлами
     * @param userInputHandler - экземпляр класса для работы с вводимыми в консоль данными
     * @param collectionManager - экземпляр класса для работы с коллекцией
     * @see CommandManager#CommandManager(CollectionManager)
     */
    public CommandManager(IHandlerInput userInputHandler, FileManager fileManager, CollectionManager collectionManager) {
        this.fileManager = fileManager;
        this.userInputHandler = userInputHandler;
        this.collectionManager =  collectionManager;
        this.commands = new HashMap<>();
        commands.put("help", new HelpCommand(this.getKeysCommands()));
        commands.put("man", new ManDescriptorCommand(this.getCommands()));
        commands.put("info", new InfoCommand(this.getCollectionManager()));
        commands.put("show", new ShowCommand(this.getCollectionManager()));
        commands.put("insert", new InsertCommand(this.getCollectionManager()));
        commands.put("update", new UpdateCommand(this.getCollectionManager()));
        commands.put("remove_key", new RemoveKeyCommand(this.getCollectionManager()));
        commands.put("clear", new ClearCommand(this.getCollectionManager()));
        commands.put("save", new SaveColCommand(this.getCollectionManager(), this.getFileManager()));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand(this.getCollectionManager()));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(this.getCollectionManager()));
        commands.put("remove_lower_key", new RemoveLowerKeyCommand(this.getCollectionManager()));
        commands.put("filter_contains_name", new FilterColByNameCommand(this.getCollectionManager()));
        commands.put("filter_starts_with_name", new FilterColByNearNameCommand(this.getCollectionManager()));
        commands.put("print_descending", new PrintDescendingCommand(this.getCollectionManager()));
    }

    /**
     * Функция получения команды с консоли
     */
    public void startInteraction() {
        while(true) {
            String commandStr = userInputHandler.readWithMessage("Write Command: ");
            executeCommand(commandStr, true);
        }
    }
    /**
     * Функция выполнения команды
     * @param commandStr - строка, содержащая ключ команды
     * @param interactive - определяет, является ли команда интерактивной
     */
    public void executeCommand(String commandStr, boolean interactive) {
        try {
            String[] cmd = getCommandFromStr(commandStr);
            ICommand command = this.getCommand(cmd[0]);
            if (!interactive) userInputHandler.printLn("\nRUNNING COMMAND: " + commandStr);
            command.execute(userInputHandler, this.getCommandArgs(cmd));
        } catch (NoSuchCommandException | IOException ex) {
            userInputHandler.printLn(1, ex.getMessage());
        } catch (NumberFormatException ex) {
            userInputHandler.printLn(1,"Incorrect format of the entered value");
        } catch (ArrayIndexOutOfBoundsException ex) {
            userInputHandler.printLn(1,"There is a problem in the amount of args passed");
        } catch (XStreamException ex) {
            userInputHandler.printLn(1,"Problems trying to parse object from/into a file");
        } catch (SecurityException ex) {
            userInputHandler.printLn(1,"Security problems trying to access to the file (Can not be read or edited)");
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

    public ICommand getCommand(String key) throws NoSuchCommandException {
        if (!commands.containsKey(key)) {
            throw new NoSuchCommandException("What are u writing? type 'help' for the available commands. \nUnknown: '" + key + "'");
        }
        return commands.getOrDefault(key, null);
    }

    public Set<String> getKeysCommands() {
        return this.getCommands().keySet();
    }

    public Map<String, ICommand> getCommands() {
        return this.commands;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
