package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.util.ListEntrySerializable;

import java.io.IOException;
import java.util.List;

/**
 * Класс для выполнения и получения информации о функции получения экземпляров класса Dragon, у которых значение поля name начинается с заданной подстроки
 * @autor Хосе Ортис
 * @version 1.0
*/

public class FilterColByNearNameCommand extends AbsCommand {

    public final String description = "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name";

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         List<ListEntrySerializable> filteredCol = context.collectionManager().filterStartsWithName(args[0]);
         if (filteredCol.isEmpty())
             return "No elements found.";
         else
             return filteredCol;
     }

     @Override
     public String getDescription() {
         return description;
     }
 }
