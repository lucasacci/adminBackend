package com.apirest.apiadmin.services;

import com.apirest.apiadmin.models.VentaModel;
import com.apirest.apiadmin.repositories.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    IVentaRepository ventaRepository;

    public VentaModel guardarVenta(VentaModel venta) {
        return ventaRepository.save(venta);
    }

    public List<VentaModel> listarventas() {
        return ventaRepository.findAll();
    }

    /*
    public String generarRecibo() {
        int numero = 0;
        String numeroFinalRecibo = "";

        List<VentaModel> recibos = listarRecibos();

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
    */







}
