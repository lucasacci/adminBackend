package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vendedores")
public class VendedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_vendedor;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String correo;
    @Column
    private int dni;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gerente")
    private GerenteModel id_gerente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_vendedor")
    private List<ReciboModel> recibos;

    public long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public List<ReciboModel> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<ReciboModel> recibos) {
        this.recibos = recibos;
    }

    public GerenteModel getId_gerente() {
        return id_gerente;
    }

    public void setId_gerente(GerenteModel id_gerente) {
        this.id_gerente = id_gerente;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
}
