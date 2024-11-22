package com.apirest.apiadmin.controllers;

import ch.qos.logback.core.net.server.Client;
import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.services.ClientService;
import com.apirest.apiadmin.services.VendedorService;
import com.apirest.apiadmin.services.VentaService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<JsonNode> getClients() {
        List<ClientModel> clients = clientService.getClients();
        List<JsonNode> clientsJson = new ArrayList<>();
        try {
            clients.forEach( clientModel -> {
                clientsJson.add(JsonParser.clientToJson(clientModel));
            });

            ApiResponse<List<JsonNode>> response = new ApiResponse<>(
                    "Listado Clientes.",
                    clientsJson
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Error al obtener Clientes: Ex: " + e.getMessage(),
                    null
            );
            JsonNode errorResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/crearcliente")
    public ResponseEntity<JsonNode> guardarCliente(@RequestBody JsonNode json) {
        try {
            VendedorModel vendedor = vendedorService.getVendedor(JsonParser.vendorIdHeaderVentaFromJson(json));

            ClientModel client = JsonParser.getClientFromJson(json, vendedor);

            String responseCliente = clientService.guardarCliente(client);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    responseCliente,
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al crear el cliente" + e.getMessage(),
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonNode> getCliente(@PathVariable Long id) {
        try{
            ClientModel client = clientService.getCLiente(id);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Vendedor encontrado",
                    JsonParser.clientToJson(client)
            );
            JsonNode jsonResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al buscar el Vendedor: " + e.getMessage(),
                    null
            );
            JsonNode jsonResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> editCliente(@RequestBody ClientModel cliente, @PathVariable Long id) {
        try {
            ClientModel updatedCliente = clientService.editCliente(cliente, id);
            return ResponseEntity.ok("Cliente actualizado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el cliente.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        try {
            boolean ok = this.clientService.eliminarCliente(id);

            if (ok) {
                return ResponseEntity.ok("Cliente eliminado con exito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el cliente.");
        }
    }

}



