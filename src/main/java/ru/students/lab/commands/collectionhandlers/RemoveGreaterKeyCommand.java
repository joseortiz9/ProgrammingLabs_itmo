package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class RemoveGreaterKeyCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "удалить из коллекции все элементы, ключ которых превышает заданный\nSyntax: remove_greater_key key";
    private CollectionManager collectionManager;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            int initialSize = this.collectionManager.getCollection().size();
            this.collectionManager.removeGreaterKey(Integer.valueOf(args[0]));
            int finalSize = this.collectionManager.getCollection().size();

            if (initialSize == finalSize)
                setResultExecution(0,"No Dragons removed");
            else
                setResultExecution(0,"A total of " + (initialSize - finalSize) + " were removed");
        } catch (NumberFormatException ex) {
            setResultExecution(1,"Incorrect format of the entered key");
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
