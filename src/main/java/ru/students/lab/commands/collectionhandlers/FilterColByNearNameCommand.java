package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class FilterColByNearNameCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name";
    private CollectionManager collectionManager;

    public FilterColByNearNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.collectionManager.filterStartsWithName(args[0])
                .forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
}
