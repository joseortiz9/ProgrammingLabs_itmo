package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.*;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.factories.DragonFactory;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.models.Dragon;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CommandReader extends Thread {

    private static final Logger LOG = LogManager.getLogger(CommandReader.class);

    private ClientUdpChannel channel;
    private IHandlerInput userInputHandler;
    private CommandManager commandManager;
    private DragonFactory dragonFactory;

    public CommandReader(ClientUdpChannel socket, CommandManager commandManager, IHandlerInput userInput) {
        this.channel = socket;
        this.userInputHandler = userInput;
        this.commandManager = commandManager;
        this.dragonFactory = new DragonFactory();
    }

    @Override
    public void run() {
        while(true) {
            try {
                startInteraction();
            }catch (NullPointerException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public void startInteraction() throws IOException, NullPointerException {
        String commandStr;
        commandStr = userInputHandler.readWithMessage("Write Command: ");
        AbsCommand command = commandManager.getCommand(commandStr);

        if (command == null)
            throw new NullPointerException();

        if (command instanceof HelpCommand || command instanceof ManDescriptorCommand)
            userInputHandler.printLn((String) command.execute(null));
        else if (command instanceof ExitCommand)
            finishClient();
        else {
            checkForInputs(command);
            channel.sendCommand(command);
        }
    }

    public void checkForInputs(AbsCommand command) {
        if (command.requireDragonInput()) {
            Dragon dragon = dragonFactory.generateDragonByInput(userInputHandler);
            command.addDragonInput(dragon);
        }
    }

    public void finishClient() {
        channel.disconnect();
        this.interrupt();
    }
}
