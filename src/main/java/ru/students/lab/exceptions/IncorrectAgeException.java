package ru.students.lab.exceptions;

public class IncorrectAgeException extends Exception {
    public IncorrectAgeException() {
        super();
    }
    public IncorrectAgeException(String s) {
        super(s);
    }
}
