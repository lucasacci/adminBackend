package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditVentaListener;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "ventas")
@EntityListeners(AuditVentaListener.class)
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column
    private Date fechaVenta;

    @Column
    private Double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vendedor")
    private VendedorModel vendedor;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClientModel cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "venta")
    private List<LineVentaModel> lineasDeVenta = new ArrayList<LineVentaModel>();

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public VentaModel(Date fechaVenta, Double montoTotal, VendedorModel vendedor, ClientModel cliente){
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.vendedor = vendedor;
        this.cliente = cliente;
    }

    public VentaModel(){}

    public void agregarProducto(ProductoModel producto) {
        LineVentaModel linea = new LineVentaModel(producto.getPrecio(),this,producto);
        this.lineasDeVenta.add(linea);
    }

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

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public VendedorModel getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorModel vendedor) {
        this.vendedor = vendedor;
    }

    public ClientModel getCliente() {
        return cliente;
    }

    public void setCliente(ClientModel cliente) {
        this.cliente = cliente;
    }

    public List<LineVentaModel> getLineasDeVenta() {
        return lineasDeVenta;
    }

    public void setLineasDeVenta(List<LineVentaModel> lineasDeVenta) {
        this.lineasDeVenta = lineasDeVenta;
    }

}
