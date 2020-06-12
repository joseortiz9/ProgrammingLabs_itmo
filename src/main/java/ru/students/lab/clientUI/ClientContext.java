package ru.students.lab.clientUI;

import ru.students.lab.managers.CommandManager;
import ru.students.lab.network.ClientResponseHandler;
import ru.students.lab.network.ClientUdpChannel;

public interface ClientContext {
    CommandManager commandManager();
    ClientUdpChannel clientChannel();
    ClientResponseHandler responseHandler();
}
