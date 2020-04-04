package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции, id которого равен заданному
 * @autor Хосе Ортис
 * @version 1.0
 */
public class UpdateCommand extends AbsCommand {

    public static final String DESCRIPTION = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    protected boolean requireInputs = true;
    protected Dragon dragon = null;

    @Override
    public void addDragonInput(Dragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        context.result().setLength(0);
        /*Dragon newDragon = (userInputHandler.isInteractive()) ?
                dragonFactory.generateDragonByInput(userInputHandler) :
                dragonFactory.generateFromScript(userInputHandler);*/

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
    public String getDescription() {
        return description;
    }

    @Override
    public boolean requireDragonInput() {
        return requireInputs;
    }

}
