package com.example.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@Entity
@Table(name = "usuario", schema = "reto2")
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    private String password;
    @Column(name = "is_admin")
    private Byte isAdmin;

    @OneToMany(mappedBy = "user")
    private List<Copia> misCopias = new ArrayList<>(0);


    public void addCopy(Copia c){
        c.setUser(this);
        this.misCopias.add(c);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario that = (Usuario) o;
        return id == that.id && Objects.equals(nombreUsuario, that.nombreUsuario) && Objects.equals(password, that.password) && Objects.equals(isAdmin, that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreUsuario, password, isAdmin);
    }
}
