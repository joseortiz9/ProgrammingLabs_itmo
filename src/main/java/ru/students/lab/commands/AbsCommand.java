package ru.students.lab.commands;

public abstract class AbsCommand implements ICommand {
    private String resultExecution = "0___ ";

    @Override
    public String getResultExecution() {
        return resultExecution;
    }

    @Override
    public void setResultExecution(int code, String resultExecution) {
        this.resultExecution = code + "___" + resultExecution;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
