package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.services.DescuentosService;
import com.apirest.apiadmin.services.GerenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/descuentos")
public class DescuentoRestController {
    @Autowired
    private DescuentosService descuentosService;

    @Autowired
    private GerenteService gerenteService;

    @PostMapping("/add/{idGerente}")
    public ResponseEntity<JsonNode> addDescuento(@PathVariable Long idGerente, @RequestBody JsonNode jsonDescuento){
        try {
            GerenteModel gerente = gerenteService.getGerente(idGerente);
            DescuentoModel descuento = JsonParser.descuentoFromJson(jsonDescuento, gerente);
            String responseDescuento = descuentosService.saveDescuento(descuento);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    responseDescuento,
                    new ObjectMapper().createObjectNode()
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al Crear Descuento: " + ex.getMessage(),
                    null);

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<JsonNode> getDescuentos(@RequestParam Boolean onlyValid ) {

        List<DescuentoModel> descuentos = descuentosService.getDescuentos();
        List<JsonNode> descuentosJson = new ArrayList<>();

        try {
            descuentos.forEach(
            descuentoModel -> {
                if (onlyValid.equals(true) && descuentoModel.isValid()){
                    descuentosJson.add(JsonParser.descuentosToJson(descuentoModel));
                } else if (onlyValid.equals(false)){
                    descuentosJson.add(JsonParser.descuentosToJson(descuentoModel));
                }
            });

            ApiResponse<List<JsonNode>> response = new ApiResponse<>(
                    "Obtener Descuentos.",
                    descuentosJson
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Error al obtener descuentos: " + e.getMessage(),
                    null);

            JsonNode errorResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
