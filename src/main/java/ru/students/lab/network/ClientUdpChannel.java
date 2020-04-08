package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.util.ListEntrySerializable;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class ClientUdpChannel extends AbsUdpSocket {

    private class DataReceiver implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    receiveData();
                } catch (ClosedChannelException ignored) {
                } catch (EOFException ex) {
                    System.err.println("Reached limit of data to receive");
                    LOG.error("Reached Limit", ex);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("I/O Problems, check logs");
                    LOG.error("I/O Problems", e);
                }
            }
        }

        public void receiveData() throws IOException, ClassNotFoundException {
            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            final SocketAddress addressFromServer = receiveDatagram(buf);
            buf.flip();

            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);

            if (bytes.length < 1)
                return;

            sentPacket = false;
            if (bytes.length < AbsUdpSocket.DATA_SIZE)
                processResponse(bytes);
            else
                throw new EOFException();
        }

        private void processResponse(byte[] petitionBytes) throws IOException, ClassNotFoundException {
            try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(petitionBytes))) {
                final Object obj = stream.readObject();
                LOG.info("received object: " + obj);
                if (obj == null)
                    throw new ClassNotFoundException();
                printObj(obj);
            }
        }

        private void printObj(Object obj) throws ClassNotFoundException {
            if (obj instanceof String)
                System.out.println(obj);
            else if (obj instanceof List) {
                ((List<ListEntrySerializable>) obj).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
                System.out.println("Elements found: "+ ((List) obj).size());
            } else
                throw new ClassNotFoundException();
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
                else {
                    LOG.info("Server is down, Retrying....");
                    System.out.println("Server is down, Retrying....");
                }
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

            final long start = System.currentTimeMillis();
            sendDatagram(objectBuffer);
            Thread.sleep(500);
            if (sentPacket && connected && System.currentTimeMillis() - start > 500) {
                connected = false;
                tryToConnect((InetSocketAddress)addressServer);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            LOG.error("", e);
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
