package com.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase HibernateUtil para establecer la conexión de la base datos con hibernate
 * @author Carlos Javier
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static{
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password", System.getenv("hibernate_password"))
                .setProperty("hibernate.connection.username", System.getenv("hibernate_username"))
                .buildSessionFactory();
    }

    /**
     * Constructor para la conexion
     * @return
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
