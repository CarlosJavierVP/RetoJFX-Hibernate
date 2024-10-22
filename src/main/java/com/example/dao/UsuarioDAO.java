package com.example.dao;


import com.example.HibernateUtil;
import com.example.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    private static SessionFactory sessionFactory = null;
    public UsuarioDAO (SessionFactory se){
        sessionFactory = se;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> lista;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u", Usuario.class);
            lista = query.list();
        }
        return lista;
    }

    @Override
    public Usuario findById(Integer id) {
        Usuario user;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            user = session.get(Usuario.class, id);
        }
        return user;
    }

    @Override
    public void save(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.persist(usuario);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Usuario usuario) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            usuario = session.get(Usuario.class, usuario.getId());
            session.merge(usuario);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Usuario usuario) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.remove(usuario);
            session.getTransaction().commit();
        }

    }


}
