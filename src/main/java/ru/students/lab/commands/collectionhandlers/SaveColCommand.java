package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;

import java.io.IOException;

public class SaveColCommand implements ICommand {

    public static final String DESCRIPTION = "сохранить коллекцию в файл";
    private CollectionManager collectionManager;
    private FileManager fileManager;

    public SaveColCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws IOException {
        this.fileManager.SaveCollectionInXML(this.collectionManager.getCollection());
        userInputHandler.printLn(0,"All elems saved!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
