package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции удаления из коллекции элементов, ключ которых превышает заданный
 * @autor Хосе Ортис
 * @version 1.0
*/

public class RemoveGreaterKeyCommand extends AbsCommand {

    public RemoveGreaterKeyCommand() {
        commandKey = "remove_greater_key";
        description = "удалить из коллекции все элементы, ключ которых превышает заданный\nSyntax: remove_greater_key key";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        context.result().setLength(0);
        int initialSize = context.collectionManager().getCollection().size();
        context.collectionManager().removeGreaterKey(Integer.valueOf(args[0]));
        int finalSize = context.collectionManager().getCollection().size();

        if (initialSize == finalSize)
            context.result().append("No Dragons removed");
        else
            context.result().append("A total of ").append(initialSize - finalSize).append(" were removed");
        return context.result().toString();
    }
}
