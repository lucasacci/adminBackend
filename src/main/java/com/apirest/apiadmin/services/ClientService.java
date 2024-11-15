package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientService {

    @Autowired
    IClientRepository clientRepository;

    public ArrayList<ClientModel> getClients(){
        return (ArrayList<ClientModel>) clientRepository.findAll();
    }

    public ClientModel guardarCliente(ClientModel clientModel){
        return clientRepository.save(clientModel);
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
        clienteEditado.setCorreo(cliente.getCorreo());

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
