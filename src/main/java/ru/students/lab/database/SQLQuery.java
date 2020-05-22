package ru.students.lab.database;

public class SQLQuery {
    public static class Get {
        //DRAGONS
        public static final String DRAGONS = "SELECT dragons.id, dragons.name, dragons.age, dragons.creation_date, dragons.key, coordinates.x, coordinates.y, dragon_colors.color, dragon_types.type, dragon_characters.character, dragon_heads.num_eyes\n" +
                "FROM dragons\n" +
                "    INNER JOIN coordinates ON dragons.id = coordinates.dragon_id\n" +
                "    INNER JOIN dragon_colors ON dragons.color = dragon_colors.id\n" +
                "    INNER JOIN dragon_types ON dragons.color = dragon_types.id\n" +
                "    INNER JOIN dragon_characters ON dragons.color = dragon_characters.id\n" +
                "    INNER JOIN dragon_heads ON dragons.id = dragon_heads.dragon_id";

        //USERS
        public static final String Users = "SELECT * FROM users";
        public static final String PASS_USING_USERNAME = "SELECT password FROM users WHERE username = ?";

    }

    public static class Add {
        public static final String DRAGON = "" +
                "INSERT INTO dragons(name, creation_date, age, color, type, character, key) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING id";
        public static final String COORDINATE = "" +
                "INSERT INTO coordinates(x, y, dragon_id) " +
                "VALUES(?, ?, ?)";
        public static final String DRAGON_HEAD = "" +
                "INSERT INTO dragon_heads(num_eyes, dragon_id) " +
                "VALUES(?, ?)";

        public static final String USER = "" +
                "INSERT INTO users(username, password) VALUES(?, ?)";
    }

    public static class Delete {
        public static final String DRAGON = "";
        public static final String USER = "";
    }
}
