package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;
import ru.students.lab.exceptions.DragonFormatException;
import ru.students.lab.models.Dragon;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции добавления в коллекцию элемента с заданным ключем
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InsertCommand extends AbsCommand {

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
        StringBuilder sb = new StringBuilder();

        if (context.collectionManager().getCollection().containsKey(Integer.valueOf(args[0]))) {
            sb.append("The key '").append(Integer.valueOf(args[0])).append("' already exist");
            return sb.toString();
        }

        if (dragon == null)
            throw new DragonFormatException();

        //AuthorizationException happens when the credentials passed are wrong and the user was already logged
        String dragonIDaddedToDB = "";
        try {
            dragonIDaddedToDB = context.DBRequestManager().addDragon(Integer.parseInt(args[0]), dragon, credentials);
        } catch (AuthorizationException ex) {
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");
        }

        //If the resulted from the db_execution is null and If it doesn't exist and it successfully put it, so it returns null
        if (isNumeric(dragonIDaddedToDB)) {
            dragon.setId(Integer.valueOf(dragonIDaddedToDB));
            if (context.collectionManager().insert(Integer.valueOf(args[0]), dragon) == null)
                sb.append("Dragon of ID: ").append(dragon.getId()).append(" successfully saved!");
        } else
            sb.append("Error saving the Dragon: ").append(dragonIDaddedToDB);
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
