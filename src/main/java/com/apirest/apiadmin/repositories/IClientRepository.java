package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<ClientModel, Long> {
    Optional<ClientModel> findByDni(int dni);
}
