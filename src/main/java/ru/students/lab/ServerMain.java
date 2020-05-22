package ru.students.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.commands.ExecutionContext;
import ru.students.lab.controllers.CollectionController;
import ru.students.lab.database.CollectionModel;
import ru.students.lab.database.DatabaseConfigurer;
import ru.students.lab.database.UserModel;
import ru.students.lab.managers.CollectionManager;
import ru.students.lab.managers.FileManager;
import ru.students.lab.network.ServerRequestHandler;
import ru.students.lab.network.ServerUdpSocket;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.*;
import java.util.NoSuchElementException;

/**
 * Класс для запуска работы сервера
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ServerMain {

    private static final Logger LOG = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) {
        /*
        * port and server config
        * */
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

        /*
         * database config
         * */
        final DatabaseConfigurer dbConfigurer = new DatabaseConfigurer();
        dbConfigurer.loadProperties();
        dbConfigurer.setConnection();

        /*
         * receiver and data handler config
         * */
        try {
            socket = new ServerUdpSocket(address);

            final CollectionModel collectionModel = new CollectionModel(dbConfigurer.getDbConnection());
            final UserModel userModel = new UserModel(dbConfigurer.getDbConnection());
            final CollectionController controller = new CollectionController(collectionModel, userModel);

            final FileManager fileManager = new FileManager();
            final CollectionManager collectionManager = new CollectionManager();
            final StringBuilder result = new StringBuilder();

            final ExecutionContext executionContext = new ExecutionContext() {
                @Override
                public CollectionManager collectionManager() {
                    return collectionManager;
                }
                @Override
                public CollectionController collectionController() {
                    return controller;
                }
                @Override
                public FileManager fileManager() {
                    return fileManager;
                }
                @Override
                public StringBuilder result() {
                    return result;
                }
            };
            final ServerRequestHandler requestManager = new ServerRequestHandler(socket, executionContext);

            if (socket.getSocket().isBound()) {
                LOG.info("Socket Successfully opened on " + address);
                System.out.println("Socket Successfully opened on " + address);
            }
            else {
                LOG.error("Strange behaviour trying to bind the server");
                System.err.println("Strange behaviour trying to bind the server");
                System.exit(-1);
            }

            //create shutdown hook with anonymous implementation
            Runtime.getRuntime().addShutdownHook(new Thread(requestManager::disconnect));

            requestManager.receiveFromWherever();

            while (socket.getSocket().isBound()) {
            }

        } catch (IOException ex) {
            System.err.println("I/O problems: " + ex.getMessage());
            LOG.error("I/O problems",ex);
        } catch (NoSuchElementException ex) {
            System.err.println("You wrote something strange");
            LOG.error("You wrote something strange",ex);
        } catch (JAXBException ex) {
            System.err.println("Error initialing the Parser");
            LOG.error("Error initialing the Parser", ex);
        }
    }
}
