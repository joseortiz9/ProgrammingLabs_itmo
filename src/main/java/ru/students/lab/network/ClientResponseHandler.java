package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.util.ListEntrySerializable;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.List;

public class ClientResponseHandler {

    private class ResponseReceiver extends Thread {

        protected volatile Object receivedObject = null;

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
                    LOG.error("I/O Problems", e);
                }
            }
        }

        /**
         * Функция для получения данных
         */
        public void receiveData() throws IOException, ClassNotFoundException {
            //check if server is online comparing the passed time with the actual from the request sent
            //TODO: is resetting the connection after every success request
            if (channel.requestWasSent() && System.currentTimeMillis() - startRequestTime > 1000) {
                channel.setConnectionToFalse();
            }

            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            final SocketAddress addressFromServer = channel.receiveDatagram(buf);
            buf.flip();

            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);

            if (bytes.length < 1)
                return;

            channel.setRequestSent(false);
            if (bytes.length < AbsUdpSocket.DATA_SIZE)
                receivedObject = processResponse(bytes);
            else
                throw new EOFException();
        }

        /**
         * Функция для десериализации полученных данных
         * @param petitionBytes - данные
         * @return obj - объект десериализованных данных
         */
        private Object processResponse(byte[] petitionBytes) throws IOException, ClassNotFoundException {
            try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(petitionBytes))) {
                final Object obj = stream.readObject();
                LOG.info("received object: " + obj);
                if (obj == null)
                    throw new ClassNotFoundException();
                return obj;
            }
        }
    }


    protected static final Logger LOG = LogManager.getLogger(ClientResponseHandler.class);
    private final ResponseReceiver receiverThread;
    private final ClientUdpChannel channel;
    private volatile long startRequestTime = 0L;
    //private volatile boolean assertedObj = false;

    public ClientResponseHandler(ClientUdpChannel channel) {
        this.channel = channel;
        LOG.info("starting receiver");
        receiverThread = new ResponseReceiver();
        receiverThread.setName("ClientReceiverThread");
        receiverThread.start();
    }

    public void checkForResponse() throws ClassNotFoundException {
        startRequestTime = System.currentTimeMillis();

        Object received = receiverThread.receivedObject;

        if (received instanceof String && received.equals("connect")) {
            channel.setConnected(true);
            LOG.info("Successfully connected to the server");
            System.out.println("Successfully connected to the server");
        }

        if (received != null) {
            printResponse(received);
        }

        receiverThread.receivedObject = null;

        System.out.println("wassent: " + channel.requestWasSent() + "  connection: " + channel.connected + "   addr: " + channel.addressServer);
    }

    /**
     * Функция для вывода объектов коллекции
     * @param obj- коллекция с объектами
     */
    public void printResponse(Object obj) throws ClassNotFoundException {
        if (obj instanceof String) {
            System.out.println(obj);
        }
        else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                System.out.println("Elements found: 0");
                return;
            }
            if (((List) obj).get(0) instanceof ListEntrySerializable) {
                ((List<ListEntrySerializable>) obj).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
                System.out.println("Elements found: "+ ((List) obj).size());
            }else {
                for (Object objFromScript: (List)obj) {
                    if (objFromScript instanceof String)
                        System.out.println(objFromScript);
                    else if (objFromScript instanceof List) {
                        ((List<ListEntrySerializable>) objFromScript).stream().forEach(e -> System.out.println("key:" + e.getKey() + " -> " + e.getDragon().toString()));
                        System.out.println("Elements found: "+ ((List) objFromScript).size());
                    }
                }
            }
        } else
            throw new ClassNotFoundException();
    }

    public void finishReceiver() {
        receiverThread.interrupt();
    }


    public ResponseReceiver getReceiver() {
        return receiverThread;
    }
}
