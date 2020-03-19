package ru.students.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.udp.AbsUdpSocket;
import ru.students.lab.udp.ServerUdpSocket;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerMain {

    private static final Logger LOG = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) {
        InetSocketAddress address = null;
        ServerUdpSocket socket = null;
        try {
            final int port = Integer.parseInt(args[0]);
            address = new InetSocketAddress(port);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Port isn't provided");
            LOG.error("Port isn't provided");
            System.exit(-1);
        } catch (IllegalArgumentException ex) {
            System.err.println("The provided port is out of the available range: " + args[0]);
            LOG.error("The provided port is out of the available range: " + args[0], ex);
            System.exit(-1);
        }

        try {
            socket = new ServerUdpSocket(address);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (socket.getSocket().isBound())
            LOG.info("Socket Successfully opened on " + socket.getSocket().getLocalSocketAddress());

        while (true) {
            try {
                final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
                SocketAddress addressFromClient = socket.receiveDatagram(buf);
                buf.flip();
                byte[] bytes = new byte[buf.remaining()];
                buf.get(bytes);
                String gottenStr = new String(bytes, StandardCharsets.UTF_8);

                System.out.println(gottenStr);

                if (gottenStr.equals("connect")) {
                    SocketAddress existClient = socket.checkClient(addressFromClient);
                    socket.sendDatagram(buf, existClient);
                } else {
                    socket.sendDatagram(buf, addressFromClient);
                }
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
