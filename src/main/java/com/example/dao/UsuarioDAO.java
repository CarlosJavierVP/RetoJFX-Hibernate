package com.example.dao;


import com.example.HibernateUtil;
import com.example.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    private static SessionFactory sessionFactory = null;
    public UsuarioDAO (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> lista = new ArrayList<>(0);

        try(Session session = sessionFactory.openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u", Usuario.class);
            lista = query.list();
        }
        return lista;
    }

    @Override
    public Usuario findById(Integer id) {
        Usuario user = null;

        try (Session session = sessionFactory.openSession()){
            user = session.get(Usuario.class, id);
        }
        return user;
    }

    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.persist(usuario));
    }

    @Override
    public void update(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.merge(usuario));
    }

    @Override
    public void delete(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.remove(usuario));
    }

    public Usuario validateUser (String nombre_usuario, String password){
        try(Session session = sessionFactory.openSession()){
            Query<Usuario> q = session.createQuery("from Usuario where nombreUsuario =:nombre_usuario and password =:password", Usuario.class);
            q.setParameter("nombreUsuario", nombre_usuario);
            q.setParameter("password", password);
            return q.getSingleResultOrNull();
        }
    }


}
