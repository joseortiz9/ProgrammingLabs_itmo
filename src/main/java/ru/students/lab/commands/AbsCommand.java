package ru.students.lab.commands;

import ru.students.lab.models.Dragon;

import java.io.Serializable;
import java.util.Arrays;

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

    public void addDragonInput(Dragon dragon) {
        //
    }

    public boolean requireDragonInput() {
        return requireInputs;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
