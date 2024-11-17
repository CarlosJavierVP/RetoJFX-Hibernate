package com.example.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase Pelicula modelo que recoge los datos de la Pelicula
 * @author Carlos Javier
 */
@Data
@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long idPelicula;
    private String titulo;
    private String genero;
    private Integer año;
    private String descripcion;
    private String director;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "teaser_url")
    private String teaserUrl;

    /**
     * Metodo toString
     * @return cadena de String
     */
    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", año=" + año +
                ", descripcion='" + descripcion + '\'' +
                ", director='" + director + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
