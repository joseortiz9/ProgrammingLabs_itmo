package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

/**
 * Класс для выполнения и получения информации о функции сохранения коллекции в файл
 * @autor Хосе Ортис
 * @version 1.0
*/

public class ExportToFileCommand extends AbsCommand {

     public ExportToFileCommand() {
         commandKey = "export";
         description = "сохранить коллекцию в файл .xml.\nSyntax: export <file>";
     }

     @Override
     public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
         context.result().setLength(0);
         try {
             context.fileManager().SaveCollectionInXML(context.collectionManager().getCollection(), args[0]);
             context.result().append("All elems saved!");
         } catch (JAXBException e) {
             context.result().append("Converter error saving the data");
         } catch (InvalidPathException e) {
             context.result().append("Error finding the provided file");
         }
         return context.result().toString();
     }
}
