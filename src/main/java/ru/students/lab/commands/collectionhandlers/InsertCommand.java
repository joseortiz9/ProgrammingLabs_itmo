package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

public class InsertCommand implements ICommand {

    public static final String DESCRIPTION = "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}";
    private CollectionManager collectionManager;
    private DragonFactory dragonFactory;

    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {
        if (this.collectionManager.getCollection().containsKey(Integer.valueOf(args[0]))) {
            userInputHandler.printLn(1,"The key '" + Integer.valueOf(args[0]) + "' already exist");
            return;
        }

        Dragon newDragon = dragonFactory.generateDragonFromConsole();
        // If it doesn't exist and it successfully put it, so it returns null
        if (this.collectionManager.insert(Integer.valueOf(args[0]), newDragon) == null)
            userInputHandler.printLn(0,newDragon.toString() + " saved!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
