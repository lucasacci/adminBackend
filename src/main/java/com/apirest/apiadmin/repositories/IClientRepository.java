package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IClientRepository extends JpaRepository<ClientModel, Long> {

}
