package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.MessageFormat;

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
            deletedIDs = context.DBRequestManager().deleteDragonsLowerThanKey(Integer.parseInt(args[0]), credentials);
        } catch (SQLException | NoSuchAlgorithmException ex) {
            resultDeletedByKey = context.DBRequestManager().getSQLErrorString("remove dragons lower than key", context.resourcesBundle(), ex);
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        if (deletedIDs != null)
            context.collectionManager().removeOnKey(deletedIDs);
        else
            sb.append(resultDeletedByKey);

        int finalSize = context.collectionManager().getCollection().size();
        if (initialSize == finalSize)
            sb.append(context.resourcesBundle().getString("server.response.command.remove.noremoved"));
        else
            sb.append(MessageFormat.format(context.resourcesBundle().getString("server.response.command.remove.total.removed"), (initialSize - finalSize)));

        return sb.toString();
    }
}
