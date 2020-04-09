package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции, id которого равен заданному
 * @autor Хосе Ортис
 * @version 1.0
 */
public class UpdateCommand extends AbsCommand {

    protected boolean requireInputs = true;
    protected Dragon dragon = null;

    public UpdateCommand() {
        commandKey = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
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
        if (context.collectionManager().update(Integer.valueOf(args[0]), dragon) != null)
            context.result().append(dragon.toString()).append(" Updated!");
        else
            context.result().append("The ID '").append(Integer.valueOf(args[0])).append("' doesn't exist");

        return context.result().toString();
    }

    @Override
    public boolean requireDragonInput() {
        return requireInputs;
    }
    @Override
    public Dragon getDragon() {
        return dragon;
    }

}
