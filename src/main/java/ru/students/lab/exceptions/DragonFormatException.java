package ru.students.lab.exceptions;

public class DragonFormatException extends RuntimeException {
    public DragonFormatException() {
        super("That Dragon has format problems!");
    }
}
