package ru.students.lab.exceptions;

public class NotPermissionsException extends RuntimeException {
    public NotPermissionsException(String s) {
        super(s);
    }
    public NotPermissionsException() {
        super();
    }
}
