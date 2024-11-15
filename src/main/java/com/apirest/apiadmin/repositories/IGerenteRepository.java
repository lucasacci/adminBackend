package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.GerenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGerenteRepository extends JpaRepository<GerenteModel, Long> {

}
