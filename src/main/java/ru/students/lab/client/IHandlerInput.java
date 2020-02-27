package ru.students.lab.client;

import java.util.NoSuchElementException;

/**
 * Интерфейс, содержащий функции для получения входных данных и взаимодействия с пользователем 
 *
 * @autor Хосе Ортис
 * @version 1.0
*/

public interface IHandlerInput {
     /**
     * Функция получения данных с консоли
     * @return возвращает строку с данными из консоли
     */
    String read() throws NoSuchElementException;
     /**
     * Функция получения данных с консоли
     * @param s - строка, показывающая, ввод каких данных программа ожидает от пользователя
     * @return возвращает строку с данными из консоли
     */
    String readWithMessage(String s);
     /**
     * Функция вывода данных в консоль
     * @param s - строка, выводимая в консоль
     */
    void printLn(String s);
     /**
     * Функция вывода данных в консоль
     * @param code - переменная, показывающая, успешно ли завершилась предыдущая команда
     * @param s - строка, выводимая в консоль
     */
    void printLn(int code, String s);
     /**
     * Функция вывода элементов списка в консоль
     * @param s - элемент списка и строка, выводящаяся в консоль
     */
    void printElemOfList(String s);
}
