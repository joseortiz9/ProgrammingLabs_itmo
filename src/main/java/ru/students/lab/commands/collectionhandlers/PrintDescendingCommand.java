package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;

import java.util.List;
import java.util.Map;

public class PrintDescendingCommand extends AbsCommand implements ICommand {

    public static final String DESCRIPTION = "вывести элементы коллекции в порядке убывания.\nSyntax: print_descending -{k/i/n/d} где: -k=key / -i=id / -n=name / -d=creation_date";
    private CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
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
                setResultExecution("This option is not available. Correct= -{k/i/n/d}");
        }
        if (sortedDragons != null)
            sortedDragons.forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getValue().toString()));
    }
}
