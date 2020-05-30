package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.util.ListEntrySerializable;

import java.io.IOException;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции поиска экземпляров класса Dragon, содержащих в поле name данную подстроку
 * @autor Хосе Ортис
 * @version 1.0
*/

public class FilterColByNameCommand extends AbsCommand {

    public FilterColByNameCommand() {
        commandKey = "filter_contains_name";
        description = "вывести элементы, значение поля name которых содержит заданную.\nSyntax: filter_contains_name name";
    }

     @Override
     public Object execute(ExecutionContext context, Credentials credentials) throws IOException {

         if (context.collectionController().credentialsNotExist(credentials))
             return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

         List<ListEntrySerializable> filteredCol = context.collectionManager().filterContainsName(args[0]);
         if (filteredCol.isEmpty())
             return "No elements found.";
         else
             return filteredCol;
     }
 }
