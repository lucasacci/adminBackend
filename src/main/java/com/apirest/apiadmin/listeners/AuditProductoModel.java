package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditProductoModel {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditProductoModel(IHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(ProductoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Sericio/Produco: Nombre: " + object.getNombre());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(ProductoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Sericio/Produco: Nombre: " + object.getNombre());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(ProductoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Sericio/Produco: Nombre: " + object.getNombre());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }
}
