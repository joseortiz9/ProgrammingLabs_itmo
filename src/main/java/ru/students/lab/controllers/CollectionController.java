package ru.students.lab.controllers;

import ru.students.lab.database.CollectionModel;
import ru.students.lab.database.Credentials;
import ru.students.lab.database.UserModel;
import ru.students.lab.models.Dragon;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CollectionController {

    private final CollectionModel collectionModel;
    private final UserModel userModel;
    private final ReentrantLock mainLock;

    public CollectionController(CollectionModel collectionModel, UserModel userModel) {
        this.collectionModel = collectionModel;
        this.userModel = userModel;
        mainLock = new ReentrantLock();
    }

    public HashMap<Integer, Dragon> fetchCollectionFromDB() throws SQLException {
        HashMap<Integer, Dragon> collection = collectionModel.fetchCollection();
        if (collection == null)
            throw new SQLException("It was not possible to fetch the collection from database");
        return collection;
    }

    public Object login(Credentials credentials) {
        try {
            int id = userModel.checkUserAndGetID(credentials);
            if (id > 0)
                return new Credentials(id, credentials.username, credentials.password);
            else
                return "User/Password given not found or incorrect";
        } catch (SQLException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public Object register(Credentials credentials) {
        try {
            int id = userModel.registerUser(credentials);
            if (id > 0)
                return new Credentials(id, credentials.username, credentials.password);
            else
                return credentials;
        } catch (Throwable ex) {
            return ex.getMessage();
        }
    }

    public String addDragon(int key, Dragon dragon, Credentials credentials) {
        try {
            return collectionModel.insert(key, dragon, credentials);
        } catch (Throwable ex) {
            return ex.getMessage();
        }
    }

    public String updateDragon(int id, Dragon dragon, Credentials credentials) {
        try {
            return collectionModel.update(id, dragon, credentials);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public String deleteAllDragons(Credentials credentials) {
        try {
            return collectionModel.deleteAll(credentials);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public String deleteDragon(int key, Credentials credentials) {
        try {
            return collectionModel.delete(key, credentials);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    public int[] deleteDragonsGreaterThanKey(int key, Credentials credentials) throws SQLException {
        try {
            int[] s = collectionModel.deleteGreaterThanKey(key, credentials);
            for (int value : s) System.out.println(value);
            return collectionModel.deleteGreaterThanKey(key, credentials);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public String deleteDragonsLowerThanKey(int key, Credentials credentials) {
        try {
            return collectionModel.deleteLowerThanKey(key, credentials);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
}
