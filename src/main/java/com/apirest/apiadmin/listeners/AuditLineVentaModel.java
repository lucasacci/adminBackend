package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.models.LineVentaModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditLineVentaModel {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditLineVentaModel(IHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(LineVentaModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Linea de Venta: Id:venta" + object.getVenta().getIdVenta());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Vendedor: " + object.getVenta().getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(LineVentaModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Linea de Venta: Id:venta" + object.getVenta().getIdVenta());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Vendedor: " + object.getVenta().getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(LineVentaModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Linea de Venta: Id:venta" + object.getVenta().getIdVenta());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Vendedor: " + object.getVenta().getVendedor().getId_vendedor());
        this.historyRepository.save(history);
    }
}
