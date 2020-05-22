package ru.students.lab.database;

import ru.students.lab.models.*;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class CollectionModel {
    private final Connection connection;

    public CollectionModel(Connection connection) {
        this.connection = connection;
    }


    public HashMap<Integer, Dragon> fetchCollection() throws SQLException {
        //final ReentrantLock mainLock = this.mainLock;
        //mainLock.lock();
        //try {
        HashMap<Integer, Dragon> collection = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Get.DRAGONS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            ZonedDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime().atZone(ZoneId.of("UTC"));
            DragonHead head = new DragonHead((rs.getDouble("num_eyes") == 0) ? null : rs.getDouble("num_eyes")) ;
            Dragon dragon = new Dragon(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Coordinates(rs.getLong("x"), rs.getFloat("y")),
                    rs.getLong("age"),
                    creationDate,
                    Color.valueOf(rs.getString("color")),
                    DragonType.valueOf(rs.getString("type")),
                    DragonCharacter.valueOf(rs.getString("character")),
                    head);
            collection.putIfAbsent(rs.getInt("key"), dragon);
        }
        return collection;
        /*} finally {
            mainLock.unlock();
        }*/
    }

    public String insert(int key, Dragon dragon, Credentials credentials) throws SQLException {
        //final ReentrantLock mainLock = this.mainLock;
        //mainLock.lock();
        //try {
        final boolean oldAutoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Add.DRAGON);
            int pointer = 0;
            preparedStatement.setString(++pointer, dragon.getName());
            preparedStatement.setTimestamp(++pointer, Timestamp.valueOf(dragon.getCreationDate().toLocalDateTime()));
            preparedStatement.setLong(++pointer, dragon.getAge());
            preparedStatement.setInt(++pointer, dragon.getColor().ordinal()+1);
            preparedStatement.setInt(++pointer, dragon.getType().ordinal()+1);
            preparedStatement.setInt(++pointer, dragon.getCharacter().ordinal()+1);
            preparedStatement.setInt(++pointer, key);
            ResultSet rs = preparedStatement.executeQuery();
            int dragonID = 0;
            if (rs.next())
                dragonID = rs.getInt(1);

            preparedStatement = connection.prepareStatement(SQLQuery.Add.COORDINATE);
            pointer = 0;
            preparedStatement.setLong(++pointer, dragon.getCoordinates().getX());
            preparedStatement.setFloat(++pointer, dragon.getCoordinates().getY());
            preparedStatement.setInt(++pointer, dragonID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(SQLQuery.Add.DRAGON_HEAD);
            pointer = 0;
            if (dragon.getHead().getEyesCount() == null)
                preparedStatement.setNull(++pointer, Types.DOUBLE);
            else
                preparedStatement.setDouble(++pointer, dragon.getHead().getEyesCount());
            preparedStatement.setInt(++pointer, dragonID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(SQLQuery.Add.DRAGON_USER_RELATIONSHIP);
            pointer = 0;
            preparedStatement.setInt(++pointer, credentials.id);
            preparedStatement.setInt(++pointer, dragonID);
            preparedStatement.executeUpdate();

            connection.commit();

            return String.valueOf(dragonID);
        } catch (Throwable e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(oldAutoCommit);
        }
        /*} finally {
            mainLock.unlock();
        }*/
    }

    public String update(int id, Dragon dragon, Credentials credentials) throws SQLException {
        if (!hasPermissions(credentials.id, id))
            return "You have no permissions to edit this object";

        //final ReentrantLock mainLock = this.mainLock;
        //mainLock.lock();
        //try {
        final boolean oldAutoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Update.DRAGON);
            int pointer = 0;
            preparedStatement.setString(++pointer, dragon.getName());
            preparedStatement.setTimestamp(++pointer, Timestamp.valueOf(dragon.getCreationDate().toLocalDateTime()));
            preparedStatement.setLong(++pointer, dragon.getAge());
            preparedStatement.setInt(++pointer, dragon.getColor().ordinal()+1);
            preparedStatement.setInt(++pointer, dragon.getType().ordinal()+1);
            preparedStatement.setInt(++pointer, dragon.getCharacter().ordinal()+1);
            preparedStatement.setInt(++pointer, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(SQLQuery.Update.COORDINATE);
            pointer = 0;
            preparedStatement.setLong(++pointer, dragon.getCoordinates().getX());
            preparedStatement.setFloat(++pointer, dragon.getCoordinates().getY());
            preparedStatement.setInt(++pointer, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(SQLQuery.Update.DRAGON_HEAD);
            pointer = 0;
            if (dragon.getHead().getEyesCount() == null)
                preparedStatement.setNull(++pointer, Types.DOUBLE);
            else
                preparedStatement.setDouble(++pointer, dragon.getHead().getEyesCount());
            preparedStatement.setInt(++pointer, id);
            preparedStatement.executeUpdate();

            connection.commit();

            return null;
        } catch (Throwable e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(oldAutoCommit);
        }
        /*} finally {
            mainLock.unlock();
        }*/
    }

    public boolean hasPermissions(int userID, int dragonID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Get.USER_HAS_PERMISSIONS);
        int pointer = 0;
        preparedStatement.setInt(++pointer, userID);
        preparedStatement.setInt(++pointer, dragonID);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getBoolean("exists");
        }

        return false;
    }
}
