package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.models.DragonCreator;

public class ReplaceIfLowerCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "заменить значение по ключу, если новое значение меньше старого.\nSyntax: replace_if_lower key {element}";
    private CollectionManager collectionManager;
    private DragonCreator dragonCreator;

    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonCreator = new DragonCreator();
    }

    @Override
    public void execute(String[] args) {
        Dragon newDragon = dragonCreator.generateDragon();

        // If it successfully replace it, returns the value of the old mapped object
        if (this.collectionManager.replaceIfLower(Integer.valueOf(args[0]), newDragon) != null)
            setResultExecution(newDragon.toString() + " Successfully Replaced the young poor dragon!");
        else
            setResultExecution("The key '" + Integer.valueOf(args[0]) + "' doesn't exist or is not old enough!");
    }
}
