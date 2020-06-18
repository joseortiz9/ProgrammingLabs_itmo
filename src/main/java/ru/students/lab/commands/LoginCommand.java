package ru.students.lab.commands;

import ru.students.lab.database.Credentials;

import java.io.IOException;

public class LoginCommand extends AbsCommand {

    protected Credentials credentials = null;

    public LoginCommand() {
        commandKey = "login";
        description = "Login into the system to manage your own dragons.\nSyntax: login {credentials}";
    }

    @Override
    public void addInput(Object credentials) {
        this.credentials = (Credentials) credentials;
    }

    @Override
    public Object execute(ExecutionContext context, Credentials credentials) throws IOException {
        Object response = context.DBRequestManager().login(this.credentials);
        //TODO check the best way to return the translated text
        if (response instanceof String)
            response = context.resourcesBundle().getString((String) response);
        return response;
    }

    @Override
    public int requireInput() {
        return TYPE_INPUT_CREDENTIAL;
    }

    @Override
    public Object getInput() {
        return credentials;
    }
}
