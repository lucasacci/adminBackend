package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoryRepository extends JpaRepository<HistoryModel, Long> {
}
