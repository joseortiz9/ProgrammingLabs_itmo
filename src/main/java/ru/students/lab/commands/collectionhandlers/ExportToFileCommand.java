package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.text.MessageFormat;

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
         String result = "";

         if (context.DBRequestManager().credentialsNotExist(credentials))
             return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

         try {
             context.fileManager().SaveCollectionInXML(context.collectionManager().getCollection(), args[0]);
             result = MessageFormat.format(context.resourcesBundle().getString("server.response.command.export"), args[0]);
         } catch (JAXBException e) {
             result = context.resourcesBundle().getString("server.response.command.export.error.saving");
         } catch (InvalidPathException e) {
             result = context.resourcesBundle().getString("server.response.command.export.error.path");
         }
         return result;
     }
}
