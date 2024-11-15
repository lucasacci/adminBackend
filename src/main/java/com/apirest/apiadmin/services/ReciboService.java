package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.models.ReciboModel;
import com.apirest.apiadmin.repositories.IReciboRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReciboService {

    IReciboRepository reciboRepository;

    public ReciboModel guardarRecibo(ReciboModel recibo) {
        return reciboRepository.save(recibo);
    }

    public List<ReciboModel> listarRecibos() {
        return reciboRepository.findAll();
    }

    public String generarRecibo() {
        int numero = 0;
        String numeroFinalRecibo = "";

        List<ReciboModel> recibos = listarRecibos();

        List<Integer> numeros = new ArrayList<Integer>();

        recibos.stream().forEach(recibo -> {
            numeros.add(Integer.parseInt(recibo.getNumero_orden()));
        });

        if(recibos.isEmpty()){
            numero = 1;
        }else{
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero<10){
            numeroFinalRecibo = "000000000"+String.valueOf(numero);
        }else if(numero<100){
            numeroFinalRecibo = "00000000"+String.valueOf(numero);
        }

        return numeroFinalRecibo;
    }








}
