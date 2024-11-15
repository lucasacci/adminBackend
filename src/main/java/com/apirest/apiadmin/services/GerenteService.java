package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.repositories.IGerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GerenteService {
    @Autowired
    IGerenteRepository gerenteRepository;

    public ArrayList<GerenteModel> getGerentes(){
        return (ArrayList<GerenteModel>) gerenteRepository.findAll();
    }

    public GerenteModel getGerente(Long id){
        return gerenteRepository.findById(id).get();
    }

    public GerenteModel guardarGerente(GerenteModel gerente){
        return gerenteRepository.save(gerente);
    }

    public GerenteModel editGerente(GerenteModel gerente, Long id){
        GerenteModel gerenteEditado = gerenteRepository.findById(id).get();

        gerenteEditado.setNombre(gerente.getNombre());
        gerenteEditado.setApellido(gerente.getApellido());
        gerenteEditado.setEmail(gerente.getEmail());
        gerenteEditado.setDni(gerente.getDni());

        return gerenteRepository.save(gerenteEditado);
    }

    public boolean eliminarGerente(Long id){
        try {
            if (!gerenteRepository.existsById(id)) {
                return false;
            }
            gerenteRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
