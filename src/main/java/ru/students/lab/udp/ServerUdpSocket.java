package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServerUdpSocket /*extends AbsUdpSocket*/ {

    private class DataReceiver implements Runnable {

        protected String receivedObj = null;

        @Override
        public void run() {
            while (true) {
                try {
                    receiveData();
                } catch (SocketTimeoutException ignored) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void receiveData() throws IOException {
            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            SocketAddress addressFromClient = receiveDatagram(buf);
            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            String gottenStr = new String(bytes, StandardCharsets.UTF_8);

            System.out.println(gottenStr);
            System.out.println(addressFromClient);

            ByteBuffer petitionBuf = ByteBuffer.wrap(gottenStr.getBytes(StandardCharsets.UTF_8));
            if (gottenStr.equals("connect")) {
                SocketAddress existClient = checkClient(addressFromClient);
                sendDatagram(petitionBuf, existClient);
            } else {
                processObject(petitionBuf);
            }
        }

        public void processObject(ByteBuffer petition) throws IOException {
            //this.receivedObj = petition;
        }
    }

    protected static final Logger LOG = LogManager.getLogger(ServerUdpSocket.class);

    public static final int SOCKET_TIMEOUT = 3000;

    protected DatagramSocket socket = null;
    protected List<SocketAddress> clientList;

    protected Thread receiverThread = null;

    public ServerUdpSocket(InetSocketAddress a) throws SocketException {
        socket = new DatagramSocket(a);
        socket.setSoTimeout(SOCKET_TIMEOUT);
        clientList = new ArrayList<>();

        LOG.info("starting receiver");
        receiverThread = new Thread(new DataReceiver());
        receiverThread.setName("ServerReceiverThread");
        receiverThread.start();
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
