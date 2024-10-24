package com.example.dao;


import com.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PeliculaDAO implements DAO<Pelicula>{

    private SessionFactory sessionFactory = null;

    public PeliculaDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Pelicula> findAll() {
        List<Pelicula> movies;
        try(Session session = sessionFactory.openSession()){
            Query<Pelicula> query = session.createQuery("from Pelicula ", Pelicula.class);
            movies = query.list();
        }
        return movies;
    }

    @Override
    public Pelicula findById(Integer id) {
        Pelicula peli;
        try(Session session = sessionFactory.openSession()){
            peli = session.get(Pelicula.class, id);
        }

        return peli;
    }

    @Override
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.persist(pelicula));
    }

    @Override
    public void update(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.merge(pelicula));
    }

    @Override
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.remove(pelicula));
    }


}
