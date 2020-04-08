package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;

/**
 * Класс для выполнения и получения информации о функции вывода всех элементов коллекции в строковом представлении
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ShowCommand extends AbsCommand {

    public final String description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    @Override
    public Object execute(ExecutionContext context) {
        return context.collectionManager().getSerializableList();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
