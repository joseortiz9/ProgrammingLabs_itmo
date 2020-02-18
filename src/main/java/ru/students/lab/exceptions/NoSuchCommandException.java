package ru.students.lab.exceptions;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String s) {
        super(s);
    }
}
