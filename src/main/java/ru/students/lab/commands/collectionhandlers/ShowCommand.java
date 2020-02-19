package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
 /** 
 * Класс для выполнения и получения информации о функции вывода всех элементов коллекции в строковом представлении
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ShowCommand implements ICommand {
    

    public static final String DESCRIPTION = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private CollectionManager collectionManager;
    /** 
     * Конструктор - создает объект класса ShowCommand и экземпляр класса collectionManager для последующей работы с коллекцией
     * @see ShowCommand#ShowCommand(CollectionManager)
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        this.collectionManager.getCollection().forEach((key, value) -> userInputHandler.printElemOfList("key:" + key + " -> " + value));
        userInputHandler.printLn(0, "Elements found: " + collectionManager.getCollection().size());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
