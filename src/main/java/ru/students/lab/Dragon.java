package ru.students.lab;

import ru.students.lab.exceptions.IncorrectCoordException;
import ru.students.lab.exceptions.NullValueException;

import java.time.ZonedDateTime;
import java.util.Random;

public class Dragon {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    private Color color; //Поле не может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;

    public Dragon(Integer id,
                  String name,
                  Coordinates coordinates,
                  ZonedDateTime creationDate,
                  Long age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result += (this.getId()) << 2;
        result += this.getCoordinates().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Dragon))
            return false;
        if (obj == this)
            return true;
        Dragon objDragon = (Dragon) obj;
        return this.getId().equals(objDragon.getId()) &&
                this.getName().equals(objDragon.getName()) &&
                this.getCoordinates().equals(objDragon.getCoordinates());
    }

    @Override
    public String toString() {
        return this.getName() + this.getCoordinates();
    }
}



class Coordinates {
    private Long x; //Значение поля должно быть больше -328, Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Long x, Float y) /*throws NullValueException, IncorrectCoordException*/ {
        /*if (x.equals(null) || y.equals(null))
            throw new NullValueException(x.toString());
        else if (x <= -328)
            throw new IncorrectCoordException("Value of X should be more than -328, digit x=" + x.toString());
        */
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int result = 34;
        result += this.getX() << 2;
        result <<= 3;
        result += this.getY();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates))
            return false;
        if (obj == this)
            return true;
        Coordinates objCoord = (Coordinates) obj;
        return this.getX().equals(objCoord.getX()) &&
                this.getY().equals(objCoord.getY());
    }

    @Override
    public String toString() {
        return " [x=" + this.getX() + ", y=" + this.getY() + "]";
    }
}

class DragonHead {
    private Double eyesCount; //Поле может быть null

    public DragonHead(Double eyesCount) {
        this.eyesCount = eyesCount;
    }

    public Double getEyesCount() {
        return eyesCount;
    }
}

enum Color {
    RED,
    BLUE,
    YELLOW,
    ORANGE,
    WHITE;

    @Override
    public String toString() {
        return this.name();
    }

    public static Color getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}

enum DragonType {
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    @Override
    public String toString() {
        return this.name();
    }

    public static DragonType getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}

enum DragonCharacter {
    CUNNING,
    WISE,
    EVIL,
    GOOD,
    CHAOTIC;

    @Override
    public String toString() {
        return this.name();
    }

    public static DragonCharacter getRand() {
        int randIndex = new Random().nextInt(values().length);
        return values()[randIndex];
    }
}
