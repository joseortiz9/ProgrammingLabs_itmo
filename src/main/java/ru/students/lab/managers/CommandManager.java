package ru.students.lab.managers;

import ru.students.lab.commands.*;
import ru.students.lab.commands.collectionhandlers.*;
import ru.students.lab.exceptions.NoSuchCommandException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для управления командами
 * @autor Хосе Ортис
 * @version 1.0
*/
public class CommandManager {
    private final Map<String, AbsCommand> commands;
    /** 
     * Конструктор - создает объект класса CommandManager
     */
    public CommandManager() {
        this.commands = new HashMap<>();
        initCommands();
    }

    public void initCommands() {
        commands.put("help", new HelpCommand(this.getKeysCommands()));
        commands.put("man", new ManDescriptorCommand(this.getCommands()));
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("insert", new InsertCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand());
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commands.put("remove_lower_key", new RemoveLowerKeyCommand());
        commands.put("filter_contains_name", new FilterColByNameCommand());
        commands.put("filter_starts_with_name", new FilterColByNearNameCommand());
        commands.put("print_descending", new PrintDescendingCommand());
        commands.put("exit", new ExitCommand());
        commands.put("execute_script", new ExecuteScriptCommand(getCommandsValues()));
        //commands.put("load", new LoadFromFileCommand());
        commands.put("export", new ExportToFileCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
    }

    /**
     * Функция выполнения команды
     * @param commandStr - строка, содержащая ключ команды
     */
    public AbsCommand getCommand(String commandStr) throws NoSuchElementException {
        String[] cmd = getCommandFromStr(commandStr);
        AbsCommand command = this.getCommandFromMap(cmd[0]);
        command.setArgs(this.getCommandArgs(cmd));
        return command;
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
    /**
     * Функция получения команды из коллекции команд по ключу
     * @param key - строка входных данных - ключ команды
     * @return возвращает объект класса AbsCommand
     */
    public AbsCommand getCommandFromMap(String key) throws NoSuchCommandException {
        if (!commands.containsKey(key)) {
            throw new NoSuchCommandException("What are u writing? type 'help' for the available commands. \nUnknown: '" + key + "'");
        }
        return commands.getOrDefault(key, null);
    }

    private List<AbsCommand> getCommandsValues(){
        List<AbsCommand> l = new ArrayList<>();
        commands.values().stream().forEach(e -> {
            if(!(e.getCommandKey().equals("help")) && !(e.getCommandKey().equals("man")))
                l.add(e);
        });
        return l;
    }

    public Set<String> getKeysCommands() {
        return this.getCommands().keySet();
    }

    public Map<String, AbsCommand> getCommands() {
        return this.commands;
    }
}
