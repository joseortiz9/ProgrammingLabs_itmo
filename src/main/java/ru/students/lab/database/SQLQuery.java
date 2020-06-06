package ru.students.lab.database;

public class SQLQuery {
    public static class Get {
        //DRAGONS
        public static final String DRAGONS = "SELECT dragons.id, dragons.name, dragons.age, dragons.creation_date, dragons.key, coordinates.x, coordinates.y, dragon_colors.color, dragon_types.type, dragon_characters.character, dragon_heads.num_eyes\n" +
                "FROM dragons\n" +
                "    INNER JOIN coordinates ON dragons.id = coordinates.dragon_id\n" +
                "    INNER JOIN dragon_colors ON dragons.color = dragon_colors.id\n" +
                "    INNER JOIN dragon_types ON dragons.type = dragon_types.id\n" +
                "    INNER JOIN dragon_characters ON dragons.character = dragon_characters.id\n" +
                "    INNER JOIN dragon_heads ON dragons.id = dragon_heads.dragon_id";
        public static final String DRAGON_BY_KEY = "SELECT id FROM dragons where key = ?";

        public static final String DRAGONS_WITH_USER = "SELECT dragons.id, dragons.name, dragons.age, dragons.creation_date, dragons.key, coordinates.x, coordinates.y, dragon_colors.color, dragon_types.type, dragon_characters.character, dragon_heads.num_eyes, user_id\n" +
                "FROM dragons\n" +
                "    INNER JOIN coordinates ON dragons.id = coordinates.dragon_id\n" +
                "    INNER JOIN dragon_colors ON dragons.color = dragon_colors.id\n" +
                "    INNER JOIN dragon_types ON dragons.type = dragon_types.id\n" +
                "    INNER JOIN dragon_characters ON dragons.character = dragon_characters.id\n" +
                "    INNER JOIN dragon_heads ON dragons.id = dragon_heads.dragon_id\n" +
                "    INNER JOIN users_dragons ud on dragons.id = ud.dragon_id";

        //USERS
        public static final String USERS = "SELECT * FROM users";
        public static final String PASS_USING_USERNAME = "SELECT password, id FROM users WHERE username = ?";
        public static final String ID_USING_USERNAME = "SELECT id FROM users WHERE username = ?";

        public static final String USER_HAS_PERMISSIONS = "" +
                "SELECT exists(SELECT 1 from users_dragons where user_id = ? AND dragon_id = ?)";
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

        public static final String DRAGON_USER_RELATIONSHIP = "" +
                "INSERT INTO users_dragons VALUES (?, ?)";
    }

    public static class Update {
        public static final String DRAGON = "" +
                "UPDATE dragons SET name = ?, creation_date = ?, age = ?, color = ?, type = ?, character = ?\n" +
                "WHERE dragons.id = ?";
        public static final String COORDINATE = "" +
                "UPDATE coordinates SET x = ?, y = ? WHERE dragon_id = ?";
        public static final String DRAGON_HEAD = "" +
                "UPDATE dragon_heads SET num_eyes = ? WHERE dragon_id = ?";
    }

    public static class Delete {
        public static final String ALL_DRAGONS = "DELETE FROM DRAGONS";
        public static final String DRAGON_BY_KEY = "DELETE FROM dragons where key = ?";
        public static final String DRAGONS_WITH_GREATER_KEY = "" +
                "DELETE FROM dragons \n" +
                "WHERE id IN (SELECT d.id FROM dragons d, users u, users_dragons ud\n" +
                "             WHERE d.key > ?\n" +
                "               AND ud.dragon_id = d.id\n" +
                "               AND ud.user_id in (select id from users where id = ?)) RETURNING key";
        public static final String DRAGONS_WITH_LOWER_KEY = "" +
                "DELETE FROM dragons \n" +
                "WHERE id IN (SELECT d.id FROM dragons d, users u, users_dragons ud\n" +
                "             WHERE d.key < ?\n" +
                "               AND ud.dragon_id = d.id\n" +
                "               AND ud.user_id in (select id from users where id = ?)) RETURNING key";

        public static final String USER = "DELETE FROM users where username = ?";
    }
}
