package com.example;

import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;

public class Main {
    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

        userDAO.findAll().forEach(System.out::println);

        Usuario user = new Usuario();

        System.out.println(userDAO.findById(3));
        user.setNombreUsuario("Jauja");
        userDAO.update(user);
        System.out.println(userDAO.findById(3));


    }
}