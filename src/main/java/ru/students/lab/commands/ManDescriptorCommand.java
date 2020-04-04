package ru.students.lab.commands;

import ru.students.lab.client.IHandlerInput;

import java.io.IOException;
import java.util.Map;
 /** 
 * Класс для описания команды по ее ключу
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ManDescriptorCommand extends AbsCommand {

    public static final String DESCRIPTION = "Describe a command by its key";

     @Override
     public Object execute(ExecutionContext context) throws IOException {
         return null;
     }

     /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        if (this.commandsDictionary.containsKey(args[0]))
            userInputHandler.printLn("Command: " + args[0] + "\n" + this.commandsDictionary.get(args[0]).getDescription());
        else
            userInputHandler.printLn(1,"Command: '" + args[0] + "' doesn't exist");
    }*/
}
