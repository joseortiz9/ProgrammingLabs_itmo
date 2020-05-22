package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции очистки коллекции
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClearCommand extends AbsCommand {

    public ClearCommand() {
        commandKey = "clear";
        description = "очистить коллекцию";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        context.collectionManager().clear();
        return "All elems deleted successfully!";
    }
}
