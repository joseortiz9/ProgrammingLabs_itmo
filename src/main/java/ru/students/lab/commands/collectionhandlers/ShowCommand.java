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
        StringBuilder s = new StringBuilder();
        context.collectionManager().getCollection().forEach((key, value) -> s.append("key:").append(key).append(" -> ").append(value).append("\n"));
        s.append("Elements found: ").append(context.collectionManager().getCollection().size());
        return s.toString();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
