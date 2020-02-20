package ru.students.lab.exceptions;

import java.io.IOException;
 /** 
 * Класс для исключений связанных с отсутствием данных в файле
 * @autor Хосе Ортис
 * @version 1.0
*/
public class EmptyFileException extends IOException {
     /** 
     * Конструктор - создает объект класса EmptyFileException
     * @param s - сообщение об исключении
     * @see EmptyFileException#EmptyFileException(s)
     */
    public EmptyFileException(String s) {
        super(s);
    }
}
