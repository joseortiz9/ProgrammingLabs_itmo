package ru.students.lab.commands;

import java.io.IOException;
/** 
 * Интерфейс, содержащий функции для работы с командами
 * @autor Хосе Ортис
 * @version 1.0
*/

public interface ICommand {
     /**
     * Функция для выполнения команды по работе с коллекцией
     */
    Object execute(ExecutionContext context) throws IOException;
}
