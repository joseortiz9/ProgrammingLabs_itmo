package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.exceptions.SocketConnectionException;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ClientUdpSocket extends AbsUdpSocket {

    private class DataReceiver implements Runnable {

        protected String receivedObj = null;

        @Override
        public void run() {
            while (true) {
                try {
                    receiveData();
                } catch (SocketTimeoutException ignored) {
                } catch (IOException | SocketConnectionException e) {
                    e.printStackTrace();
                }
            }
        }

        public void receiveData() throws IOException, SocketConnectionException {
            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            final SocketAddress addressFromServer = receiveDatagram(buf);
            buf.flip();

            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            String gottenStr = new String(bytes, StandardCharsets.UTF_8);
            LOG.info("[UdpReceiverThread] Received data from " + addressFromServer);

            System.out.println(gottenStr);
            if (gottenStr == null || gottenStr.equals(""))
                return;

            if (!connected && gottenStr.equals("connect")) {
                try {
                    System.out.println("im here");
                    addressServer = addressFromServer;
                    //socket.connect(addressServer);
                } catch (/*SocketException |*/ IllegalArgumentException ex) {
                    LOG.error("Error connecting", ex);
                    addressServer = null;
                    connected = false;
                    return;
                }
                connected = true;
                return;
            }

            receivedObj = gottenStr;
        }
    }


    protected static final Logger LOG = LogManager.getLogger(ClientUdpSocket.class);

    public static final int DATA_SIZE = 2048;
    public static final int SOCKET_TIMEOUT = 3000;

    protected DatagramSocket socket = null;
    protected SocketAddress addressServer = null;
    protected boolean connected = false;
    protected Thread receiverThread = null;

    public ClientUdpSocket() throws SocketException {
        socket = new DatagramSocket(null);
        socket.setSoTimeout(SOCKET_TIMEOUT);

        LOG.info("starting receiver");
        receiverThread = new Thread(new DataReceiver());
        receiverThread.start();
    }

    public void tryToConnect(InetSocketAddress AddressNeeded) throws IOException, InterruptedException {
            final long start = System.currentTimeMillis();
            addressServer = AddressNeeded;
            ByteBuffer connect = ByteBuffer.wrap("connect".getBytes(StandardCharsets.UTF_8));
            sendDatagram(connect);
            Thread.sleep(SOCKET_TIMEOUT);

            if (/*System.currentTimeMillis() - start >= SOCKET_TIMEOUT-1000 && addressServer == null &&*/ !connected)
                throw new SocketTimeoutException("Connection timeout");
    }

    public void sendDatagram(ByteBuffer content) throws IOException {
        byte[] buf = new byte[content.remaining()];
        content.get(buf);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, addressServer);
        socket.send(packet);

        LOG.info("Sent datagram to " + addressServer);
    }


    public SocketAddress receiveDatagram(ByteBuffer buffer) throws IOException {
        byte[] buf = new byte[buffer.remaining()];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        LOG.info("Received datagram from " + addressServer);
        buffer.put(buf, 0, packet.getLength());
        return packet.getSocketAddress();
    }

    public void disconnect() {
        socket.disconnect();
        receiverThread.interrupt();
    }

    public boolean isConnected() {
        return addressServer != null && connected;
    }

    public SocketAddress getAddressServer() {
        return addressServer;
    }

    public void getAddressServer(SocketAddress addressTo) {
        this.addressServer = addressTo;
    }
}
