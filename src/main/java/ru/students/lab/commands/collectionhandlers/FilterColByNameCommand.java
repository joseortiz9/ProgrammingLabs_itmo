package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.util.ListEntrySerializable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
 /** 
 * Класс для выполнения и получения информации о функции поиска экземпляров класса Dragon, содержащих в поле name данную подстроку
 * @autor Хосе Ортис
 * @version 1.0
*/

public class FilterColByNameCommand extends AbsCommand {

    public final String description = "вывести элементы, значение поля name которых содержит заданную.\nSyntax: filter_contains_name name";

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         List<ListEntrySerializable> filteredCol = context.collectionManager().filterContainsName(args[0]);
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
