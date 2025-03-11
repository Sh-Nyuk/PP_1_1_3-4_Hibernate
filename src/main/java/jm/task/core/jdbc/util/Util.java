package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URl = "jdbc:mysql://localhost:3306/firstdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConn() {
        try {
            return DriverManager.getConnection(URl, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
