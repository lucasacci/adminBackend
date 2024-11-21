package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.services.GerenteService;
import com.apirest.apiadmin.services.ProductoService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    public ResponseEntity<JsonNode> getProductos() {
        List<ProductoModel> productos = productoService.getProductos();
        List<JsonNode> productosJson = new ArrayList<>();
        try {
            productos.forEach( productoModel -> {
                productosJson.add(JsonParser.productToJson(productoModel));
            });

            ApiResponse<List<JsonNode>> response = new ApiResponse<>(
                    "Listado Productos.",
                    productosJson
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Error al obtener pacientes: " + e.getMessage(),
                    null
            );
            JsonNode errorResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProducto(@PathVariable Integer id) {
        Optional<ProductoModel> producto = Optional.ofNullable(productoService.getProducto(id));
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/crearproducto")
    public ResponseEntity<JsonNode> crearProducto(@RequestBody JsonNode json) {
        try {
            GerenteModel gerente = gerenteService.getGerente(json.get("id_gerente").asLong());

            ProductoModel producto = JsonParser.getProductFromJson(json, gerente);

            productoService.guardarProducto(producto);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Producto generado exitosmaente.",
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al cargar el producto" + e.getMessage(),
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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