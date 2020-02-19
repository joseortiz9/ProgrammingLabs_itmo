package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.io.IOException;
/** 
 * Интерфейс, содержащий функции для работы с командами
 * @autor Хосе Ортис
 * @version 1.0
*/

public interface ICommand {
    String DESCRIPTION = "No Description";
     /**
     * Функция для выполнения команды по работе с коллекцией
     * @param userInputHandler - экземпляр интерфейса для вывода информации в консоль
     * @param args - массив, с входными данными для передачи выполняемой команде
     */
    void execute(IHandlerInput userInputHandler, String[] args) throws IOException;
     /**
     * Функция для получения описания выполняемой команды
     * @return возвращает описание команды
     */
    String getDescription();
}
