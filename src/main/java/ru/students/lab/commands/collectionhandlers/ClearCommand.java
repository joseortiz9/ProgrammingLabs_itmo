package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

import java.io.IOException;

public class ClearCommand extends AbsCommand {
 /** 
 * Класс для выполнения и получения информации о функции очистки коллекции
 * @autor Хосе Ортис
 * @version 1.0
*/

    public static final String DESCRIPTION = "очистить коллекцию";

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        /*this.collectionManager.clear();
        userInputHandler.printLn(0,"All elems deleted successfully!");*/
        return null;
    }
}
