package com.example.dao;

import com.example.dto.CopyDTO;
import com.example.models.Copia;
import com.example.models.Pelicula;
import com.example.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    private static SessionFactory sessionFactory = null;
    public UsuarioDAO (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> lista;
        try(Session session = sessionFactory.openSession()){
            Query<Usuario> query = session.createQuery("select u from Usuario u", Usuario.class);
            lista = query.list();
        }
        return lista;
    }

    @Override
    public Usuario findById(Integer id) {
        Usuario user;
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
            Query<Usuario> query = session.createQuery("select u from Usuario u where u.nombreUsuario =:nombreUsuario and u.password =:password", Usuario.class);
            query.setParameter("nombreUsuario", nombre_usuario);
            query.setParameter("password", password);
            return query.getSingleResultOrNull();
        }
    }

    public List<CopyDTO> findAllUserCopies(Usuario user){
        List<CopyDTO> myCopies;

        try(Session session = sessionFactory.openSession()){
            Query<CopyDTO> query = session.createQuery("select c, p.titulo from Copia c join c.user u join Pelicula p on c.idPelicula = p.id where u.id =:userid ", CopyDTO.class);
            query.setParameter("userid", user.getIdUsuario());
            myCopies = query.list();
        }
        return myCopies;
    }

//"select p.titulo, u.misCopias from Usuario u join fetch Pelicula p where  u.id =:userid ", CopyDTO.class

//"select c from Copia c where c.user.id =:userid ", CopyDTO.class
//"select u.misCopias from Usuario u where u.id  =: userid "

}
