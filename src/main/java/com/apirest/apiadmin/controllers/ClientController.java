package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<ArrayList<ClientModel>> getClients() {
        ArrayList<ClientModel> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/crearcliente")
    public ResponseEntity<String> guardarCliente(@RequestBody ClientModel client) {
        clientService.guardarCliente(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con éxito.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientModel> getCliente(@PathVariable Long id) {
        Optional<ClientModel> cliente = Optional.ofNullable(clientService.getCLiente(id));

        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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



