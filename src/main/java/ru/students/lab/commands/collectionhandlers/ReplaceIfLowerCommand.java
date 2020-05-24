package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

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
        context.result().setLength(0);
        if (dragon == null)
            throw new DragonFormatException();

        int dragonID = context.collectionManager().isLowerAndGetID(Integer.valueOf(args[0]), dragon);
        if (dragonID > 0) {
            String resultDragonUpdated = context.collectionController().updateDragon(dragonID, dragon, credentials);

            if (resultDragonUpdated == null) {
                context.collectionManager().replaceIfLower(Integer.valueOf(args[0]), dragon);
                context.result().append(dragon.toString()).append(" replaced the young poor dragon!");
            } else
                context.result().append("Problems updating dragon: ").append(resultDragonUpdated);
        } else
            context.result().append("The given Dragon is not old enough! or the key is wrong!");

        return context.result().toString();
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
