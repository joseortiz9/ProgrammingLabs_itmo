package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;

public class FetchMapCommand extends AbsCommand {
    public FetchMapCommand() {
        commandKey = "fetch_map_elements";
        description = "Get dragons with usersID to print in the map";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) {
        //AuthorizationException happens when the credentials passed are wrong and the user was already logged
        Object collectionResult;
        try {
            collectionResult = context.collectionController().fetchCollectionWithUser(credentials);
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        return collectionResult;
    }
}
