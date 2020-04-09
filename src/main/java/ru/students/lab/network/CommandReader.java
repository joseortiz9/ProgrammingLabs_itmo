package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.commands.*;
import ru.students.lab.factories.DragonFactory;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.models.Dragon;

import java.io.*;
import java.util.NoSuchElementException;
/**
 * Класс для чтения и подтверждения правильности команд серверу
 * @autor Хосе Ортис
 * @version 1.0
 */
public class CommandReader {

    private static final Logger LOG = LogManager.getLogger(CommandReader.class);

    private final ClientUdpChannel channel;
    private final IHandlerInput userInputHandler;
    private final CommandManager commandManager;
    private final DragonFactory dragonFactory;

    public CommandReader(ClientUdpChannel socket, CommandManager commandManager, IHandlerInput userInput) {
        this.channel = socket;
        this.userInputHandler = userInput;
        this.commandManager = commandManager;
        this.dragonFactory = new DragonFactory();
    }

    /**
     * Функция для чтения команд от пользователя
     */
    public void startInteraction() throws IOException, NoSuchCommandException {
        String commandStr;
        commandStr = userInputHandler.readWithMessage("Write Command: ");
        AbsCommand command = commandManager.getCommand(commandStr);

        if (command instanceof HelpCommand || command instanceof ManDescriptorCommand)
            userInputHandler.printLn((String) command.execute(null));
        else if (command instanceof ExitCommand)
            finishClient();
        else {
            checkForInputs(command);
            channel.sendCommand(command);
        }
    }
     /**
     * Функция для проверки, нужны ли еще входные данные для отправки команды
      * @param command - команда
     */
    public void checkForInputs(AbsCommand command) {
        if (command.requireDragonInput()) {
            Dragon dragon = dragonFactory.generateDragonByInput(userInputHandler);
            command.addDragonInput(dragon);
        }
    }
    /**
     * Функция для отключения клиента
     */
    public void finishClient() {
        LOG.info("Finishing client");
        channel.disconnect();
        System.exit(0);
    }
}
