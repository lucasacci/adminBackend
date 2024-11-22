package com.apirest.apiadmin.models;

import com.apirest.apiadmin.listeners.AuditPaymentListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@EntityListeners(AuditPaymentListener.class)
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayment;

    @Column
    private String paymentMethod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCardDetails")
    private CardDetailsModel cardDetailsModel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCashPaymentDetails")
    private CashPaymentDetailsModel cashPaymentDetailsModel;

    @Column
    private LocalDateTime dateEvent;

    @Column
    private String Operation;

    public PaymentModel(String paymentMethod,
                        Double amount, Double returned,
                        String number, String name, String securityCode, String expirationDate) {
        this.paymentMethod = paymentMethod;
        if (paymentMethod.equals("Efectivo")){
            this.cashPaymentDetailsModel = new CashPaymentDetailsModel(amount, returned);
        }
        if (paymentMethod.equals("Tarjeta")){
            this.cardDetailsModel = new CardDetailsModel(number, name, securityCode, expirationDate);
        }
    }

    public PaymentModel(){}


    @PrePersist
    public void onPrePresist(){
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate(){
        audit("UPDATE");
    }

    public void audit(String operation){
        setOperation(operation);
        setDateEvent(LocalDateTime.now());
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public Long getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Long idPayment) {
        this.idPayment = idPayment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardDetailsModel getCardDetailsModel() {
        return cardDetailsModel;
    }

    public void setCardDetailsModel(CardDetailsModel cardDetailsModel) {
        this.cardDetailsModel = cardDetailsModel;
    }

    public CashPaymentDetailsModel getCashPaymentDetailsModel() {
        return cashPaymentDetailsModel;
    }

    public void setCashPaymentDetailsModel(CashPaymentDetailsModel cashPaymentDetailsModel) {
        this.cashPaymentDetailsModel = cashPaymentDetailsModel;
    }
}
