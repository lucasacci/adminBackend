package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditVendedorListener {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditVendedorListener(IHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(VendedorModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Vendedor: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("ID Gerente: " + object.getId_gerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(VendedorModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Vendedor: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("ID Gerente: " + object.getId_gerente().getId_gerente());
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(VendedorModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Vendedor: Nombre: " + object.getNombre() +
                " Apellido: " + object.getApellido() +
                " DNI: " + object.getDni());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("ID Gerente: " + object.getId_gerente().getId_gerente());
        this.historyRepository.save(history);
    }
}
