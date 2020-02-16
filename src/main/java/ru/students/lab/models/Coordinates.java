package ru.students.lab.models;

public class Coordinates {
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
