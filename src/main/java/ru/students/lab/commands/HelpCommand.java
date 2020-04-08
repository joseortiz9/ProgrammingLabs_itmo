package ru.students.lab.commands;

import java.io.IOException;
import java.util.Set;
 /** 
 * Класс для выполнения и получения информации о функции вывода справки по доступным командам
 * @autor Хосе Ортис
 * @version 1.0
*/
public class HelpCommand extends AbsCommand {

    public final String description = "";
    private Set<String> keysCommands;

    /**
    * Конструктор - создает объект класса HelpCommand и keysCommands для вывода доступных команд
    * @param keysCommands - keys for showing commands available
    */
    public HelpCommand(Set<String> keysCommands) {
        this.keysCommands = keysCommands;
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        StringBuilder s = new StringBuilder();
        s.append("Some Commands for you! \n").append(this.keysCommands.toString()).append("\nWrite man {key} to have some details");
        return s.toString();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
