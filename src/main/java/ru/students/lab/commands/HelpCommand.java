package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.io.IOException;
import java.util.Set;
 /** 
 * Класс для выполнения и получения информации о функции вывода справки по доступным командам
 * @autor Хосе Ортис
 * @version 1.0
*/
public class HelpCommand extends AbsCommand {

    public static final String DESCRIPTION = "вывести справку по доступным командам";

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         return null;
     }
     /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        userInputHandler.printLn("Some Commands for you! \n" + this.keysCommands.toString() + "\n Write man {key} to have some details");
    }*/

}
