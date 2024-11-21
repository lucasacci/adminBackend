package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditClientListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "clientes")
@EntityListeners(AuditClientListener.class)
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_cliente;

    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String email;
    @Column
    private int dni;

    @Column
    private String direccion;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<VentaModel> ventas;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vendedor")
    private VendedorModel vendedor;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public ClientModel(int dni, String nombre, String apellido, String email, String direccion, VendedorModel vendedor) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.vendedor = vendedor;
        this.direccion = direccion;
    }

    public ClientModel(){}

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

    public List<VentaModel> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaModel> ventas) {
        this.ventas = ventas;
    }

    public VendedorModel getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorModel vendedor) {
        this.vendedor = vendedor;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
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
}
