package ru.students.lab.commands;

import ru.students.lab.database.Credentials;

/**
 * Класс для выполнения и получения информации о функции вывода информации о коллекции
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InfoCommand extends AbsCommand {

    public InfoCommand() {
        commandKey = "info";
        description = "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) {
        return context.collectionManager().toString();
    }
 }
