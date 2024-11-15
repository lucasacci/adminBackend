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
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String email;
    @Column
    private int dni;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_gerente")
    private List<VendedorModel> vendedorModelList;

    public long getId_gerente() {
        return id_gerente;
    }

    public void setId_gerente(long id_gerente) {
        this.id_gerente = id_gerente;
    }

    public List<VendedorModel> getVendedorModelList() {
        return vendedorModelList;
    }

    public void setVendedorModelList(List<VendedorModel> vendedorModelList) {
        this.vendedorModelList = vendedorModelList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
}
