package ru.students.lab.models;
 /** 
 * Класс модели головы дракона
 * @autor Хосе Ортис
 * @version 1.0
*/

public class DragonHead {
    private Double eyesCount; //Поле может быть null
    /** 
     * Конструктор - создает пустой объект класса DragonHead
     * @see DragonHead#DragonHead()
     */
    public DragonHead() {
    }
    /** 
     * Конструктор - создает непустой объект класса DragonHead
     * @param eyesCount - количество глаз у дракона
     * @see DragonHead#DragonHead(eyesCount)
     */
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
