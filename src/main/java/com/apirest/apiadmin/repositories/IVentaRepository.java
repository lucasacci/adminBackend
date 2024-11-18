package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<VentaModel, Integer> {

}
