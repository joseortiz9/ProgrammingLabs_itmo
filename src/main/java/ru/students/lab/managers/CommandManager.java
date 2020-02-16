package ru.students.lab.managers;

import ru.students.lab.client.ConsoleHandler;
import ru.students.lab.commands.*;
import ru.students.lab.commands.collectionhandlers.*;
import java.util.HashMap;
import java.util.Set;

public class CommandManager {
    private HashMap<String, ICommand> commands;
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private ConsoleHandler consoleHandler;

    public CommandManager(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.consoleHandler = new ConsoleHandler();
        this.collectionManager =  new CollectionManager(fileManager.getCollectionFromFile());
        this.commands = new HashMap<>();
        commands.put("help", new HelpCommand(this.getKeysCommands()));
        commands.put("man", new ManDescriptorCommand());
        commands.put("info", new InfoCommand(this.getCollectionManager()));
        commands.put("show", new ShowCommand(this.getCollectionManager()));
        commands.put("insert", new InsertCommand(this.getCollectionManager()));
        commands.put("update", new UpdateCommand(this.getCollectionManager()));
        commands.put("remove_key", new RemoveKeyCommand(this.getCollectionManager()));
        commands.put("clear", new ClearCommand(this.getCollectionManager()));
        commands.put("save", new SaveColCommand(this.getCollectionManager(), this.getFileManager()));
        commands.put("execute_script", new ExecuteScriptCommand(this.getFileManager()));
        commands.put("exit", new ExitCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand(this.getCollectionManager()));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(this.getCollectionManager()));
        commands.put("remove_lower_key", new RemoveLowerKeyCommand(this.getCollectionManager()));
        commands.put("filter_contains_name", new FilterColByNameCommand(this.getCollectionManager()));
        commands.put("filter_starts_with_name", new FilterColByNearNameCommand(this.getCollectionManager()));
        commands.put("print_descending", new PrintDescendingCommand(this.getCollectionManager()));
    }

    public void startInteraction()
    {
        String commandInputKey = "";
        String[] commandInputArgs;
        ICommand command;
        do {
            //consoleHandler.readCommandLine();
            commandInputKey = consoleHandler.getCommandKey();
            commandInputArgs = consoleHandler.getCommandArgs();
            if (this.getCommands().containsKey(commandInputKey) || commandInputKey != null) {
                command = this.getCommand(commandInputKey);
                command.execute(commandInputArgs);
                System.out.println(command.getResultExecution());
            }
            else
                System.out.println("What are u writing? type 'help' for the available commands. \nUnknown: '" + commandInputKey + "'");

        } while (!commandInputKey.equals("exit"));
    }

    public ICommand getCommand(String key) {
        return this.getCommands().get(key);
    }

    public void runCommand(String key, String[] args) {
        this.getCommand(key).execute(args);
    }

    public String getDescriptionCommand(String key) {
        return this.getCommand(key).getDescription();
    }

    public Set<String> getKeysCommands() {
        return this.getCommands().keySet();
    }

    public HashMap<String, ICommand> getCommands() {
        return this.commands;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
