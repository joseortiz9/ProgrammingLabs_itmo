package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции удаления элемента из коллекции по его ключу
 * @autor Хосе Ортис
 * @version 1.0
*/
public class RemoveKeyCommand extends AbsCommand {

    public RemoveKeyCommand() {
        commandKey = "remove_key";
        description = "удалить элемент из коллекции по его ключу.\nSyntax: remove_key key";
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        context.result().setLength(0);
        if (context.collectionManager().removeKey(Integer.valueOf(args[0])) != null)
            context.result().append("k:").append(args[0]).append(" Successfully removed!");
        else
            context.result().append("The key '").append(args[0]).append("' doesn't exist");
        return context.result().toString();
    }
}
