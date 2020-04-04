package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;

import java.io.IOException;
 /** 
 * Класс для выполнения и получения информации о функции сохранения коллекции в файл
 * @autor Хосе Ортис
 * @version 1.0
*/

public class SaveColCommand extends AbsCommand {

    public final String description = "сохранить коллекцию в файл";
    private CollectionManager collectionManager;
    private FileManager fileManager;

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         return null;
     }

     /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws IOException {
        this.fileManager.SaveCollectionInXML(this.collectionManager.getCollection());
        userInputHandler.printLn(0,"All elems saved!");
    }*/

}
