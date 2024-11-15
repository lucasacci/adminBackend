package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "productos")
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
    @JoinColumn(name = "id_recibo")
    private ReciboModel id_recibo;

//    public int getCantidad() {
//        return cantidad;
//    }
//
//    public void setCantidad(int cantidad) {
//        this.cantidad = cantidad;
//    }

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
