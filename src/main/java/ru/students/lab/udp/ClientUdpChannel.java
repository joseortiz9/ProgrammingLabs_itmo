package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.commands.AbsCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.charset.StandardCharsets;

public class ClientUdpChannel extends AbsUdpSocket {

    private class DataReceiver implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    receiveData();
                } catch (ClosedChannelException ignored) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void receiveData() throws IOException {
            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            final SocketAddress addressFromServer = receiveDatagram(buf);
            buf.flip();

            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            String gottenStr = new String(bytes, StandardCharsets.UTF_8);

            if (gottenStr.equals(""))
                return;

            sentPacket = false;
            processReceivedObject(gottenStr);
        }

        public void processReceivedObject(Object receivedObj) {
            System.out.println("From: " +addressServer+" Response:\n"+receivedObj);
        }
    }



    protected static final Logger LOG = LogManager.getLogger(ClientUdpChannel.class);

    protected DatagramChannel channel;
    protected volatile SocketAddress addressServer;
    protected volatile boolean connected;
    protected volatile boolean sentPacket;
    public Thread receiverThread;

    public ClientUdpChannel() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(null);
        addressServer = null;

        LOG.info("starting receiver");
        receiverThread = new Thread(new DataReceiver());
        receiverThread.setName("ClientReceiverThread");
        receiverThread.start();
    }


    public void tryToConnect(InetSocketAddress addressServer) {
            do {
                try {
                    synchronized (this) {
                        this.addressServer = addressServer;
                        sendCommand("connect");
                    }
                    Thread.sleep(SOCKET_TIMEOUT-200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final long start = System.currentTimeMillis();
                if (!sentPacket && !connected && System.currentTimeMillis() - start < SOCKET_TIMEOUT)
                    connected = true;
                else
                    LOG.info("Server is down, Retrying....");
            } while (!isConnected());
    }


    public void sendDatagram(ByteBuffer content) throws IOException {
        channel.send(content, addressServer);
        this.sentPacket = true;
        LOG.info("sent datagram to " + addressServer);
    }


    public SocketAddress receiveDatagram(ByteBuffer buffer) throws IOException {
        SocketAddress ret;
        ret = channel.receive(buffer);
        return ret;
    }

    public void sendCommand(Object command) {
        try(ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayStream)) {

            objectStream.writeObject(command);
            LOG.info("send object " + command);

            final ByteBuffer objectBuffer = ByteBuffer.wrap(byteArrayStream.toByteArray());
            sendDatagram(objectBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void disconnect() {
        try {
            channel.close();
            receiverThread.interrupt();
        } catch (IOException e) {
            LOG.error("Error trying to disconnect, doing a forced out", e);
            System.exit(-1);
        }
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

    public DatagramChannel getChannel() {
        return channel;
    }
}
