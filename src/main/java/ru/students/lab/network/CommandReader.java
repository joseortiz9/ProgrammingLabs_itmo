package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.exceptions.AuthorizationException;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.factories.CredentialFactory;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.commands.*;
import ru.students.lab.factories.DragonFactory;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.models.Dragon;

import javax.security.sasl.AuthenticationException;
import java.io.*;

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
    public void startInteraction(Credentials credentials) throws IOException, ArrayIndexOutOfBoundsException, NoSuchCommandException {
        String commandStr;
        commandStr = userInputHandler.readWithMessage("Write Command: ");
        AbsCommand command = commandManager.getCommand(commandStr);

        if (command instanceof HelpCommand || command instanceof ManDescriptorCommand)
            userInputHandler.printLn((String) command.execute(null, credentials));
        else if (command instanceof ExitCommand)
            finishClient();
        else {
            if (credentials.username.equals(UserModel.DEFAULT_USERNAME)
                    && !(command instanceof LoginCommand)
                    && !(command instanceof RegisterCommand))
                throw new AuthorizationException("The default user can not execute special commands, please login");

            checkForInputs(command);
            channel.sendCommand(new CommandPacket(command, credentials));
        }
    }

    /**
     * Функция для проверки, нужны ли еще входные данные для отправки команды
     * @param command - команда
     */
    public void checkForInputs(AbsCommand command) {
        if (command.requireInput() == ICommand.TYPE_INPUT_DRAGON) {
            Dragon dragon = dragonFactory.generateDragonByInput(userInputHandler);
            command.addInput(dragon);
        } else if (command.requireInput() == ICommand.TYPE_INPUT_CREDENTIAL) {
            Credentials credentials = CredentialFactory.getInstance().generateCredentialByInput(userInputHandler);
            command.addInput(credentials);
        }
    }

    /**
     * Функция для отключения клиента
     */
    public void finishClient() {
        System.out.println("Good bye!");
        LOG.info("Finishing client");
        channel.disconnect();
        System.exit(0);
    }
}
