package com.example.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "copia", schema = "reto2", catalog = "")
public class Copia {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "estado")
    private Object estado;
    @Basic
    @Column(name = "soporte")
    private Object soporte;
    @Basic
    @Column(name = "id_pelicula")
    private Integer idPelicula;
    @Basic
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getEstado() {
        return estado;
    }

    public void setEstado(Object estado) {
        this.estado = estado;
    }

    public Object getSoporte() {
        return soporte;
    }

    public void setSoporte(Object soporte) {
        this.soporte = soporte;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copia that = (Copia) o;
        return id == that.id && Objects.equals(estado, that.estado) && Objects.equals(soporte, that.soporte) && Objects.equals(idPelicula, that.idPelicula) && Objects.equals(idUsuario, that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estado, soporte, idPelicula, idUsuario);
    }
}
