package ru.students.lab.models;

import ru.students.lab.exceptions.IncorrectAgeException;
import ru.students.lab.exceptions.NullValueException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Dragon implements Comparable<Dragon> {
    private static Integer IDcounter = 1;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    private Color color; //Поле не может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;

    public Dragon() {
        this.setId();
        this.setCreationDate();
    }

    public Dragon(String name,
                  Coordinates coordinates,
                  Long age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this.setId();
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setCreationDate();
        this.setAge(age);
        this.setColor(color);
        this.setType(type);
        this.setCharacter(character);
        this.setHead(head);
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
    public String getFormattedCDate() {
        return this.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss.SSS-z"));
    }
    public Long getAge() {
        return age;
    }
    public Color getColor() {
        return color;
    }
    public DragonHead getHead() {
        return head;
    }

    public void setId() {
        this.id = IDcounter;
        IDcounter += 1;
    }

    public String setName(String name) {
        try {
            if (name.isEmpty())
                throw new NullValueException("name");
            this.name = name;
            return "";
        } catch (NullValueException ex) {
            return ex.getMessage();
        }
    }

    public String setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return "";
    }

    public String setAge(Long age) {
        try {
            if (age <= 0)
                throw new IncorrectAgeException("Age can not be a negative number!");
            this.age = age;
            return "";
        } catch (IncorrectAgeException ex) {
            return ex.getMessage();
        }
    }

    public String setColor(Color color) {
        try {
            if (color == null)
                throw new NullValueException();
            this.color = color;
            return "";
        } catch (NullValueException | IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    public String setHead(DragonHead head) {
        try {
            if (head == null)
                throw new NullValueException();
            this.head = head;
            return "";
        } catch (NullValueException ex) {
            return ex.getMessage();
        }
    }

    public String setCharacter(DragonCharacter character) {
        try {
            if (character == null)
                throw new NullValueException();
            this.character = character;
            return "";
        } catch (NullValueException ex) {
            return ex.getMessage();
        }
    }

    public String setType(DragonType type) {
        try {
            if (type == null)
                throw new NullValueException();
            this.type = type;
            return "";
        } catch (NullValueException ex) {
            return ex.getMessage();
        }
    }

    public void setCreationDate() {
        this.creationDate = ZonedDateTime.now();
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
        if (!(obj instanceof Dragon)) return false;
        if (obj == this) return true;
        Dragon objDragon = (Dragon) obj;
        return this.getId().equals(objDragon.getId()) &&
                this.getName().equals(objDragon.getName()) &&
                this.getCoordinates().equals(objDragon.getCoordinates());
    }

    @Override
    public String toString() {
        return "{ID=" + this.getId() + "," +
                "name=" + this.getName() + ", " +
                "creationDate=[" + this.getFormattedCDate() + "], " +
                "Color=" + this.getColor().toString() + ", " +
                "Location="+this.getCoordinates() + "}";
    }

    @Override
    public int compareTo(Dragon dragon) {
        long difference = this.getCreationDate().compareTo(dragon.getCreationDate());

        if(difference > 0) return 1; //first one is newer than the second
        else if(difference < 0) return -1;
        else return 0;
    }
}
