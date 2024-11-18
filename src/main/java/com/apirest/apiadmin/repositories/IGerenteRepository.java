package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.GerenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGerenteRepository extends JpaRepository<GerenteModel, Long> {
    Optional<GerenteModel> findByDni(String dni);
}
