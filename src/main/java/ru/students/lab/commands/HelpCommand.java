package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.util.Set;
 /** 
 * Класс для выполнения и получения информации о функции вывода справки по доступным командам
 * @autor Хосе Ортис
 * @version 1.0
*/
public class HelpCommand implements ICommand {

    public static final String DESCRIPTION = "вывести справку по доступным командам";
    private Set<String> keysCommands;
    /** 
     * Конструктор - создает объект класса HelpCommand и keysCommands для вывода доступных команд
     * @see HelpCommand#HelpCommand(keysCommands)
     */
    public HelpCommand(Set<String> keysCommands) {
        this.keysCommands = keysCommands;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        userInputHandler.printLn("Some Commands for you! \n" + this.keysCommands.toString() + "\n Write man {key} to have some details");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
