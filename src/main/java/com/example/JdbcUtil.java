package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase JdbcUtil para establecer la conexion con la base de datos y generar informes con Jaspersoft
 * @author Carlos Javier
 */
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

    /**
     * Constructor para la conexion
     * @return
     */
    public static Connection getCon() {
        return con;
    }
}
