package ru.students.lab.commands;

import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;

public interface ExecutionContext {
    CollectionManager collectionManager();
    FileManager fileManager();
}
