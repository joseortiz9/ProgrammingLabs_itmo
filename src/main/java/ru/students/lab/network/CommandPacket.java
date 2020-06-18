package ru.students.lab.network;

import ru.students.lab.commands.AbsCommand;
import ru.students.lab.database.Credentials;

import java.io.Serializable;
import java.util.Locale;

public class CommandPacket implements Serializable {

    private static final long serialVersionUID = -860971330126223957L;

    private final AbsCommand command;
    private final Credentials credentials;
    private final Locale locale;

    public CommandPacket(AbsCommand command, Credentials credentials, Locale locale) {
        this.command = command;
        this.credentials = credentials;
        this.locale = locale;
    }
    
    public Credentials getCredentials() {
        return credentials;
    }
    public AbsCommand getCommand() {
        return command;
    }
    public Locale getLocale() {
        return locale;
    }
}
