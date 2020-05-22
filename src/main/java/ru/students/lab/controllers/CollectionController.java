package ru.students.lab.controllers;

import ru.students.lab.database.CollectionModel;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.models.Dragon;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

public class CollectionController {

    private final CollectionModel collectionModel;
    private final UserModel userModel;

    public CollectionController(CollectionModel collectionModel, UserModel userModel) {
        this.collectionModel = collectionModel;
        this.userModel = userModel;
    }

    public HashMap<Integer, Dragon> fetchCollectionFromDB() throws SQLException {
        HashMap<Integer, Dragon> collection = collectionModel.fetchCollection();
        if (collection == null || collection.isEmpty())
            throw new SQLException("It was not possible to fetch the collection from database");
        return collection;
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

    public String addDragon(int key, Dragon dragon) {
        try {
            return collectionModel.insert(key, dragon);
        } catch (Throwable ex) {
            return ex.getMessage();
        }
    }
}
