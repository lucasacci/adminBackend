package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "ventas")
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

}
