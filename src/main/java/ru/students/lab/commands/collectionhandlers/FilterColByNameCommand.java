package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class FilterColByNameCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы, значение поля name которых содержит заданную.\nSyntax: filter_contains_name name";
    private CollectionManager collectionManager;

    public FilterColByNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.collectionManager.filterContainsName(args[0])
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
}
