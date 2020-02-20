package ru.students.lab.exceptions;
 /** 
 * Класс для исключений связанных с некорректным вводом данных в консоль
 * @autor Хосе Ортис
 * @version 1.0
*/
public class IncorrectInputException extends Exception {
  /** 
     * Конструктор - создает объект класса IncorrectInputException
     * @see IncorrectInputException#IncorrectInputException()
     */
    public IncorrectInputException() {
        super();
    }
     /** 
     * Конструктор - создает объект класса IncorrectInputException
     * @param s - сообщение об исключении
     * @see IncorrectInputException#IncorrectInputException(s)
     */
    public IncorrectInputException(String s) {
        super(s);
    }
}
