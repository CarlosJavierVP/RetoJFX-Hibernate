package com.example.dao;

import com.example.models.dto.CopyDTO;
import com.example.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

/**
 * Clase UsuarioDAO que implementa los metodos DAO de usuario
 * @author Carlos Javier
 */
public class UsuarioDAO implements DAO<Usuario> {
    private static SessionFactory sessionFactory = null;
    public UsuarioDAO (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Metodo findAll para obtener todos los usuarios
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> findAll() {
        List<Usuario> lista;
        try(Session session = sessionFactory.openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u", Usuario.class);
            lista = query.list();
        }
        return lista;
    }

    /**
     * Metodo findByid para obtener un usuario por su id
     * @param id del objeto
     * @return usuario por el id dado
     */
    @Override
    public Usuario findById(Long id) {
        Usuario user;
        try (Session session = sessionFactory.openSession()){
            user = session.get(Usuario.class, id);
        }
        return user;
    }

    /**
     * Metodo save para persistir un nuevo usuario en la base de datos
     * @param usuario el nuevo objeto pasado por parámetro
     */
    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.persist(usuario));
    }

    /**
     * Metodo update para actualizar el usuario en la base de datos
     * @param usuario el objeto modificado
     */
    @Override
    public void update(Usuario usuario) {
        sessionFactory.inTransaction(session -> {
            session.merge(usuario);
            session.flush();
            session.clear();
        });
    }

    /**
     * Metodo delete para eliminar el usuario de la base de datos
     * @param usuario el objeto eliminado
     */
    @Override
    public void delete(Usuario usuario) {
        sessionFactory.inTransaction(session -> {
            session.remove(usuario);
        });
    }

    /**
     * Metodo validateUser para validar el usuario y conectase
     * @param nombre_usuario
     * @param password
     * @return usuario
     */
    public Usuario validateUser (String nombre_usuario, String password){
        try(Session session = sessionFactory.openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u where u.nombreUsuario =:nombreUsuario and u.password =:password", Usuario.class);
            query.setParameter("nombreUsuario", nombre_usuario);
            query.setParameter("password", password);
            return query.getSingleResultOrNull();
        }
    }

    /**
     * Metodo findAllUserCopies metodo para obtener una lista de copias del usuario
     * @param user
     * @return lista de copias
     */
    public List<CopyDTO> findAllUserCopies(Usuario user){
        List<CopyDTO> myCopies;

        try(Session session = sessionFactory.openSession()){
            Query<CopyDTO> query = session.createQuery("select p.titulo, c.estado, c.soporte, c.idCopy, p.idPelicula  from Copia c join c.user u join Pelicula p on c.idPelicula = p.id where u.id =:userid ", CopyDTO.class);
            query.setParameter("userid", user.getIdUsuario());
            myCopies = query.list();
        }
        return myCopies;
    }

    /**
     * Metodo validateNewUser para validar la creación de un nuevo usuario
     * @param user_name
     * @return usuario
     */
    public Usuario validateNewUser(String user_name){
        try(Session session = sessionFactory.openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u where u.nombreUsuario =: username");
            query.setParameter("username", user_name);
            return query.getSingleResultOrNull();
        }
    }

}
