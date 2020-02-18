package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;

import java.util.List;
import java.util.Map;

public class FilterColByNearNameCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name";
    private CollectionManager collectionManager;

    public FilterColByNearNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        List<Map.Entry<Integer, Dragon>> filteredCol = this.collectionManager.filterStartsWithName(args[0]);
        if (filteredCol.isEmpty())
            userInputHandler.printLn(0,"No elements found.");
        else {
            filteredCol.forEach(e -> userInputHandler.printElemOfList("key:" + e.getKey() + " -> " + e.getValue().toString()));
            userInputHandler.printLn(0, "Elements found: " + filteredCol.size());
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
