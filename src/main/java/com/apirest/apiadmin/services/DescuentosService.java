package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.repositories.IDescuentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DescuentosService {
    @Autowired
    IDescuentosRepository descuentosRepository;

    public String saveDescuento(DescuentoModel descuento) {
        try {
            descuentosRepository.save(descuento);
            return "Descuento Guardado exitosamente.";
        } catch (Exception ex) {
            return "Error al guardar el Descuento. EX: " + ex.getMessage();
        }
    }

    public List<DescuentoModel> getDescuentos() {
        List<DescuentoModel> descuentos = new ArrayList<>();
        descuentosRepository.findAll().forEach(descuentoModel -> {descuentos.add(descuentoModel);});
        return descuentos;
    }
}
