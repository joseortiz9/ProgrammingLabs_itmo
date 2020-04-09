package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции по ключу в случае если его новое значение меньше старого
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ReplaceIfLowerCommand extends AbsCommand {

    protected boolean requireInputs = true;
    protected Dragon dragon = null;

    public ReplaceIfLowerCommand() {
        commandKey = "replace_if_lower";
        description = "заменить значение по ключу, если новое значение меньше старого.\nSyntax: replace_if_lower key {element}";
    }

    @Override
    public void addDragonInput(Dragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        context.result().setLength(0);
        if (dragon == null)
            throw new DragonFormatException();

        // If it successfully replace it, returns the value of the old mapped object
        if (context.collectionManager().replaceIfLower(Integer.valueOf(args[0]), dragon) != null)
            context.result().append(dragon.toString()).append(" replaced the young poor dragon!");
        else
            context.result().append("The key '").append(Integer.valueOf(args[0])).append("' doesn't exist or is not old enough!");
        return context.result().toString();
    }

    @Override
    public boolean requireDragonInput() {
        return requireInputs;
    }
}
