package ru.students.lab.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class ServerRequestHandler {

    private class DataReceiver extends Thread {

        protected Object receivedObj = null;

        @Override
        public void run() {
            while (true) {
                try {
                    receiveData();
                } catch (SocketTimeoutException ignored) {
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void receiveData() throws IOException, ClassNotFoundException {
            final ByteBuffer buf = ByteBuffer.allocate(AbsUdpSocket.DATA_SIZE);
            SocketAddress addressFromClient = socket.receiveDatagram(buf);
            buf.flip();
            final byte[] petitionBytes = new byte[buf.remaining()];
            buf.get(petitionBytes);

            socket.checkClient(addressFromClient);
            if (petitionBytes.length > 0)
                processRequest(petitionBytes);
            else
                receivedObj = null;
        }

        private void processRequest(byte[] petitionBytes) throws IOException, ClassNotFoundException {
            try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(petitionBytes))) {
                final Object obj = stream.readObject();
                LOG.info("received object: " + obj);
                executeObj(obj);
            }
        }

        private void executeObj(Object obj) throws IOException {
            if (obj != null) {
                Object responseExecution;
                if (obj instanceof String)
                    responseExecution = obj;
                else {
                    AbsCommand command = (AbsCommand) obj;
                    responseExecution = command.execute(executionContext);
                }
                socket.sendObjResponse(responseExecution);
            }
        }
    }


    protected static final Logger LOG = LogManager.getLogger(ServerRequestHandler.class);

    private volatile ServerUdpSocket socket;
    private DataReceiver receiverThread;
    private volatile ExecutionContext executionContext;

    public ServerRequestHandler(ServerUdpSocket socket, ExecutionContext context) {
        this.socket = socket;
        this.executionContext = context;

        LOG.info("starting receiver");
        receiverThread = new DataReceiver();
        receiverThread.setName("ServerReceiverThread");
    }

    public void start() {
        receiverThread.start();
    }

    public void disconnect() {
        LOG.info("Disconnecting the server");
        socket.getSocket().disconnect();
        receiverThread.interrupt();
    }
}
