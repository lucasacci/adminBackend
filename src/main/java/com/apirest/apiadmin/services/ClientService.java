package com.apirest.apiadmin.services;

import ch.qos.logback.core.net.server.Client;
import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    IClientRepository clientRepository;

    public ArrayList<ClientModel> getClients(){
        return (ArrayList<ClientModel>) clientRepository.findAll();
    }

    public Optional<ClientModel> getClientByDNI(int dniclient){
        return clientRepository.findByDni(dniclient);
    }

    public String guardarCliente(ClientModel client){
        try {
            if(getClientByDNI(client.getDni()).isPresent()) {
                return "Cliente ya existente. DNI: " + client.getDni();
            }
            clientRepository.save(client);
            return "Cliente Registrado Correctamente.";
        } catch (Exception e) {
            return "Error al registrar el Cliente. Ex: " + e.getMessage();
        }
    }

    public ClientModel getCLiente(Long id){
        return clientRepository.findById(id).get();
    }

    public ClientModel editCliente(ClientModel cliente, Long id){
        ClientModel clienteEditado = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));


        clienteEditado.setNombre(cliente.getNombre());
        clienteEditado.setApellido(cliente.getApellido());
        clienteEditado.setDni(cliente.getDni());
        clienteEditado.setEmail(cliente.getEmail());

        return clientRepository.save(clienteEditado);
    }

    public boolean eliminarCliente(Long id) {
        try {
            if (!clientRepository.existsById(id)) {
                return false;
            }
            clientRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
