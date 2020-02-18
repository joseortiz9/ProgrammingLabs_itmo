package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class RemoveGreaterKeyCommand implements ICommand {

    public static final String DESCRIPTION = "удалить из коллекции все элементы, ключ которых превышает заданный\nSyntax: remove_greater_key key";
    private CollectionManager collectionManager;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {

        int initialSize = this.collectionManager.getCollection().size();
        this.collectionManager.removeGreaterKey(Integer.valueOf(args[0]));
        int finalSize = this.collectionManager.getCollection().size();

        if (initialSize == finalSize)
            userInputHandler.printLn(0,"No Dragons removed");
        else
            userInputHandler.printLn(0,"A total of " + (initialSize - finalSize) + " were removed");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
