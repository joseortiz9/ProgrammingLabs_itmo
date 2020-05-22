package ru.students.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.CurrentUser;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.network.ClientResponseHandler;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.util.UserInputHandler;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.network.ClientUdpChannel;
import ru.students.lab.network.CommandReader;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.nio.channels.ClosedChannelException;
import java.util.NoSuchElementException;
/**
 * Класс для запуска работы клиента
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClientMain {

    private static final Logger LOG = LogManager.getLogger(ClientMain.class);

    public static void main(String[] args) {
        InetSocketAddress address = null;
        ClientUdpChannel channel = null;
        try {
            final int port = Integer.parseInt(args[0]);
            if (args.length > 1) {
                final String host = args[1];
                address = new InetSocketAddress(host, port);
            }
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
            channel = new ClientUdpChannel();
        } catch (IOException ex) {
            System.err.println("Unable to connect to the server, check logs for detailed information");
            LOG.error("Unable to connect to the server", ex);
            System.exit(-1);
        }

        CurrentUser currentUser = new CurrentUser(new Credentials("default", ""));
        IHandlerInput userInputHandler = new UserInputHandler(true);
        CommandManager manager = new CommandManager();
        CommandReader reader = new CommandReader(channel, manager, userInputHandler);
        ClientResponseHandler responseHandler = new ClientResponseHandler(channel, currentUser);

        while(true) {
            try {
                if (channel.isConnected())
                    reader.startInteraction(currentUser.getCredentials());
                else
                    channel.tryToConnect(address);

                responseHandler.checkForResponse();

            } catch (NoSuchCommandException ex) {
                System.out.println(ex.getMessage());
            } catch (NoSuchElementException ex) {
                reader.finishClient();
                responseHandler.finishReceiver();
            } catch (ClosedChannelException ignored) {
            }catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("No argument passed");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("I/O Problems, check logs");
                LOG.error("I/O Problems", e);
            }
        }
    }
}
