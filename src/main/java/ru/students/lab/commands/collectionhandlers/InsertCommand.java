package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.models.DragonCreator;

public class InsertCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}";
    private CollectionManager collectionManager;
    private DragonCreator dragonCreator;

    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonCreator = new DragonCreator();
    }

    @Override
    public void execute(String[] args) {
        Dragon newDragon = dragonCreator.generateDragon();

        // If it doesn't exist and it successfully put it, so it returns null
        if (this.collectionManager.insert(Integer.valueOf(args[0]), newDragon) == null)
            setResultExecution(newDragon.toString() + " Successfully saved!");
        else
            setResultExecution("The key '" + Integer.valueOf(args[0]) + "' already exist");
    }
}
