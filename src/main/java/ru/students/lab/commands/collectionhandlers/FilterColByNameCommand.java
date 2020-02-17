package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;

import java.util.List;
import java.util.Map;

public class FilterColByNameCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы, значение поля name которых содержит заданную.\nSyntax: filter_contains_name name";
    private CollectionManager collectionManager;

    public FilterColByNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        List<Map.Entry<Integer, Dragon>> filteredCol = this.collectionManager.filterContainsName(args[0]);
        if (filteredCol.isEmpty())
            setResultExecution(0,"No elements found.");
        else {
            filteredCol.forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
            setResultExecution(0, "Elements found: " + filteredCol.size());
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
