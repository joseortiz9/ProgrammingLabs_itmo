package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class ShowCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.collectionManager.getCollection().forEach((key, value) -> System.out.println("key:" + key + " -> " + value));
    }
}
