package ru.students.lab.exceptions;
 /** 
 * Класс для исключений связанных с отсутствием вводимой команды
 * @autor Хосе Ортис
 * @version 1.0
*/

public class NoSuchCommandException extends RuntimeException {
     /** 
     * Конструктор - создает объект класса NoSuchCommandException
     * @param s - сообщение об исключении
     * @see NoSuchCommandException#NoSuchCommandException(s)
     */
    public NoSuchCommandException(String s) {
        super(s);
    }
}
