package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции удаления из коллекции элементов, ключ которых меньше чем заданный
 * @autor Хосе Ортис
 * @version 1.0
*/
public class RemoveLowerKeyCommand extends AbsCommand {

    public RemoveLowerKeyCommand() {
        commandKey = "remove_lower_key";
        description = "удалить из коллекции все элементы, ключ которых меньше, чем заданный.\nSyntax: remove_lower_key key";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        context.result().setLength(0);
        int initialSize = context.collectionManager().getCollection().size();
        context.collectionManager().removeLowerKey(Integer.valueOf(args[0]));
        int finalSize = context.collectionManager().getCollection().size();

        if (initialSize == finalSize)
            context.result().append("No Dragons removed");
        else
            context.result().append("A total of ").append(initialSize - finalSize).append(" were removed");
        return context.result().toString();
    }
}
