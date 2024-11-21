package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.repositories.IVendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    IVendedorRepository vendedorRepository;

    public ArrayList<VendedorModel> getVendedores() {
        return (ArrayList<VendedorModel>) vendedorRepository.findAll();
    }

    public VendedorModel getVendedor(Long id) {
        return vendedorRepository.findById(id).get();
    }

    public String guardarVendedor(VendedorModel vendedor) {
        try {
            if (getVendedorByDNI(vendedor.getDni()).isPresent()){
                return "Vendedor ya registrado. DNI: " + vendedor.getDni();
            }
            vendedorRepository.save(vendedor);
            return "Vendedor registrador exitosamente.";
        } catch (Exception e) {
            return "Error al rergistrar el cliente. Ex: " + e.getMessage();
        }
    }

    private Optional<VendedorModel> getVendedorByDNI(int dni) {
        return vendedorRepository.findByDni(dni);
    }

    public VendedorModel editVendedor(VendedorModel vendedor, Long id) {
        VendedorModel vendedorEditado = vendedorRepository.findById(id).get();

        vendedorEditado.setNombre(vendedor.getNombre());
        vendedorEditado.setCorreo(vendedor.getCorreo());
        vendedorEditado.setDni(vendedor.getDni());
        vendedorEditado.setId_gerente(vendedor.getId_gerente());
        vendedorEditado.setVentas(vendedor.getVentas());
        vendedorEditado.setApellido(vendedor.getApellido());

        return vendedorRepository.save(vendedorEditado);
    }

    public boolean eliminarVendedor(Long id) {
        try {
            if (!vendedorRepository.existsById(id)) {
                return false;
            }
            vendedorRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }




}
