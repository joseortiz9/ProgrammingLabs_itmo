package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

/**
 * Класс для выполнения и получения информации о функции вывода всех элементов коллекции в строковом представлении
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ShowCommand extends AbsCommand {

    public ShowCommand() {
        commandKey = "show";
        description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) {
        return context.collectionManager().getSerializableList();
    }
}
