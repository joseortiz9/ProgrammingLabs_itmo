package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class RemoveLowerKeyCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "удалить из коллекции все элементы, ключ которых меньше, чем заданный.\nSyntax: remove_lower_key key";
    private CollectionManager collectionManager;

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        int initialSize = this.collectionManager.getCollection().size();
        this.collectionManager.removeLowerKey(Integer.valueOf(args[0]));
        int finalSize = this.collectionManager.getCollection().size();

        if (initialSize == finalSize)
            setResultExecution("No Dragons removed");
        else
            setResultExecution("A total of " + (initialSize - finalSize) + " were removed");
    }
}
