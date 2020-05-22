package ru.students.lab.controllers;

import ru.students.lab.database.CollectionModel;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CollectionController {

    private final CollectionModel collectionModel;
    private final UserModel userModel;

    public CollectionController(CollectionModel collectionModel, UserModel userModel) {
        this.collectionModel = collectionModel;
        this.userModel = userModel;
    }

    public Object login(Credentials credentials) {
        try {
            if (userModel.checkUser(credentials))
                return credentials;
            else
                return "User/Password given not found or incorrect";
        } catch (SQLException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public Object register(Credentials credentials) {
        try {
            userModel.registerUser(credentials);
            return credentials;
        } catch (Throwable ex) {
            return ex.getMessage();
        }
    }
}
