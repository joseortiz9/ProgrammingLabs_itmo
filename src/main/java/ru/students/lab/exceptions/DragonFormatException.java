package ru.students.lab.exceptions;
/**
 * Класс для исключений связанных с неправильным форматом дракона
 * @autor Хосе Ортис
 * @version 1.0
 */
public class DragonFormatException extends RuntimeException {
    public DragonFormatException() {
        super("That Dragon has format problems!");
    }
}
