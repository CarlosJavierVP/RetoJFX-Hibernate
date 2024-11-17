package com.example.dao;

import com.example.models.Copia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

/**
 * Clase CopiaDAO que recoge los metodos DAO de copia
 * @author Carlos Javier
 */
public class CopiaDAO implements DAO<Copia>{
    private static SessionFactory sessionFactory = null;

    public CopiaDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Metodo findAll para obtener todas las copias
     * @return lista de copias
     */
    @Override
    public List<Copia> findAll() {
        List<Copia> copies;
        try(Session session = sessionFactory.openSession()){
            Query<Copia> query = session.createQuery("from Copia", Copia.class);
            copies = query.list();
        }
        return copies;
    }

    /**
     * Metodo findById para obtener una copia por su id
     * @param id del objeto
     * @return copia con el id dado
     */
    @Override
    public Copia findById(Long id) {
        Copia copy;
        try(Session session = sessionFactory.openSession()){
            copy = session.get(Copia.class, id);
        }
        return copy;
    }

    /**
     * Metodo save para persistir el nuevo objeto en la base de datos
     * @param copia el nuevo objeto pasado por parámetro
     */
    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction(session -> session.persist(copia) );
    }

    /**
     * Metodo update para actualizar la copia en la base de datos
     * @param copia el objeto modificado
     */
    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction(session -> session.merge(copia));
    }

    /**
     * Metodo delete para eliminar la copia
     * @param copia el objeto eliminado
     */
    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction(session -> session.remove(copia));
    }





}
