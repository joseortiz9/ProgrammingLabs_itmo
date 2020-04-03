package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.client.IHandlerInput;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ConsoleReader extends Thread {

    private static final Logger LOG = LogManager.getLogger(ConsoleReader.class);

    private AbsUdpSocket socket;
    private IHandlerInput userInput;

    public ConsoleReader(AbsUdpSocket socket, IHandlerInput userInput) {
        this.socket = socket;
        this.userInput = userInput;
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
     * Функция получения команды с консоли catch ( e) {
     e.printStackTrace();
     }
     */
    public void startInteraction() throws IOException {
        String commandStr;
        commandStr = userInput.readWithMessage("Write Command: ");
        ByteBuffer buff = ByteBuffer.wrap(commandStr.getBytes(StandardCharsets.UTF_8));
        socket.sendDatagram(buff);
        //executeCommand(commandStr, this.userInput);
    }
}
