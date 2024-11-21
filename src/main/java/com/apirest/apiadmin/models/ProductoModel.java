package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditProductoListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@EntityListeners(AuditProductoListener.class)
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_producto;
    @Column
    private String nombre;
    @Column
    private Double precio;
//    @Column
//    private int cantidad;
    @Column
    private int stock;
    @Column
    private String categoria;
    @Column
    private String imagen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gerente")
    private GerenteModel gerente;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public ProductoModel(String nombre, Double precio, int stock, String categoria, GerenteModel gerente) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.gerente = gerente;
    }

    //    public int getCantidad() {
//        return cantidad;
//    }
//
//    public void setCantidad(int cantidad) {
//        this.cantidad = cantidad;
//    }

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

    public GerenteModel getGerente() {
        return gerente;
    }

    public void setGerente(GerenteModel gerente) {
        this.gerente = gerente;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


}
