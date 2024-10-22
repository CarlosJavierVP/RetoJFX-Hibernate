package com.example.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pelicula", schema = "reto2", catalog = "")
public class Pelicula {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "año")
    private Integer año;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "director")
    private String director;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
