package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.DescuentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDescuentosRepository extends JpaRepository<DescuentoModel, Long> {
}
