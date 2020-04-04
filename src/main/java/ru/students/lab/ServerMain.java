package ru.students.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;
import ru.students.lab.udp.ServerRequestHandler;
import ru.students.lab.udp.ServerUdpSocket;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class ServerMain {

    private static final Logger LOG = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) {
        InetSocketAddress address = null;
        ServerUdpSocket socket = null;
        try {
            final int port = Integer.parseInt(args[0]);
            address = new InetSocketAddress(port);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Port isn't provided");
            LOG.error("Port isn't provided");
            System.exit(-1);
        } catch (IllegalArgumentException ex) {
            System.err.println("The provided port is out of the available range: " + args[0]);
            LOG.error("The provided port is out of the available range: " + args[0], ex);
            System.exit(-1);
        }

        try {
            socket = new ServerUdpSocket(address);

            FileManager fileManager = new FileManager(Paths.get("data.xml").toAbsolutePath().toString());
            CollectionManager collectionManager = new CollectionManager(fileManager.getCollectionFromFile());
            ExecutionContext executionContext = new ExecutionContext() {
                @Override
                public CollectionManager collectionManager() {
                    return collectionManager;
                }
                @Override
                public FileManager fileManager() {
                    return fileManager;
                }
            };

            ServerRequestHandler requestManager = new ServerRequestHandler(socket, executionContext);
            requestManager.start();

        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (InvalidPathException | SecurityException ex) {
            System.err.println("Invalid file's path or/and security problem trying to access it");
            LOG.error("Invalid file's path or/and security problem trying to access it",ex);
        } catch (JAXBException ex) {
            System.err.println("Problem processing the data from/into the file: " + ex.getMessage());
            LOG.error("Problem processing the data from/into the file",ex);
        } catch (IOException ex) {
            System.err.println("I/O problems: " + ex.getMessage());
            LOG.error("I/O problems",ex);
        } catch (NoSuchElementException ex) {
            System.err.println("You wrote something strange");
            LOG.error("You wrote something strange",ex);
        }

        if (socket.getSocket().isBound())
            LOG.info("Socket Successfully opened on " + socket.getSocket().getLocalSocketAddress());
    }
}
