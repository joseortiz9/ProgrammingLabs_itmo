package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции, id которого равен заданному
 * @autor Хосе Ортис
 * @version 1.0
 */
public class UpdateCommand extends AbsCommand {

    protected Dragon dragon = null;

    public UpdateCommand() {
        commandKey = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    }

    @Override
    public void addInput(Object dragon) {
        this.dragon = (Dragon) dragon;
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        StringBuilder sb = new StringBuilder();

        if (dragon == null)
            throw new DragonFormatException();

        String dragonIDaddedToDB = context.collectionController().updateDragon(Integer.parseInt(args[0]), dragon, credentials);

        // If it successfully replace it, returns the value of the old mapped object
        if (dragonIDaddedToDB == null) {
            if (context.collectionManager().update(Integer.valueOf(args[0]), dragon) != null)
                sb.append(dragon.toString()).append(" Updated!");
        } else
            sb.append("Problems updating dragon: ").append(dragonIDaddedToDB);

        return sb.toString();
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
