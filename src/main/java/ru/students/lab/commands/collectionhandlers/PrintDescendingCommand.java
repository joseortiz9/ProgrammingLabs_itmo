package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.util.DragonEntrySerializable;

import java.io.IOException;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции вывода элементов коллекции в порядке убывания
 * @autor Хосе Ортис
 * @version 1.0
*/
public class PrintDescendingCommand extends AbsCommand {

    public PrintDescendingCommand() {
        commandKey = "print_descending";
        description = "вывести элементы коллекции в порядке убывания.\nSyntax: print_descending -{k/i/n/d} где: -k=key / -i=id / -n=name / -d=creation_date";
    }

     @Override
     public Object execute(ExecutionContext context, Credentials credentials) throws IOException {

         if (context.DBRequestManager().credentialsNotExist(credentials))
             return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

         String res = "";
         List<DragonEntrySerializable> sortedDragons = null;

         switch (args[0]) {
             case "":
             case "-k":
                 //System.out.println("Sorting by key...");
                 sortedDragons = context.collectionManager().sortByKey();
                 break;
             case "-i":
                 //System.out.println("Sorting by ID...");
                 sortedDragons = context.collectionManager().sortById();
                 break;
             case "-n":
                 //System.out.println("Sorting by Name...");
                 sortedDragons = context.collectionManager().sortByName();
                 break;
             case "-d":
                 //System.out.println("Sorting by Creation Date...");
                 sortedDragons = context.collectionManager().sortByCreationDate();
                 break;
             default:
                 res = context.resourcesBundle().getString("server.response.command.printdescending.error.options");
         }
         if (sortedDragons != null)
             return sortedDragons;
         return res;
     }

     @Override
     public String getDescription() {
         return description;
     }
 }
