package ru.students.lab.database;

public class SQLQuery {
    private static class Get {
        private static final String DRAGONS = "SELECT * FROM dragons";

        private static final String Users = "SELECT * FROM users";
    }

    private static class add {
        private static final String DRAGON = "" +
                "INSERT INTO dragons(name, creation_date, age, color, type, character)\n" +
                "VALUES(?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
        private static final String USER = "" +
                "INSERT INTO users(username, email, password) VALUES(?, ?, ?)\n" +
                "    ON CONFLICT DO NOTHING RETURNING id";
    }

    private static class delete {
        private static final String DRAGON = "";
        private static final String USER = "";
    }
}
