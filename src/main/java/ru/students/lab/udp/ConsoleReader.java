package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ConsoleReader extends Thread {

    private static final Logger LOG = LogManager.getLogger(ConsoleReader.class);

    private AbsUdpSocket socket = null;

    public ConsoleReader(AbsUdpSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                while (!in.ready()) {
                    Thread.sleep(100);
                }
                String line = in.readLine();
                ByteBuffer buff = ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8));
                socket.sendDatagram(buff);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
