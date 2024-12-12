package com.example.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Usuario modelo que recoge los datos de Usuario
 * @author Carlos Javier
 */
@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long idUsuario;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String password;
    @Column(name = "is_admin")
    private Byte isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Copia> misCopias = new ArrayList<>(0);

    /**
     * Metodo addCopy para añadir una copia a la lista de copias del usuario
     * @param c copia
     */
    public void addCopy(Copia c){
        c.setUser(this);
        this.misCopias.add(c);
    }

    /**
     * Metodo toString
     * @return cadena de string
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", misCopias=" + misCopias +
                '}';
    }
}
