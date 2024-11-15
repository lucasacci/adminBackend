package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.ReciboModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReciboRepository extends JpaRepository<ReciboModel, Integer> {

}
