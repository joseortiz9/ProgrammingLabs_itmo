package ru.students.lab.controllers;

import ru.students.lab.database.CollectionModel;
import ru.students.lab.database.UserModel;

public class CollectionController {

    private final CollectionModel collectionModel;
    private final UserModel userModel;

    public CollectionController(CollectionModel collectionModel, UserModel userModel) {
        this.collectionModel = collectionModel;
        this.userModel = userModel;
    }
}
