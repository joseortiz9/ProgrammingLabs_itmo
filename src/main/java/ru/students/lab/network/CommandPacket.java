package ru.students.lab.network;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.database.Credentials;

import java.io.Serializable;

public class CommandPacket implements Serializable {

    private static final long serialVersionUID = -860971330126223957L;

    private final AbsCommand command;
    private final Credentials credentials;

    public CommandPacket(AbsCommand command, Credentials credentials) {
        this.command = command;
        this.credentials = credentials;
    }
    public Credentials getCredentials() {
        return credentials;
    }
    public AbsCommand getCommand() {
        return command;
    }
}
