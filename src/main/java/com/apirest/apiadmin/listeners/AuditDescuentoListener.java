package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditDescuentoListener {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditDescuentoListener(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(DescuentoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Descuento: " + object.getDescripcion() +
                " Porcentaje: " + object.getPorcentajeDescuento());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(DescuentoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Descuento: " + object.getDescripcion() +
                " Porcentaje: " + object.getPorcentajeDescuento());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(DescuentoModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Descuento: " + object.getDescripcion() +
                " Porcentaje: " + object.getPorcentajeDescuento());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Gerente: " + object.getGerente().getId_gerente());
        this.historyRepository.save(history);
    }
}
