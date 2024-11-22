package com.apirest.apiadmin.listeners;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.HistoryModel;
import com.apirest.apiadmin.models.PaymentModel;
import com.apirest.apiadmin.repositories.IHistoryRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditPaymentListener {
    private final IHistoryRepository historyRepository;

    @Lazy
    public AuditPaymentListener(IHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    @PrePersist
    private void prePersist(PaymentModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Payment: " + object.getPaymentMethod());
        history.setDate(LocalDateTime.now());
        history.setOperation("INSERT");
        history.setUser("");
        this.historyRepository.save(history);
    }

    @PreUpdate
    private void preUpdate(PaymentModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Payment: " + object.getPaymentMethod());
        history.setDate(LocalDateTime.now());
        history.setOperation("UPDATE");
        history.setUser("");
        this.historyRepository.save(history);
    }

    @PreRemove
    private void preRemove(PaymentModel object){
        HistoryModel history = new HistoryModel();
        history.setName("Payment: " + object.getPaymentMethod());
        history.setDate(LocalDateTime.now());
        history.setOperation("DELETE");
        history.setUser("");
        this.historyRepository.save(history);
    }
}
