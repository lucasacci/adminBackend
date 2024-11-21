package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.VendedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVendedorRepository extends JpaRepository<VendedorModel, Long> {
    Optional<VendedorModel> findByDni(int dni);
}
