package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.util.DragonEntrySerializable;

import java.io.IOException;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции получения экземпляров класса Dragon, у которых значение поля name начинается с заданной подстроки
 * @autor Хосе Ортис
 * @version 1.0
*/

public class FilterColByNearNameCommand extends AbsCommand {

    public FilterColByNearNameCommand() {
        commandKey = "filter_starts_with_name";
        description = "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name";
    }

     @Override
     public Object execute(ExecutionContext context, Credentials credentials) throws IOException {

         if (context.DBRequestManager().credentialsNotExist(credentials))
             return new Credentials(-1, UserModel.DEFAULT_USERNAME, "");

         List<DragonEntrySerializable> filteredCol = context.collectionManager().filterStartsWithName(args[0]);
         if (filteredCol.isEmpty())
             return context.resourcesBundle().getString("server.response.command.filter.list.error.empty");
         else
             return filteredCol;
     }
 }
