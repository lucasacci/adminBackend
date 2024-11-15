package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<ArrayList<VendedorModel>> getVendedores() {
        ArrayList<VendedorModel> vendedores = vendedorService.getVendedores();
        return ResponseEntity.ok().body(vendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorModel> getVendedor(@PathVariable Long id) {
        Optional<VendedorModel> vendedor = Optional.ofNullable(vendedorService.getVendedor(id));

        return vendedor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/crearvendedor")
    public ResponseEntity<String> crearVendedor(@RequestBody VendedorModel vendedor) {
        vendedorService.guardarVendedor(vendedor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor creado con exito.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarVendedor(@PathVariable Long id, @RequestBody VendedorModel vendedor) {
        try{
            VendedorModel updatedVendedor = vendedorService.editVendedor(vendedor, id);
            return ResponseEntity.ok().body("Vendedor editado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVendedor(@PathVariable Long id) {
        try{
            boolean ok = vendedorService.eliminarVendedor(id);

            if (ok){
                return ResponseEntity.ok("Vendedor eliminado con exito.");
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor no encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar vendedor");
        }
    }

}

