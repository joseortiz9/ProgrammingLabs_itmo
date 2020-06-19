package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции по ключу в случае если его новое значение меньше старого
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ReplaceIfLowerCommand extends AbsCommand {

    protected Dragon dragon = null;

    public ReplaceIfLowerCommand() {
        commandKey = "replace_if_lower";
        description = "заменить значение по ключу, если новое значение меньше старого.\nSyntax: replace_if_lower key {element}";
    }

    @Override
    public void addInput(Object dragon) {
        this.dragon = (Dragon) dragon;
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        String res = "";
        if (dragon == null)
            throw new DragonFormatException();

        int dragonID = context.collectionManager().isLowerAndGetID(Integer.valueOf(args[0]), dragon);
        if (dragonID > 0) {
            //AuthorizationException happens when the credentials passed are wrong and the user was already logged
            String resultDragonUpdated = "";
            try {
                resultDragonUpdated = context.DBRequestManager().updateDragon(dragonID, dragon, credentials, context.resourcesBundle());
            } catch (AuthorizationException ex) {
                return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
            }

            if (resultDragonUpdated == null) {
                context.collectionManager().replaceIfLower(Integer.valueOf(args[0]), dragon);
                res = MessageFormat.format(context.resourcesBundle().getString("server.response.command.removeiflower"), dragon.getName());
            } else
                res = resultDragonUpdated;
        } else
            res = context.resourcesBundle().getString("server.response.command.removeiflower.error.notlower");

        return res;
    }

    @Override
    public int requireInput() {
        return TYPE_INPUT_DRAGON;
    }
    @Override
    public Object getInput() {
        return dragon;
    }
}
