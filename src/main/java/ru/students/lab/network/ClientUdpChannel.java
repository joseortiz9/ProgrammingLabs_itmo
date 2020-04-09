package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.util.ListEntrySerializable;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class ClientUdpChannel extends AbsUdpSocket {

    protected static final Logger LOG = LogManager.getLogger(ClientUdpChannel.class);

    protected DatagramChannel channel;
    protected SocketAddress addressServer;
    protected boolean connected;
    protected boolean requestSent;

    public ClientUdpChannel() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(null);
        addressServer = null;
    }


    public void tryToConnect(InetSocketAddress addressServer) {
        this.addressServer = addressServer;
        sendCommand("connect");
        System.out.println("Trying to reach the server...");
        LOG.info("Trying to reach the server...");
    }


    public void sendDatagram(ByteBuffer content) throws IOException {
        channel.send(content, addressServer);
        this.requestSent = true;
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
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            LOG.error("", e);
        }
    }


    public void disconnect() {
        try {
            channel.close();
        } catch (IOException e) {
            LOG.error("Error trying to disconnect, doing a forced out", e);
            System.exit(-1);
        }
    }

    public boolean isConnected() {
        return addressServer != null && connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setConnectionToFalse() {
        this.addressServer = null;
        this.connected = false;
    }

    public boolean requestWasSent() {
        return requestSent;
    }

    public Object receiveData() throws IOException, ClassNotFoundException {
        final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
        final SocketAddress addressFromServer = receiveDatagram(buf);
        buf.flip();

        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);

        if (bytes.length < 1)
            return null;

        requestSent = false;
        if (bytes.length < AbsUdpSocket.DATA_SIZE)
            return processResponse(bytes);
        else
            throw new EOFException();
    }

    private Object processResponse(byte[] petitionBytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(petitionBytes))) {
            final Object obj = stream.readObject();
            LOG.info("received object: " + obj);
            if (obj == null)
                throw new ClassNotFoundException();
            return obj;
        }
    }

    public void printObj(Object obj) throws ClassNotFoundException {
        if (obj instanceof String)
            System.out.println(obj);
        else if (obj instanceof List) {
            ((List<ListEntrySerializable>) obj).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
            System.out.println("Elements found: "+ ((List) obj).size());
        } else if (obj instanceof String[]) {

        } else
            throw new ClassNotFoundException();
    }
}
