package com.example.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
