package com.example.dao;

import com.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

/**
 * Clase PeliculaDAO que recoge los metodos DAO de pelicula
 * @author Carlos Javier
 */
public class PeliculaDAO implements DAO<Pelicula>{
    private SessionFactory sessionFactory = null;

    public PeliculaDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Metodo findAll para obtener todas las peliculas
     * @return lista de peliculas
     */
    @Override
    public List<Pelicula> findAll() {
        List<Pelicula> movies;
        try(Session session = sessionFactory.openSession()){
            Query<Pelicula> query = session.createQuery("from Pelicula ", Pelicula.class);
            movies = query.list();
        }
        return movies;
    }

    /**
     * Metodo findById para obtener una pelicula por su id
     * @param id del objeto
     * @return pelicula con el id dado
     */
    @Override
    public Pelicula findById(Long id) {
        Pelicula peli;
        try(Session session = sessionFactory.openSession()){
            peli = session.get(Pelicula.class, id);
        }
        return peli;
    }

    /**
     * Metodo save para persistir la nueva pelicula en la base de datos
     * @param pelicula el nuevo objeto pasado por parámetro
     */
    @Override
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.persist(pelicula));
    }

    /**
     * Metodo update para actualizar la pelicula
     * @param pelicula el objeto modificado
     */
    @Override
    public void update(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.merge(pelicula));
    }

    /**
     * Metodo delete para eliminar la pelicula de la base de datos
     * @param pelicula el objeto eliminado
     */
    @Override
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.remove(pelicula));
    }

    /**
     * Metodo findByTitle para obtener una pelicula por su titulo
     * @param title
     * @return pelicula por su titulo dado
     */
    public Pelicula findByTitle (String title){
        Pelicula peli;
        try(Session session = sessionFactory.openSession()) {
            Query<Pelicula> query = session.createQuery("select p from Pelicula p where p.titulo =: titulo", Pelicula.class);
            query.setParameter("titulo", title);
            peli = query.getSingleResultOrNull();
        }
        return peli;
    }


}
