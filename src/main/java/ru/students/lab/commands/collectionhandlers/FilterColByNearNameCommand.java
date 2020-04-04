package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;

import java.io.IOException;
import java.util.List;
import java.util.Map;
 /** 
 * Класс для выполнения и получения информации о функции получения экземпляров класса Dragon, у которых значение поля name начинается с заданной подстроки
 * @autor Хосе Ортис
 * @version 1.0
*/

public class FilterColByNearNameCommand extends AbsCommand {

    public final String description = "вывести элементы, значение поля name которых начинается с заданной подстроки.\nSyntax: filter_starts_with_name name";

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         context.result().setLength(0);
         List<Map.Entry<Integer, Dragon>> filteredCol = context.collectionManager().filterStartsWithName(args[0]);
         if (filteredCol.isEmpty())
             context.result().append("No elements found.");
         else {
             filteredCol.forEach(e -> context.result().append("key:").append(e.getKey()).append(" -> ").append(e.getValue().toString()).append("\n"));
             context.result().append("Elements found: ").append(filteredCol.size());
         }
         return context.result().toString();
     }

     @Override
     public String getDescription() {
         return description;
     }
 }
