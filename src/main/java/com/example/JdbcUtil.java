package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static Connection con;

    static{
        String url = "jdbc:mysql://localhost/reto2";
        String user= System.getenv("MYSQL_USER");
        String pass= System.getenv("MYSQL_ROOT_PASSWORD");

        try{
            con = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getCon() {
        return con;
    }
}
