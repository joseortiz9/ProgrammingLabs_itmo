package ru.students.lab.commands;

/**
 * Класс для выполнения и получения информации о функции вывода информации о коллекции
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InfoCommand extends AbsCommand {

    public final String description = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";

    @Override
    public Object execute(ExecutionContext context) {
        return context.collectionManager().toString();
    }

     @Override
     public String getDescription() {
         return description;
     }
 }
