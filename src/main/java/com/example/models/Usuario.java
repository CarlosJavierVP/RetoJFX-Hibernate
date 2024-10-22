package com.example.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "reto2", catalog = "")
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "is_admin")
    private Byte isAdmin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
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
