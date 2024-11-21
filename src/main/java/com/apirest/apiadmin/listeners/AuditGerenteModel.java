package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditGerenteModel {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditGerenteModel(IHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(GerenteModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Gerente: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Admin: " + "1");
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(GerenteModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Gerente: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Admin: " + "1");
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(GerenteModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Gerente: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Admin: " + "1");
        this.historyRepository.save(history);
    }
}
