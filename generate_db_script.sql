CREATE TABLE dragon_colors (
    id SERIAL PRIMARY KEY,
    color VARCHAR(30) NOT NULL
);

CREATE TABLE dragon_types (
    id SERIAL PRIMARY KEY,
    type VARCHAR(30) NOT NULL
);

CREATE TABLE dragon_characters (
    id SERIAL PRIMARY KEY,
    character VARCHAR(30) NOT NULL
);

CREATE TABLE dragons (
    id SERIAL PRIMARY KEY,
    key INT UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    age BIGINT NULL,
    color INT NOT NULL,
    type INT NOT NULL,
    character INT NOT NULL,
    FOREIGN KEY (color) REFERENCES dragon_colors(id),
    FOREIGN KEY (type) REFERENCES dragon_types(id),
    FOREIGN KEY (character) REFERENCES dragon_characters(id)
);

/*float in SQL has the same limits as double in Java*/
CREATE TABLE dragon_heads (
    id SERIAL PRIMARY KEY,
    num_eyes FLOAT NULL,
    dragon_id INT NOT NULL,
    FOREIGN KEY (dragon_id) REFERENCES dragons(id) ON DELETE CASCADE
);

/*decimal in SQL has the same limits as float in Java*/
CREATE TABLE coordinates (
    id SERIAL PRIMARY KEY,
    x BIGINT NOT NULL,
    y DECIMAL NOT NULL,
    dragon_id INT NOT NULL,
    FOREIGN KEY (dragon_id) REFERENCES dragons(id) ON DELETE CASCADE
);

INSERT INTO dragon_colors(color)
VALUES('RED'),('BLUE'),('YELLOW'),('ORANGE'),('WHITE');

INSERT INTO dragon_types(type)
VALUES('WATER'),('UNDERGROUND'),('AIR'),('FIRE');

INSERT INTO dragon_characters(character)
VALUES('CUNNING'),('WISE'),('EVIL'),('GOOD'),('CHAOTIC');



CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE users_dragons (
    user_id INT NOT NULL,
    dragon_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (dragon_id) REFERENCES dragons(id) ON DELETE CASCADE
);

INSERT INTO users(username, password)
VALUES('root', '4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2');

