package ru.students.lab.commands;

public class ExitCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "завершить программу (без сохранения в файл))";

    @Override
    public void execute(String[] args) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
