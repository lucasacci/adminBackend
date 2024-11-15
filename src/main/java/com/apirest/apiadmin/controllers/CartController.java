package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ProductoCarritoDTO;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.models.ReciboModel;
import com.apirest.apiadmin.services.ProductoService;
import com.apirest.apiadmin.services.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReciboService reciboService;

    ReciboModel recibo = new ReciboModel();

    @PostMapping("/carrito")
    public ResponseEntity<String> agregarItem(@RequestBody ProductoCarritoDTO producto) {

        if ( producto.getIdProducto() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id del producto y la cantidad son obligatorios.");
        }

        reciboService.obtenerProducto(producto);
        // Obtener el producto
        ProductoModel producto_db = productoService.getProducto(producto.getIdProducto());
        int cantidad = producto.getCantidad();
        double precio = producto_db.getPrecio();
        // Verificar si el producto tiene suficiente stock
        if (producto_db.getStock() < cantidad) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Stock insuficiente. Disponible: " + producto_db.getStock());
        }else {
            producto_db.setStock(producto_db.getStock() - cantidad);
            productoService.editProducto(producto_db, producto.getIdProducto());
            recibo.agregarProducto(producto_db, cantidad);
            return ResponseEntity.ok("Producto agregado al carrito.");
        }
    }

    @GetMapping("/carrito")
    public ResponseEntity<ReciboModel> verCarrito() {
        recibo.setCantidad(recibo.getCantidadProducto());
        recibo.getProductos();
        recibo.setTotal(recibo.calcularTotal());
        return ResponseEntity.ok(recibo);
    }

    @DeleteMapping("/carrito")
    public ResponseEntity<String> eliminarItem(@RequestParam ProductoModel producto) {
        // obtengo el producto
        ProductoModel x = productoService.getProducto(producto.getId_producto());

        recibo.eliminarProducto(producto, x.getStock());
        x.setStock(x.getStock() + producto.getStock());

        return ResponseEntity.ok("Producto eliminado.");
    }


}
