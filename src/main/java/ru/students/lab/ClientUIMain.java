package ru.students.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.students.lab.clientUI.ClientContext;
import ru.students.lab.clientUI.controllers.LoginRegisterController;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.CurrentUser;
import ru.students.lab.exceptions.AuthorizationException;
import ru.students.lab.exceptions.NoSuchCommandException;
import ru.students.lab.managers.CommandManager;
import ru.students.lab.network.ClientResponseHandler;
import ru.students.lab.network.ClientUdpChannel;
import ru.students.lab.network.CommandReader;
import ru.students.lab.util.IHandlerInput;
import ru.students.lab.util.UserInputHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Класс для запуска UI работы клиента
 * @autor Хосе Ортис
 * @version 1.0
 */
public class ClientUIMain extends Application {

    private static final Logger LOG = LogManager.getLogger(ClientUIMain.class);
    private static ClientContext clientContext;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login_register.fxml"));
        loader.setController(new LoginRegisterController(clientContext));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Dragons World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        initConfig(args);
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOG.log(Level.INFO, "Closing Dragons World on {}", LocalDateTime.now())));
    }


    public static void initConfig(String[] args) {
        InetSocketAddress address = null;
        try {
            final int port = Integer.parseInt(args[0]);
            if (args.length > 1) {
                final String host = args[1];
                address = new InetSocketAddress(host, port);
            }
            address = new InetSocketAddress(port);
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.error("Port isn't provided");
            System.exit(-1);
        } catch (IllegalArgumentException ex) {
            LOG.error("The provided port is out of the available range: " + args[0], ex);
            System.exit(-1);
        }

        try {
            final ClientUdpChannel channel = new ClientUdpChannel(address);
            CurrentUser currentUser = new CurrentUser(new Credentials(-1, "default", ""));
            LOG.info("Logged as the 'default' user, please use login command");

            CommandManager manager = new CommandManager();
            ClientResponseHandler responseHandler = new ClientResponseHandler(channel, currentUser);

            clientContext = new ClientContext() {
                @Override
                public CommandManager commandManager() {
                    return manager;
                }
                @Override
                public ClientUdpChannel clientChannel() {
                    return channel;
                }
                @Override
                public ClientResponseHandler responseHandler() {
                    return responseHandler;
                }
            };

        } catch (IOException ex) {
            LOG.error("Unable to connect to the server", ex);
            System.exit(-1);
        }
    }


}
