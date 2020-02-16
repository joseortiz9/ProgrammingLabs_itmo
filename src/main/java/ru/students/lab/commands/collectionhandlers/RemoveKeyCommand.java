package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class RemoveKeyCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "удалить элемент из коллекции по его ключу.\nSyntax: remove_key key";
    private CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (this.collectionManager.removeKey(Integer.valueOf(args[0])) != null)
            setResultExecution("k:" + args[0] + " Successfully removed!");
        else
            setResultExecution("The key '" + args[0] + "' doesn't exist");
    }
}
