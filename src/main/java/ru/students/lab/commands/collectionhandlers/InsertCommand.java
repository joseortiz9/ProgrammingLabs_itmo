package ru.students.lab.commands.collectionhandlers;

import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.commands.ICommand;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.models.Dragon;
import ru.students.lab.factories.DragonFactory;

import java.io.IOException;

/**
 * Класс для выполнения и получения информации о функции добавления в коллекцию элемента с заданным ключем
 * @autor Хосе Ортис
 * @version 1.0
*/
public class InsertCommand extends AbsCommand {

    public static final String DESCRIPTION = "добавить новый элемент с заданным ключом.\nSyntax: insert key {element}\n"
            +"Rules:\n"
            +"coord{X}    [should be more than -328]\n"
            +"age         [should be more than 0]\n"
            +"head{#Eyes} [empty or more than 0]";

    private DragonFactory dragonFactory;
    /** 
     * Конструктор - создает объект InsertCommand и экземпляр класса collectionManager для последующей работы с коллекцией; создает экземпляр класса DragonFactory для создания экземпляра класса Dragon
     */
    public InsertCommand() {
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public Object execute(ExecutionContext context) throws IOException {
        return null;
    }
    /*
    @Override
    public void execute(IHandlerInput userInputHandler, String[] args) throws NumberFormatException {
        if (this.collectionManager.getCollection().containsKey(Integer.valueOf(args[0]))) {
            userInputHandler.printLn(1,"The key '" + Integer.valueOf(args[0]) + "' already exist");
            return;
        }

        Dragon newDragon = (userInputHandler.isInteractive()) ?
                dragonFactory.generateDragonByInput(userInputHandler) :
                dragonFactory.generateFromScript(userInputHandler);

        if (newDragon == null) {
            userInputHandler.printLn(1,"That Dragon has format problems!");
            return;
        }

        // If it doesn't exist and it successfully put it, so it returns null
        if (this.collectionManager.insert(Integer.valueOf(args[0]), newDragon) == null)
            userInputHandler.printLn(0,newDragon.toString() + " saved!");
    }
    */


}
