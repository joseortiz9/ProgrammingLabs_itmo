package ru.students.lab.database;

public class SQLQuery {
    public static class Get {
        //DRAGONS
        public static final String DRAGONS = "SELECT * FROM dragons";

        //USERS
        public static final String Users = "SELECT * FROM users";
        public static final String PASS_USING_USERNAME = "SELECT password FROM users WHERE username = ?";

    }

    public static class Add {
        public static final String DRAGON = "" +
                "INSERT INTO dragons(name, creation_date, age, color, type, character)\n" +
                "VALUES(?, ?, ?, ?, ?, ?)";

        public static final String USER = "" +
                "INSERT INTO users(username, password) VALUES(?, ?)";
    }

    public static class Delete {
        public static final String DRAGON = "";
        public static final String USER = "";
    }
}
