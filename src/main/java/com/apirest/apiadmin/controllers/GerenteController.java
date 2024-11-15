package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.services.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/gerentes")
public class GerenteController {

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

    @PostMapping("/crearGerente")
    public ResponseEntity<String> crearGerente(@RequestBody GerenteModel gerente) {
        gerenteService.guardarGerente(gerente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Gerente creado con exito.");
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
