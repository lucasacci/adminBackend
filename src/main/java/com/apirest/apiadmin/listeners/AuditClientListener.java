package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditClientListener {

    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditClientListener(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(ClientModel client){
        HistoryModel history = new HistoryModel();
        history.setName("Client. DNI:" + client.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Vendedor: " + client.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(ClientModel client){
        HistoryModel history = new HistoryModel();
        history.setName("Client. DNI:" + client.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Vendedor: " + client.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(ClientModel client){
        HistoryModel history = new HistoryModel();
        history.setName("Client. DNI:" + client.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Vendedor: " + client.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }
}
