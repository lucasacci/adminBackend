package com.apirest.apiadmin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayment;

    @Column
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "idCardDetails")
    private CardDetailsModel cardDetailsModel;

    @ManyToOne
    @JoinColumn(name = "idCashPaymentDetails")
    private CashPaymentDetailsModel cashPaymentDetailsModel;

    public PaymentModel(Long idPayment, PaymentMethod paymentMethod, CardDetailsModel cardDetailsModel, CashPaymentDetailsModel cashPaymentDetailsModel) {
        this.idPayment = idPayment;
        this.paymentMethod = paymentMethod;
        this.cardDetailsModel = cardDetailsModel;
        this.cashPaymentDetailsModel = cashPaymentDetailsModel;
    }
}

enum PaymentMethod {
    EFECTIVO, TARJETA
}