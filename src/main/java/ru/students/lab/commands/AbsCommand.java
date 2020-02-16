package ru.students.lab.commands;

public abstract class AbsCommand implements ICommand {
    private String resultExecution;

    @Override
    public String getResultExecution() {
        return resultExecution;
    }

    @Override
    public void setResultExecution(String resultExecution) {
        this.resultExecution = resultExecution;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
