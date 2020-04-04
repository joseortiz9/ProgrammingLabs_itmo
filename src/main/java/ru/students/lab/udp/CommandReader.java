package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.client.IHandlerInput;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ICommand;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.managers.CommandManager;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CommandReader extends Thread {

    private static final Logger LOG = LogManager.getLogger(CommandReader.class);

    private ClientUdpChannel channel;
    private IHandlerInput userInputHandler;
    private CommandManager commandManager;

    public CommandReader(ClientUdpChannel socket, CommandManager commandManager, IHandlerInput userInput) {
        this.channel = socket;
        this.userInputHandler = userInput;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        while(true) {
            try {
                startInteraction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public void startInteraction() throws IOException {
        String commandStr;
        commandStr = userInputHandler.readWithMessage("Write Command: ");
        AbsCommand command = commandManager.getCommand(commandStr);
        if (command != null)
            channel.sendCommand(command);
    }
}
