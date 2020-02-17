package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class ClearCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "очистить коллекцию";
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.collectionManager.clear();
        setResultExecution(0,"All elems deleted successfully!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
