package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;

public class SaveColCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "сохранить коллекцию в файл";
    private CollectionManager collectionManager;
    private FileManager fileManager;

    public SaveColCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        this.fileManager.SaveCollectionInXML(this.collectionManager.getCollection());
        setResultExecution(0,"All elems saved successfully!");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
