package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import java.io.IOException;
import java.sql.SQLException;

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

        String resultDeletedByKey = "";
        int[] deletedIDs = null;
        try {
            deletedIDs = context.collectionController().deleteDragonsGreaterThanKey(Integer.parseInt(args[0]), credentials);
        } catch (SQLException ex) {
            resultDeletedByKey = ex.getMessage();
        }


        if (deletedIDs != null) {
            context.collectionManager().removeGreaterKey(deletedIDs);
        } else
            context.result().append("Problems deleting dragons: ").append(resultDeletedByKey);

        int finalSize = context.collectionManager().getCollection().size();

        if (initialSize == finalSize)
            context.result().append("\nNo Dragons removed");
        else
            context.result().append("A total of ").append(initialSize - finalSize).append(" were removed");
        return context.result().toString();
    }
}
