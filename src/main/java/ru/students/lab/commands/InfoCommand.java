package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.managers.CollectionManager;
 /** 
 * Класс для выполнения и получения информации о функции вывода информации о коллекции
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InfoCommand implements ICommand {

    public static final String DESCRIPTION = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    private CollectionManager collectionManager;
    /** 
     * Конструктор - создает объект класса InfoCommand и экземпляр класса collectionManager для последующей работы с коллекцией
     * @see InfoCommand#InfoCommand(CollectionManager)
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        userInputHandler.printLn(this.collectionManager.toString());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
