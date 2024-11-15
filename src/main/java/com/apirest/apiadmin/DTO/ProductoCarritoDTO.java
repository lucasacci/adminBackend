package com.apirest.apiadmin.DTO;

public class ProductoCarritoDTO {
    private Integer id_producto;
    private Integer cantidad;

    public Integer getIdProducto() {
        return this.id_producto;
    }

    public void setIdProducto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
