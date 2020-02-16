package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

public class InsertCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}";
    private CollectionManager collectionManager;
    private DragonFactory dragonFactory;

    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void execute(String[] args) {
        if (this.collectionManager.getCollection().containsKey(Integer.valueOf(args[0]))) {
            setResultExecution("The key '" + Integer.valueOf(args[0]) + "' already exist");
            return;
        }

        Dragon newDragon = dragonFactory.generateDragonFromConsole();
        // If it doesn't exist and it successfully put it, so it returns null
        if (this.collectionManager.insert(Integer.valueOf(args[0]), newDragon) == null)
            setResultExecution(newDragon.toString() + " Successfully saved!");
    }
}
