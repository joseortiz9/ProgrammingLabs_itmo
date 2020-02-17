package ru.students.lab.models;

public class DragonHead {
    private Double eyesCount; //Поле может быть null

    public DragonHead() {
    }

    public DragonHead(Double eyesCount) {
        this.eyesCount = eyesCount;
    }

    public String setEyesCount(Double eyesCount) {
        this.eyesCount = eyesCount;
        return "";
    }

    public Double getEyesCount() {
        return eyesCount;
    }
}