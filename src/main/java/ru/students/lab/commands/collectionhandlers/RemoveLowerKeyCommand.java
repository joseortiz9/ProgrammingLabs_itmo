package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
 /** 
 * Класс для выполнения и получения информации о функции удаления из коллекции элементов, ключ которых меньше чем заданный
 * @autor Хосе Ортис
 * @version 1.0
*/
public class RemoveLowerKeyCommand implements ICommand {

    public static final String DESCRIPTION = "удалить из коллекции все элементы, ключ которых меньше, чем заданный.\nSyntax: remove_lower_key key";
    private CollectionManager collectionManager;
     /** 
     * Конструктор - создает объект класса RemoveLowerKeyCommand и экземпляр класса collectionManager для последущей работе с коллекцией
     * @see RemoveLowerKeyCommand#RemoveLowerKeyCommand(CollectionManager)
     */

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {

        int initialSize = this.collectionManager.getCollection().size();
        this.collectionManager.removeLowerKey(Integer.valueOf(args[0]));
        int finalSize = this.collectionManager.getCollection().size();

        if (initialSize == finalSize)
            userInputHandler.printLn(0,"No Dragons removed");
        else
            userInputHandler.printLn(0,"A total of " + (initialSize - finalSize) + " were removed");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
