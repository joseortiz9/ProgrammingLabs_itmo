package ru.students.lab.models;

import java.io.Serializable;

/**
 * Класс модели головы дракона
 * @autor Хосе Ортис
 * @version 1.0
*/

public class DragonHead implements Serializable {

    private static final long serialVersionUID = 4259939532361840335L;

    private Double eyesCount; //Поле может быть null

    /** 
     * Конструктор - создает непустой объект класса DragonHead
     * @param eyesCount - количество глаз у дракона
     */
    public DragonHead(Double eyesCount) {
        this.eyesCount = eyesCount;
    }

    public Double getEyesCount() {
        return eyesCount;
    }

    @Override
    public String toString() {
        return ""+eyesCount;
    }
}
