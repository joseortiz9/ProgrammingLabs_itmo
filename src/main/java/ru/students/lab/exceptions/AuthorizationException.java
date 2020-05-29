package ru.students.lab.exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String s) {
        super(s);
    }
    public AuthorizationException() {
        super();
    }
}
