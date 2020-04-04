package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
 /** 
 * Класс для выполнения и получения информации о функции вывода всех элементов коллекции в строковом представлении
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ShowCommand extends AbsCommand {

    public final String description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    @Override
    public Object execute(ExecutionContext context) {
        context.result().setLength(0);
        context.collectionManager().getCollection().forEach((key, value) -> context.result().append("key:").append(key).append(" -> ").append(value).append("\n"));
        context.result().append("Elements found: ").append(context.collectionManager().getCollection().size());
        return context.result().toString();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
