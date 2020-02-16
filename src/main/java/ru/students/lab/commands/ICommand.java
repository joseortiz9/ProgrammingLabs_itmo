package ru.students.lab.commands;

public interface ICommand {
    String DESCRIPTION = "No Description";
    void execute(String[] args);
    String getDescription();
    void setResultExecution(String resultExecution);
    String getResultExecution();
}
