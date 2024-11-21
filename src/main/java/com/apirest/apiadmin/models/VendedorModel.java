package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditVendedorListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendedores")
@EntityListeners(AuditVendedorListener.class)
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

    @Column
    private String direccion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gerente")
    private GerenteModel id_gerente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vendedor")
    private List<VentaModel> ventas;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public VendedorModel(String nombre, String apellido, String correo, int dni, String direccion, GerenteModel id_gerente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.dni = dni;
        this.id_gerente = id_gerente;
        this.direccion = direccion;
    }

    public VendedorModel(){};

    @PrePersist
    public void onPrePresist(){
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate(){
        audit("UPDATE");
    }

    public void audit(String operation){
        setOperation(operation);
        setDateEvent(LocalDateTime.now());
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public List<VentaModel> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaModel> ventas) {
        this.ventas = ventas;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
