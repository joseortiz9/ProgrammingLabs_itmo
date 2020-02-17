package ru.students.lab.exceptions;

public class NullValueException extends Exception {
    public NullValueException() {
        super("Can not be null!");
    }

    public NullValueException(String str) {
        super(str + " can not be empty!");
    }
}
