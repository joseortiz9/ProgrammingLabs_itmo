package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class ClearCommand implements ICommand {
 /** 
 * Класс для выполнения и получения информации о функции очистки коллекции
 * @autor Хосе Ортис
 * @version 1.0
*/

    public static final String DESCRIPTION = "очистить коллекцию";
    private CollectionManager collectionManager;
    /** 
     * Конструктор - создает объект класса clearCommand и экземпляр класса collectionManager для последущей работе с коллекцией
     * @see ClearCommand#ClearCommand(CollectionManager)
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        this.collectionManager.clear();
        userInputHandler.printLn(0,"All elems deleted successfully!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
