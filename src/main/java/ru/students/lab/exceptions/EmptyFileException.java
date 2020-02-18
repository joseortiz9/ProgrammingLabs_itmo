package ru.students.lab.exceptions;

import java.io.IOException;

public class EmptyFileException extends IOException {
    public EmptyFileException(String s) {
        super(s);
    }
}
