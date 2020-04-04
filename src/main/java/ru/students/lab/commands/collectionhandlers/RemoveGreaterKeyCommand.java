package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции удаления из коллекции элементов, ключ которых превышает заданный
 * @autor Хосе Ортис
 * @version 1.0
*/

public class RemoveGreaterKeyCommand extends AbsCommand {

    public static final String DESCRIPTION = "удалить из коллекции все элементы, ключ которых превышает заданный\nSyntax: remove_greater_key key";

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        return null;
    }

    /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {

        int initialSize = this.collectionManager.getCollection().size();
        this.collectionManager.removeGreaterKey(Integer.valueOf(args[0]));
        int finalSize = this.collectionManager.getCollection().size();

        if (initialSize == finalSize)
            userInputHandler.printLn(0,"No Dragons removed");
        else
            userInputHandler.printLn(0,"A total of " + (initialSize - finalSize) + " were removed");
    }*/

}
