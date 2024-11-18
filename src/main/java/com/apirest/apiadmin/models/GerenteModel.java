package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "gerentes")
public class GerenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_gerente;

    @Column
    private String dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private String direccion;

    public GerenteModel(String dni, String nombre, String apellido, String email, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
    }

    public GerenteModel(){}

    public long getId_gerente() {
        return id_gerente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
