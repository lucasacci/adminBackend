package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.models.VentaModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditVentaListener {

    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditVentaListener(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(VentaModel venta){
        HistoryModel history = new HistoryModel();
        history.setName("Venta. DNI Cliente: " + venta.getCliente().getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Vendedor: " + venta.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(VentaModel venta){
        HistoryModel history = new HistoryModel();
        history.setName("Venta. DNI Cliente: " + venta.getCliente().getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Vendedor: " + venta.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(VentaModel venta){
        HistoryModel history = new HistoryModel();
        history.setName("Venta. DNI Cliente: " + venta.getCliente().getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Vendedor: " + venta.getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }
}
