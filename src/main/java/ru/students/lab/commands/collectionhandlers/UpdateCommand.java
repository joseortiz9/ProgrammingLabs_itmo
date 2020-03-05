package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

public class UpdateCommand implements ICommand {
     /** 
 * Класс для выполнения и получения информации о функции замены элемента коллекции, id которого равен заданному
 * @autor Хосе Ортис
 * @version 1.0
*/

    public static final String DESCRIPTION = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    private CollectionManager collectionManager;
    private DragonFactory dragonFactory;
     /** 
     * Конструктор - создает объект класса UpdateCommand и экземпляр класса collectionManager для последующей работы с коллекцией; создает экземпляр класса DragonFactory для создания экземпляра класса Dragon
     * @see UpdateCommand#UpdateCommand(CollectionManager)
     */

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {
        if (args.length == 0)
            throw new ArrayIndexOutOfBoundsException();

        Dragon newDragon = (userInputHandler.isInteractive()) ?
                dragonFactory.generateDragonByInput(userInputHandler) :
                dragonFactory.generateFromScript(userInputHandler);

        if (newDragon == null) {
            userInputHandler.printLn(1,"That Dragon has format problems!");
            return;
        }

        // If it successfully replace it, returns the value of the old mapped object
        if (this.collectionManager.update(Integer.valueOf(args[0]), newDragon) != null)
            userInputHandler.printLn(0,newDragon.toString() + " Updated!");
        else
            userInputHandler.printLn(1,"The ID '" + Integer.valueOf(args[0]) + "' doesn't exist");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
