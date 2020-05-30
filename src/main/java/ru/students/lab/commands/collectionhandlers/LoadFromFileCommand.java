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
        StringBuilder sb = new StringBuilder();

        if (context.collectionController().credentialsNotExist(credentials))
            return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

        try {
            context.collectionManager().setCollection(context.fileManager().getCollectionFromFile(args[0]));
            sb.append("All elems added to the collection!");
        } catch (JAXBException e) {
            sb.append("Converter error adding the elems");
        } catch (InvalidPathException e) {
            sb.append("Error finding the provided file");
        }
        return sb.toString();
    }
}
