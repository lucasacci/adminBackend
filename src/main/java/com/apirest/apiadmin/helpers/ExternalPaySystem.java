package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.models.PaymentModel;

import java.util.Optional;

public class ExternalPaySystem {

    private PaymentModel payment;

    public ExternalPaySystem(PaymentModel payment){
        this.payment = payment;
    }

    public ExternalPaySystem(){}

    public String pay(){
        return "Pago realizado con exito";
    }
}
