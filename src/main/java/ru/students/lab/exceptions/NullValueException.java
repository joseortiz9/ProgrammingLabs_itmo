package ru.students.lab.exceptions;
 /** 
 * Класс для исключений связанных с недопустимым значением поля экземпляра класса
 * @autor Хосе Ортис
 * @version 1.0
*/

public class NullValueException extends Exception {
     /** 
     * Конструктор - создает объект класса NullValueException
     * @see NullValueException#NullValueException()
     */
    public NullValueException() {
        super("Can not be null!");
    }
     /** 
     * Конструктор - создает объект класса NullValueException
     * @param str - строка с названием поля с недопустимым значением
     * @see NullValueException#NullValueException()
     */
    public NullValueException(String str) {
        super(str + " can not be empty!");
    }
}
