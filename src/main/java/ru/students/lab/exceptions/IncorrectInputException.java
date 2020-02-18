package ru.students.lab.exceptions;

public class IncorrectInputException extends Exception {
    public IncorrectInputException() {
        super();
    }
    public IncorrectInputException(String s) {
        super(s);
    }
}