package com.apirest.apiadmin.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistory;
    private String name;
    private String operation;
    private String user;
    private LocalDateTime date;

    public HistoryModel(Long idHistory, String name, String operation, String user, LocalDateTime date) {
        this.idHistory = idHistory;
        this.name = name;
        this.operation = operation;
        this.user = user;
        this.date = date;
    }

    public HistoryModel(){}

    public Long getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
