package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Класс для выполнения и получения информации о функции очистки коллекции
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClearCommand extends AbsCommand {

    public ClearCommand() {
        commandKey = "clear";
        description = "очистить коллекцию";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {

        //AuthorizationException happens when the credentials passed are wrong and the user was already logged
        String resDeletingAll = "";
        try {
            resDeletingAll = context.DBRequestManager().deleteAllDragons(credentials, context.resourcesBundle());
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        if (resDeletingAll == null) {
            context.collectionManager().clear();
            return context.resourcesBundle().getString("server.response.command.clear");
        } else
            return resDeletingAll;
    }
}
