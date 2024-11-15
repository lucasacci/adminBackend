package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<ArrayList<ProductoModel>> getProductos() {
        ArrayList<ProductoModel> productos = productoService.getProductos();
        return ResponseEntity.ok().body(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProducto(@PathVariable Integer id) {
        Optional<ProductoModel> producto = Optional.ofNullable(productoService.getProducto(id));
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/crearproducto")
    public ResponseEntity<String> crearProducto(@RequestBody ProductoModel producto) {
        productoService.guardarProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarProducto(@PathVariable Integer id, @RequestBody ProductoModel producto) {
        try{
            ProductoModel updatedProducto = productoService.editProducto(producto, id);
            return ResponseEntity.ok().body("Producto editado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer id) {
        try{
            boolean ok = productoService.eliminarProducto(id);

            if (ok){
                return ResponseEntity.ok().body("Producto eliminado con exito.");
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar gerente");
        }
    }


}