package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции очистки коллекции
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClearCommand extends AbsCommand {

    public final String description = "очистить коллекцию";

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        context.collectionManager().clear();
        return "All elems deleted successfully!";
    }

    @Override
    public String getDescription() {
        return description;
    }
}
