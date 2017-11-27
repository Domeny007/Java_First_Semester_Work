package utils.DatabaseUtils;

import exceptions.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbWrapper {

    private final static String DRIVER = "org.postgresql.Driver";
    private final static String CONNECTION_URI = "jdbc:postgresql://localhost:5432/movie_app";
    private final static String LOGIN = "postgres";
    private final static String PASSWORD = "pgAdmin";

    private static Connection conn;

    public static Connection getConnection() throws DbException {
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(CONNECTION_URI, LOGIN, PASSWORD);
            } catch (SQLException ex) {
                throw new DbException("Can't connect to DB (" + ex.getErrorCode() + ": " + ex.getMessage() + ").");
            } catch (ClassNotFoundException e) {
                throw new DbException("Can't find DB driver.");
            }

        }
        return conn;
    }

}
