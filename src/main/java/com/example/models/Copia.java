package com.example.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Copia modelo que recoge los datos de la Copia
 * @author Carlos Javier
 */
@Data
@Entity
@Table(name = "copia")
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long idCopy;
    private String estado;
    private String soporte;
    @Column(name ="id_pelicula")
    private Long idPelicula;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario user;

    /**
     * Metodo toString
     * @return cadena String
     */
    @Override
    public String toString() {
        return "Copia{" +
                "id=" + idCopy +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                ", peli=" + idPelicula +
                ", user=" + user.getNombreUsuario() +
                '}';
    }
}
