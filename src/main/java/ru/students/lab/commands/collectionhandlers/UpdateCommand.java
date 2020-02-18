package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

public class UpdateCommand implements ICommand {

    public static final String DESCRIPTION = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    private CollectionManager collectionManager;
    private DragonFactory dragonFactory;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {
        Dragon newDragon = dragonFactory.generateDragonFromConsole();

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
