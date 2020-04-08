package ru.students.lab.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.commands.AbsCommand;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.exceptions.DragonFormatException;

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
                if (obj == null)
                    throw new ClassNotFoundException();
                executeObj(obj);
            }
        }

        private void executeObj(Object obj) throws IOException {
            Object responseExecution;
            if (obj instanceof String)
                responseExecution = obj;
            else {
                AbsCommand command = (AbsCommand) obj;
                try {
                    responseExecution = command.execute(executionContext);
                }catch (DragonFormatException ex) {
                    responseExecution = ex.getMessage();
                    LOG.error(ex.getMessage(), ex);
                } catch (NumberFormatException ex) {
                    responseExecution = "Incorrect format of the entered value";
                    LOG.error("Incorrect format of the entered value", ex);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    responseExecution = "There is a problem in the amount of args passed";
                    LOG.error("There is a problem in the amount of args passed", ex);
                } catch (SecurityException ex) {
                    responseExecution = "Security problems trying to access to the file (Can not be read or edited)";
                    LOG.error("Security problems trying to access to the file (Can not be read or edited)", ex);
                }
            }
            socket.sendResponse(responseExecution);
        }
    }


    protected static final Logger LOG = LogManager.getLogger(ServerRequestHandler.class);

    private final ServerUdpSocket socket;
    private final DataReceiver receiverThread;
    private final ExecutionContext executionContext;

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
