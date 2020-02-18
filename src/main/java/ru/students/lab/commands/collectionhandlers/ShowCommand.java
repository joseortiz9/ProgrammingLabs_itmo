package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;

public class ShowCommand implements ICommand {

    public static final String DESCRIPTION = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        this.collectionManager.getCollection().forEach((key, value) -> userInputHandler.printElemOfList("key:" + key + " -> " + value));
        userInputHandler.printLn(0,"show done");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
