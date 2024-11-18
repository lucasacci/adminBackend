package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.repositories.IGerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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

    public Optional<GerenteModel> getGerenteByDNI(String dniGerente){
        return gerenteRepository.findByDni(dniGerente);
    }

    public String guardarGerente(GerenteModel gerente){
        try {
            if(getGerenteByDNI(gerente.getDni()).isPresent()) {
                return "Gerente ya existente. DNI: " + gerente.getDni();
            }
            gerenteRepository.save(gerente);
            return "Gerente Registrado Correctamente.";
        } catch (Exception e) {
            return "Error al registrar el gerente. Ex: " + e.getMessage();
        }
    }

    public GerenteModel editGerente(GerenteModel gerente, Long id){
        GerenteModel gerenteEditado = gerenteRepository.findById(id).get();

        gerenteEditado.setNombre(gerente.getNombre());
        gerenteEditado.setApellido(gerente.getApellido());
        gerenteEditado.setEmail(gerente.getEmail());
        gerenteEditado.setDni(gerente.getDni());
        gerenteEditado.setDireccion(gerente.getDireccion());

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
