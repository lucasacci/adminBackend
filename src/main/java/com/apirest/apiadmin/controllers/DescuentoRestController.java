package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.services.DescuentosService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Frequency;
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

    @PostMapping("/add")
    public ResponseEntity<JsonNode> addDescuento(@RequestBody JsonNode jsonDescuento){
        try {
            DescuentoModel descuento = JsonParser.descuentoFromJson(jsonDescuento);
            String responseDescuento = descuentosService.saveDescuento(descuento);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    responseDescuento,
                    new ObjectMapper().createObjectNode()
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            ApiResponse<JsonNode> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al Crear Descuento: " + ex.getMessage(),
                    null);

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<JsonNode> getDescuentos() {
        List<DescuentoModel> descuentos = descuentosService.getDescuentos();
        List<JsonNode> descuentosJson = new ArrayList<>();

        try {
            descuentos.forEach(
            descuentoModel -> {
                descuentosJson.add(JsonParser.descuentosToJson(descuentoModel));
            });

            ApiResponse<List<JsonNode>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Obtener Descuentos.",
                    descuentosJson
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "Error al obtener descuentos: " + e.getMessage(),
                    null);

            JsonNode errorResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
