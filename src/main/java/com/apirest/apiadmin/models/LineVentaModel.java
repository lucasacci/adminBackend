package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditLineVentaListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lineaDeVenta")
@EntityListeners(AuditLineVentaListener.class)
public class LineVentaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLineVenta;

    @Column
    private Double precioVenta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idVenta")
    private VentaModel venta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_producto")
    private ProductoModel producto;

    @Column
    private Integer count;

    @Column
    private Double subTotal;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public LineVentaModel(Double precioVenta, Integer count, Double subtotal, VentaModel venta, ProductoModel producto){
        this.precioVenta = precioVenta;
        this.venta = venta;
        this.producto = producto;
        this.count = count;
        this.subTotal = subtotal;
    }

    public LineVentaModel(Double precioVenta, Integer count, Double subtotal, ProductoModel producto){
        this.precioVenta = precioVenta;
        this.producto = producto;
        this.count = count;
        this.subTotal = subtotal;
    }

    public LineVentaModel(){}

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

    public Long getIdLineVenta() {
        return idLineVenta;
    }

    public void setIdLineVenta(Long idLineVenta) {
        this.idLineVenta = idLineVenta;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public VentaModel getVenta() {
        return venta;
    }

    public void setVenta(VentaModel venta) {
        this.venta = venta;
    }

    public ProductoModel getProducto() {
        return producto;
    }

    public void setProducto(ProductoModel producto) {
        this.producto = producto;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
