package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Класс для выполнения и получения информации о функции удаления элемента из коллекции по его ключу
 * @autor Хосе Ортис
 * @version 1.0
*/
public class RemoveKeyCommand extends AbsCommand {

    public RemoveKeyCommand() {
        commandKey = "remove_key";
        description = "удалить элемент из коллекции по его ключу.\nSyntax: remove_key key";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        String res = "";

        //AuthorizationException happens when the credentials passed are wrong and the user was already logged
        String resultDeletedByKey = "";
        try {
            resultDeletedByKey = context.DBRequestManager().deleteDragon(Integer.parseInt(args[0]), credentials, context.resourcesBundle());
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        // If it successfully replace it, returns the value of the old mapped object
        if (resultDeletedByKey == null) {
            if (context.collectionManager().removeKey(Integer.valueOf(args[0])) != null)
                res = MessageFormat.format(context.resourcesBundle().getString("server.response.command.removebykey"), args[0]);
        } else
            res = resultDeletedByKey;

        return res;
    }
}
