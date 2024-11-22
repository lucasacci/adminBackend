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


}
