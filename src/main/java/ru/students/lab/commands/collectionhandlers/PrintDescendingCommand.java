package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;

import java.util.List;
import java.util.Map;
 /** 
 * Класс для выполнения и получения информации о функции вывода элементов коллекции в порядке убывания
 * @autor Хосе Ортис
 * @version 1.0
*/
public class PrintDescendingCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы коллекции в порядке убывания.\nSyntax: print_descending -{k/i/n/d} где: -k=key / -i=id / -n=name / -d=creation_date";
    private CollectionManager collectionManager;
    /** 
     * Конструктор - создает объект класса PrintDescendingCommand и экземпляр класса collectionManager для последущей работе с коллекцией
     * @see PrintDescendingCommand#PrintDescendingCommand(CollectionManager)
     */
    public PrintDescendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) {
        List<Map.Entry<Integer, Dragon>> sortedDragons = null;

        switch (args[0]) {
            case "":
            case "-k":
                System.out.println("Sorting by key...");
                sortedDragons = this.collectionManager.sortByKey();
                break;
            case "-i":
                System.out.println("Sorting by ID...");
                sortedDragons = this.collectionManager.sortById();
                break;
            case "-n":
                System.out.println("Sorting by Name...");
                sortedDragons = this.collectionManager.sortByName();
                break;
            case "-d":
                System.out.println("Sorting by Creation Date...");
                sortedDragons = this.collectionManager.sortByCreationDate();
                break;
            default:
                userInputHandler.printLn(1,"This option is not available. Correct= -{k/i/n/d}");
        }
        if (sortedDragons != null) {
            sortedDragons.forEach(e -> userInputHandler.printElemOfList("key:" + e.getKey() + " -> " + e.getValue().toString()));
            userInputHandler.printLn(0, "Elements found: " + sortedDragons.size());
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
