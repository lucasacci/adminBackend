package com.apirest.apiadmin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CashPaymentDetails")
public class CashPaymentDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCashPaymentDetails;

    @Column
    private Double amount;

    @Column
    private Double returned;

    public CashPaymentDetailsModel(Double amount, Double returned) {
        this.amount = amount;
        this.returned = returned;
    }

    public CashPaymentDetailsModel(){}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getReturned() {
        return returned;
    }

    public void setReturned(Double returned) {
        this.returned = returned;
    }
}
