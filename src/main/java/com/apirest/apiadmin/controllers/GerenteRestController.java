package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.services.GerenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/gerentes")
public class GerenteRestController {

    @Autowired
    private GerenteService gerenteService;


    @GetMapping
    public ResponseEntity<ArrayList<GerenteModel>> getGerentes() {
        ArrayList<GerenteModel> gerentes = gerenteService.getGerentes();
        return ResponseEntity.ok().body(gerentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GerenteModel> getGerente(@PathVariable Long id) {
        Optional<GerenteModel> gerente = Optional.ofNullable(gerenteService.getGerente(id));

        return gerente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/add")
    public ResponseEntity<JsonNode> crearGerente(@RequestBody JsonNode jsonGerente) {

        try {
            GerenteModel gerente = JsonParser.gerenteFromJson(jsonGerente);
            String responseGerente = gerenteService.guardarGerente(gerente);


            ApiResponse<JsonNode> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    responseGerente,
                    new ObjectMapper().createObjectNode()
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            ApiResponse<JsonNode> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al Crear Gerente: " + ex.getMessage(),
                    null);

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarGerente(@PathVariable Long id, @RequestBody GerenteModel gerente) {
        try {
            GerenteModel updatedGerente = gerenteService.editGerente(gerente, id);
            return ResponseEntity.ok().body("Gerente editado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGerente(@PathVariable Long id) {
        try {
            boolean ok = gerenteService.eliminarGerente(id);

            if (ok) {
                return ResponseEntity.ok().body("Gerente eliminado con exito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar gerente");
        }
    }




}
