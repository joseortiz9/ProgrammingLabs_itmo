package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ServerUdpSocket /*extends AbsUdpSocket*/ {

    protected static final Logger LOG = LogManager.getLogger(ServerUdpSocket.class);

    public static final int DATA_SIZE = 2048;
    public static final int SOCKET_TIMEOUT = 3000;

    protected DatagramSocket socket = null;
    protected List<SocketAddress> clientList;

    public ServerUdpSocket(InetSocketAddress a) throws SocketException {
        socket = new DatagramSocket(a);
        socket.setSoTimeout(SOCKET_TIMEOUT);
        clientList = new ArrayList<>();
    }

    //@Override
    public void sendDatagram(ByteBuffer content, SocketAddress client) throws IOException {
        byte[] buf = new byte[content.remaining()];
        content.get(buf);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, client);
        socket.send(packet);

        LOG.info("Sent datagram from SERVER to " + client);
    }

   // @Override
    public SocketAddress receiveDatagram(ByteBuffer buffer) throws IOException {
        byte[] buf = new byte[buffer.remaining()];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        LOG.info("Received datagram in SERVER from " + packet.getSocketAddress());
        buffer.put(buf, 0, packet.getLength());
        return packet.getSocketAddress();
    }

    public SocketAddress checkClient(SocketAddress a) {
        SocketAddress client = clientList.stream()
                .filter((c) -> c.equals(a))
                .findFirst()
                .orElse(null);

        if (client == null)
            clientList.add(a);

        return clientList.get(clientList.size() - 1);
    }

    public void disconnect() {
        socket.disconnect();
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
