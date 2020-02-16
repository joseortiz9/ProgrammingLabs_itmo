package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.models.DragonCreator;

import java.util.Map;
import java.util.Optional;

public class UpdateCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "обновить значение элемента коллекции, id которого равен заданному.\nSyntax: update id {element}";
    private CollectionManager collectionManager;
    private DragonCreator dragonCreator;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.dragonCreator = new DragonCreator();
    }

    @Override
    public void execute(String[] args) {
        Dragon newDragon = dragonCreator.generateDragon();

        // If it successfully replace it, returns the value of the old mapped object
        if (this.collectionManager.update(Integer.valueOf(args[0]), newDragon) != null)
            setResultExecution(newDragon.toString() + " Successfully Updated!");
        else
            setResultExecution("The ID '" + Integer.valueOf(args[0]) + "' doesn't exist");
    }
}
