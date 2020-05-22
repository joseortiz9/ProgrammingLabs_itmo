package ru.students.lab.database;

import java.sql.Connection;

public class CollectionModel {
    private final Connection connection;

    public CollectionModel(Connection connection) {
        this.connection = connection;
    }
}
