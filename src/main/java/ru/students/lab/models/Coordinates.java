package ru.students.lab.models;

public class Coordinates {
    private Long x; //Значение поля должно быть больше -328, Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates() {
    }

    public Coordinates(Long x, Float y) {
        this.setX(x);
        this.setY(y);
    }

    public Long getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
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
        if (!(obj instanceof Coordinates)) return false;
        if (obj == this) return true;
        Coordinates objCoord = (Coordinates) obj;
        return this.getX().equals(objCoord.getX()) &&
                this.getY().equals(objCoord.getY());
    }

    @Override
    public String toString() {
        return "[x=" + this.getX() + ", y=" + this.getY() + "]";
    }
}
