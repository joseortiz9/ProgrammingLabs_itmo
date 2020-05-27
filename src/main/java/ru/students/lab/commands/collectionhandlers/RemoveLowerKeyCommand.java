package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import java.io.IOException;
import java.sql.SQLException;

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
        StringBuilder sb = new StringBuilder();
        int initialSize = context.collectionManager().getCollection().size();

        String resultDeletedByKey = "";
        int[] deletedIDs = null;
        try {
            deletedIDs = context.collectionController().deleteDragonsLowerThanKey(Integer.parseInt(args[0]), credentials);
        } catch (SQLException ex) {
            resultDeletedByKey = ex.getMessage();
        }

        if (deletedIDs != null)
            context.collectionManager().removeOnKey(deletedIDs);
        else
            sb.append("Problems deleting dragons: ").append(resultDeletedByKey);

        int finalSize = context.collectionManager().getCollection().size();

        if (initialSize == finalSize)
            sb.append("\nNo Dragons removed");
        else
            sb.append("A total of ").append(initialSize - finalSize).append(" were removed");
        return sb.toString();
    }
}
