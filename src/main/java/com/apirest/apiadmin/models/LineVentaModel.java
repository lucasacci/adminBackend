package com.apirest.apiadmin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lineaDeVenta")
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

    public LineVentaModel(Double precioVenta, VentaModel venta, ProductoModel producto){
        this.precioVenta = precioVenta;
        this.venta = venta;
        this.producto = producto;
    }

    public LineVentaModel(Double precioVenta, ProductoModel producto){
        this.precioVenta = precioVenta;
        this.producto = producto;
    }

    public LineVentaModel(){}
}
