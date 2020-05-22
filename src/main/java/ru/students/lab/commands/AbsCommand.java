package ru.students.lab.commands;

import ru.students.lab.models.Dragon;

import java.io.Serializable;
import java.util.Arrays;
/**
 * Класс для создания и наследования command
 * @autor Хосе Ортис
 * @version 1.0
 */
public abstract class AbsCommand implements ICommand, Serializable {

    private static final long serialVersionUID = 2901644046809010785L;
    protected String commandKey;
    protected String description = "No Description";
    protected String[] args;
    protected boolean requireInputs = false;

    public AbsCommand() {}
    public AbsCommand(String[] args) {
        this.args = args;
    }

    public String getCommandKey() {
        return commandKey;
    }
    public String getDescription() {
        return description;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     *
     * Создает объект класса dragon для передачи его серверу
     *
     * @param dragon
     */
    public void addDragonInput(Dragon dragon) {
        //
    }
    /**
     *
     * Определяет, нужен ли данной команде дополнительный ввод объекта класса dragon
     *
     *
     * @return true если ввод необходим, иначе false
     */
    public boolean requireDragonInput() {
        return requireInputs;
    }
    public Dragon getDragon() {
        return null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "args=" + Arrays.toString(args) +
                "dragon=" + getDragon() +
                '}';
    }
}
