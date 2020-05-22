package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции добавления в коллекцию элемента с заданным ключем
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InsertCommand extends AbsCommand {

    protected boolean requireInputs = true;
    protected Dragon dragon = null;

    public InsertCommand() {
        commandKey = "insert";
        description = "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}\n"
                +"Rules:\n"
                +"coord{X}    [should be more than -328]\n"
                +"age         [should be more than 0]\n"
                +"head{#Eyes} [empty or more than 0]";
    }

    @Override
    public void addInput(Object dragon) {
        this.dragon = (Dragon) dragon;
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        context.result().setLength(0);

        if (context.collectionManager().getCollection().containsKey(Integer.valueOf(args[0]))) {
            context.result().append("The key '").append(Integer.valueOf(args[0])).append("' already exist");
            return context.result().toString();
        }

        if (dragon == null)
            throw new DragonFormatException();

        // If it doesn't exist and it successfully put it, so it returns null
        if (context.collectionManager().insert(Integer.valueOf(args[0]), dragon) == null)
            context.result().append(dragon.toString()).append(" saved!");
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
