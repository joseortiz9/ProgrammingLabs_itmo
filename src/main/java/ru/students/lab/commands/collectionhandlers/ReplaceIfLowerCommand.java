package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

public class ReplaceIfLowerCommand implements ICommand {

    public static final String DESCRIPTION = "заменить значение по ключу, если новое значение меньше старого.\nSyntax: replace_if_lower key {element}";
    private CollectionManager collectionManager;
    private DragonFactory dragonFactory;

    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {

        Dragon newDragon = dragonFactory.generateDragonByInput(userInputHandler);

        // If it successfully replace it, returns the value of the old mapped object
        if (this.collectionManager.replaceIfLower(Integer.valueOf(args[0]), newDragon) != null)
            userInputHandler.printLn(0,newDragon.toString() + " replaced the young poor dragon!");
        else
            userInputHandler.printLn(1,"The key '" + Integer.valueOf(args[0]) + "' doesn't exist or is not old enough!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
