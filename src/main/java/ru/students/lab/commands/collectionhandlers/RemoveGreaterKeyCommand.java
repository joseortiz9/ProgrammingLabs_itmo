package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
        StringBuilder sb = new StringBuilder();
        int initialSize = context.collectionManager().getCollection().size();

        String resultDeletedByKey = "";
        int[] deletedIDs = null;
        try {
            deletedIDs = context.collectionController().deleteDragonsGreaterThanKey(Integer.parseInt(args[0]), credentials);
        } catch (SQLException | NoSuchAlgorithmException ex) {
            resultDeletedByKey = ex.getMessage();
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        if (deletedIDs != null)
            context.collectionManager().removeOnKey(deletedIDs);
        else
            sb.append("Problems deleting dragons: ").append(resultDeletedByKey);

        int finalSize = context.collectionManager().getCollection().size();

        if (initialSize == finalSize)
            sb.append("No Dragons removed");
        else
            sb.append("A total of ").append(initialSize - finalSize).append(" dragons were removed");
        return sb.toString();
    }
}
