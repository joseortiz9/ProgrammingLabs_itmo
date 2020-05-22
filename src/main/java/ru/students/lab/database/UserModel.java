package ru.students.lab.database;

import java.sql.Connection;

public class UserModel {
    private final Connection connection;

    public UserModel(Connection connection) {
        this.connection = connection;
    }
}
