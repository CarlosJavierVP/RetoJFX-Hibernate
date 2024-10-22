package com.example.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
@Data
@Entity
@Table(name = "pelicula", schema = "reto2")
public class Pelicula {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String titulo;

    private String genero;

    private Integer año;

    private String descripcion;

    private String director;

    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula that = (Pelicula) o;
        return id == that.id && Objects.equals(titulo, that.titulo) && Objects.equals(genero, that.genero) && Objects.equals(año, that.año) && Objects.equals(descripcion, that.descripcion) && Objects.equals(director, that.director) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, genero, año, descripcion, director, imageUrl);
    }
}
