package org.reverse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
    private static Connection conn;

    private static final String URL = "jdbc:sqlite:meubanco.db";

    private Singleton() {}

    public static Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }



}
