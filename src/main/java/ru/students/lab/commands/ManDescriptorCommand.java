package ru.students.lab.commands;

import java.io.IOException;
import java.util.Map;
 /** 
 * Класс для описания команды по ее ключу
 * @autor Хосе Ортис
 * @version 1.0
*/
public class ManDescriptorCommand extends AbsCommand {

    public final String description = "Describe a command by its key";
     private Map<String, AbsCommand> commandsDictionary;
     /**
      * Конструктор - создает объект класса ManDescriptorCommand и экземпляр класса commandsDictionary для вывода информации о команде по ее ключу
      * @param commandsDictionary - commands instances for getting its descriptions
      */
     public ManDescriptorCommand(Map<String, AbsCommand> commandsDictionary) {
         this.commandsDictionary = commandsDictionary;
     }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        StringBuilder s = new StringBuilder();
        if (this.commandsDictionary.containsKey(args[0]))
            s.append("Command: ").append(args[0]).append("\n").append(this.commandsDictionary.get(args[0]).getDescription());
        else
            s.append("Command: '").append(args[0]).append("' doesn't exist");
        return s.toString();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
