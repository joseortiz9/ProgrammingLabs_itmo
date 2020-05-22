package ru.students.lab.database;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class UserModel {
    private static final String DEFAULT_USERNAME = "default";
    private static final String ROOT_USERNAME = "root";
    private final Connection connection;
    private final ReentrantLock mainLock = new ReentrantLock();

    public UserModel(Connection connection) {
        this.connection = connection;
    }

    public boolean checkUser(Credentials credentials) throws SQLException, NoSuchAlgorithmException {
        if (credentials == null || credentials.username.equals(DEFAULT_USERNAME))
            return false;

        PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Get.PASS_USING_USERNAME);
        preparedStatement.setString(1, credentials.username);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next())
            return hashPassword(credentials.password).equals(rs.getString(1));
        return false;
    }


    public void registerUser(Credentials credentials) throws SQLException, NoSuchAlgorithmException {
        //final ReentrantLock mainLock = this.mainLock;
        //mainLock.lock();
        //try {
            final boolean oldAutoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.Add.USER);

                int pointer = 0;
                preparedStatement.setString(++pointer, credentials.username);
                preparedStatement.setString(++pointer, hashPassword(credentials.password));

                preparedStatement.executeUpdate();
                connection.commit();
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


    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return toHexString(md.digest(password.getBytes(StandardCharsets.UTF_8)));
    }
    public String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
            hexString.insert(0, '0');

        return hexString.toString();
    }
}
