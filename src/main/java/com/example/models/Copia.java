package com.example.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;
@Data
@Entity
@Table(name = "copia", schema = "reto2")
public class Copia {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private Object estado;

    private Object soporte;

    @Column(name = "id_pelicula")
    private Integer idPelicula;

    @Column(name = "id_usuario")
    private Integer idUsuario;

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
