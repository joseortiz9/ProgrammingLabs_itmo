package ru.students.lab.commands;

import ru.students.lab.database.DBRequestManager;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;
/**
 * Интерфейс для работы с collectionManager, fileManager и stringBuilder
 * @autor Хосе Ортис
 * @version 1.0
 */
public interface ExecutionContext {
    CollectionManager collectionManager();
    DBRequestManager DBRequestManager();
    FileManager fileManager();
}
