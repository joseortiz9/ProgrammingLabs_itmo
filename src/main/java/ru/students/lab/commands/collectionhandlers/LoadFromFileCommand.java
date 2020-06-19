package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class LoadFromFileCommand extends AbsCommand {

    public LoadFromFileCommand() {
        commandKey = "load";
        description = "Add elements from a .xml file.\nSyntax: load <file>";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        String result = "";

        if (context.DBRequestManager().credentialsNotExist(credentials))
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

        try {
            context.collectionManager().setCollection(context.fileManager().getCollectionFromFile(args[0]));
            result = context.resourcesBundle().getString("server.response.command.import");
        } catch (JAXBException e) {
            result = context.resourcesBundle().getString("server.response.command.export.error.saving");
        } catch (InvalidPathException e) {
            result = context.resourcesBundle().getString("server.response.command.export.error.path");
        }
        return result;
    }
}
