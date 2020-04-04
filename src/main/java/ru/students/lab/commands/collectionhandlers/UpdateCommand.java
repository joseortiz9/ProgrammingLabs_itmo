package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции замены элемента коллекции, id которого равен заданному
 * @autor Хосе Ортис
 * @version 1.0
 */
public class UpdateCommand extends AbsCommand {

    public static final String DESCRIPTION = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    private DragonFactory dragonFactory;

    /**
     * Конструктор - создает объект класса UpdateCommand и экземпляр класса collectionManager для последующей работы с коллекцией; создает экземпляр класса DragonFactory для создания экземпляра класса Dragon
     */
    public UpdateCommand() {
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        return null;
    }

    /*
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
    }*/

}
