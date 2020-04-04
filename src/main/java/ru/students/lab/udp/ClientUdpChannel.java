package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
            else
                sentPacket = false;

            processReceivedObject(gottenStr);
        }

        public void processReceivedObject(Object receivedObj) {
            System.out.println("received: "+receivedObj+" from "+addressServer);
        }
    }



    protected static final Logger LOG = LogManager.getLogger(ClientUdpChannel.class);
    protected static final int PACKET_SIZE = 1024;

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


    public void tryToConnect(InetSocketAddress addressServer) throws IOException, InterruptedException {
            do {
                try {
                    synchronized (this) {
                        this.addressServer = addressServer;
                        ByteBuffer connect = ByteBuffer.wrap("connect".getBytes(StandardCharsets.UTF_8));
                        sendDatagram(connect);
                    }
                    Thread.sleep(SOCKET_TIMEOUT-200);
                } catch (AlreadyBoundException | UnsupportedAddressTypeException | IOException ex) {
                    LOG.error("Strange Error", ex);
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

    public void sendObj(ByteBuffer content) {
        try {
            sendDatagram(content);
        } catch (ClosedChannelException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void receiveObj() {

    }

    public void disconnect() {
        try {
            channel.close();
            receiverThread.interrupt();
        } catch (IOException e) {
            LOG.error("Error trying to disconnect, doing a force out", e);
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
