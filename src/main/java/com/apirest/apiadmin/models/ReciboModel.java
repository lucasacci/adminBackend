package com.apirest.apiadmin.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "recibos")
public class ReciboModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_recibo;
    @Column
    private String numero_orden;
    @Column
    private Date fecha_recibo;
    @Column
    private int cantidad;
    @Column
    private double monto;
    @Column
    private double total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vendedor")
    private VendedorModel id_vendedor;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClientModel id_cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_recibo")
    private List<ProductoModel> productos = new ArrayList<ProductoModel>();

    @Transient
    private Map<ProductoModel, Integer> productoCantidad = new HashMap<>();



    public void agregarProducto(ProductoModel producto, int cantidad) {
        boolean encontrado = false;

        for (ProductoModel p : productos) {
            if (p.getId_producto() == producto.getId_producto()) {
                int cantidadActual = productoCantidad.get(p);
                int nuevaCantidad = cantidad + cantidadActual;

                productoCantidad.put(p, nuevaCantidad);
                p.setStock(nuevaCantidad);

                double total = p.getPrecio() + nuevaCantidad;
                p.setPrecio(total);

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            productoCantidad.put(producto, cantidad);
            producto.setStock(cantidad);
            productos.add(producto);
        }
    }

    public void eliminarProducto(ProductoModel producto, int cantidad) {

        if(productoCantidad.containsKey(producto)) {
            int cantidadActual = productoCantidad.get(producto);
            productoCantidad.put(producto, cantidadActual-cantidad);
        }else {
            productoCantidad.put(producto, cantidad);
            productos.remove(producto);
        }

    }

    public int getCantidadProducto() {
        return productos.size();
    }

    public double calcularTotal() {
        return productoCantidad.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrecio() * entry.getValue())
                .sum();
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ProductoModel> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoModel> productos) {
        this.productos = productos;
    }

    public String getNumero_orden() {
        return numero_orden;
    }

    public void setNumero_orden(String numero_orden) {
        this.numero_orden = numero_orden;
    }

    public Integer getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(Integer id_recibo) {
        this.id_recibo = id_recibo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_recibo() {
        return fecha_recibo;
    }

    public void setFecha_recibo(Date fecha_recibo) {
        this.fecha_recibo = fecha_recibo;
    }


    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public VendedorModel getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(VendedorModel id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public ClientModel getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(ClientModel id_cliente) {
        this.id_cliente = id_cliente;
    }


}
