package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.services.GerenteService;
import com.apirest.apiadmin.services.VendedorService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private GerenteService gerenteService;

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
    public ResponseEntity<JsonNode> crearVendedor(@RequestBody JsonNode json) {
        try {
            GerenteModel gerente = gerenteService.getGerente(json.get("id_gerente").asLong());

            VendedorModel vendedor = JsonParser.getVendedorFromJson(json, gerente);

            String responseVendedor = vendedorService.guardarVendedor(vendedor);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    responseVendedor,
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            if (responseVendedor.contains("Vendedor registrador exitosamente.")){
                return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al crear el cliente" + e.getMessage(),
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


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

