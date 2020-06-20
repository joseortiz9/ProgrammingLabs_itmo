package ru.students.lab.models;

import ru.students.lab.util.ZonedDateTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс модели дракона
 * @autor Хосе Ортис
 * @version 1.0
*/
@XmlRootElement(name = "dragon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dragon implements Comparable<Dragon>, Serializable {

    private static final long serialVersionUID = -4227836017018935821L;

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private Integer userID = 0;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(value = ZonedDateTimeSerializer.class)
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long age; //Значение поля должно быть больше 0, Поле может быть null
    private Color color; //Поле не может быть null
    private DragonType type; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;
    /** 
     * Конструктор - создает пустой экземпляр класса Dragon 
     * @see Dragon#Dragon()
     */
    public Dragon() {
        this.setCreationDate();
    }

    public Dragon(String name,
                  Coordinates coordinates,
                  Long age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this.name = name;
        this.coordinates = coordinates;
        this.setCreationDate();
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
    }

    public Dragon(Integer userID,
                  String name,
                  Coordinates coordinates,
                  Long age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this(name, coordinates, age, color, type, character, head);
        this.userID = userID;
    }

    public Dragon(Integer id,
                  Integer userID,
                  String name,
                  Coordinates coordinates,
                  Long age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this(userID, name, coordinates, age, color, type, character, head);
        this.id = id;
    }

    public Dragon(Integer id,
                  Integer userID,
                  String name,
                  Coordinates coordinates,
                  Long age,
                  ZonedDateTime creationDate,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonHead head) {
        this(id, userID, name, coordinates, age, color, type, character, head);
        this.creationDate = creationDate;
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
    public DragonType getType() {
        return type;
    }
    public DragonCharacter getCharacter() {
        return character;
    }
    public Integer getUserID() {
        return userID;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setHead(DragonHead head) {
        this.head = head;
    }
    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }
    public void setType(DragonType type) {
        this.type = type;
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
        return this.getId().equals(objDragon.getId())
                && this.getName().equals(objDragon.getName())
                && this.getCoordinates().equals(objDragon.getCoordinates())
                && this.getAge().equals(objDragon.getAge())
                && this.getColor().equals(objDragon.getColor())
                && this.getType().equals(objDragon.getType());
    }

    @Override
    public String toString() {
        return "{ID=" + this.getId() + "," +
                "{UserID=" + this.getUserID() + "," +
                "name=" + this.getName() + ", " +
                "creationDate=[" + this.getFormattedCDate() + "], " +
                "Color=" + this.getColor().toString() + ", " +
                "Location="+this.getCoordinates() + "," +
                "Head=["+this.getHead().getEyesCount() + " eyes]}";
    }

    @Override
    public int compareTo(Dragon dragon) {
        long difference = this.getCreationDate().compareTo(dragon.getCreationDate());

        if(difference > 0) return 1; //first one is newer than the second
        else if(difference < 0) return -1;
        else return 0;
    }
}
