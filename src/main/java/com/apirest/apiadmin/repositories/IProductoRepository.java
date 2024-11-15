package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<ProductoModel, Integer> {





}
