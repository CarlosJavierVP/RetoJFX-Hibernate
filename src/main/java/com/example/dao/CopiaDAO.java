package com.example.dao;

import com.example.models.Copia;
import com.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class CopiaDAO implements DAO<Copia>{

    private static SessionFactory sessionFactory = null;

    public CopiaDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Copia> findAll() {
        List<Copia> copies;
        try(Session session = sessionFactory.openSession()){
            Query<Copia> query = session.createQuery("from Copia", Copia.class);
            copies = query.list();
        }
        return copies;
    }

    @Override
    public Copia findById(Long id) {
        Copia copy;
        try(Session session = sessionFactory.openSession()){
            copy = session.get(Copia.class, id);
        }
        return copy;
    }

    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction(session -> session.persist(copia) );
    }

    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction(session -> session.merge(copia));
    }

    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction(session -> session.remove(copia));
    }


}
