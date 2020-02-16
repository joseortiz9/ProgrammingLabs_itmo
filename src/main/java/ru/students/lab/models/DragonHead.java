package ru.students.lab.models;

public class DragonHead {
    private Double eyesCount; //Поле может быть null

    public DragonHead(Double eyesCount) {
        this.eyesCount = eyesCount;
    }

    public Double getEyesCount() {
        return eyesCount;
    }
}