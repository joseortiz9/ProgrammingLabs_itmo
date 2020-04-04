package ru.students.lab.commands;

import java.io.Serializable;
import java.util.Arrays;

public abstract class AbsCommand implements ICommand, Serializable {

    protected String description = "No Description";
    protected String[] args;

    public AbsCommand() {}
    public AbsCommand(String[] args) {
        this.args = args;
    }

    public String getDescription() {
        return description;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "AbsCommand{" +
                "args=" + Arrays.toString(args) +
                "Desc=" + getDescription() +
                '}';
    }
}
