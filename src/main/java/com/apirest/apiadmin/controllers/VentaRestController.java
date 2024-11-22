package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.ExternalPaySystem;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.*;
import com.apirest.apiadmin.repositories.IEmailService;
import com.apirest.apiadmin.services.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaRestController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/create")
    public ResponseEntity<JsonNode> crearVenta(@RequestBody JsonNode json) {
        try{
            VendedorModel vendedor = vendedorService.getVendedor(JsonParser.vendorIdHeaderVentaFromJson(json));
            ClientModel cliente = clientService.getCLiente(JsonParser.clientIdHeaderVentaFromJson(json));

            PaymentModel payment = JsonParser.paymentFromJson(json);


            VentaModel venta = new VentaModel(
                    JsonParser.DateHeaderVentaFromJson(json),
                    JsonParser.montoTotalHeaderVentaFromJson(json),
                    vendedor,
                    cliente,
                    payment
            );

            Integer nroLineasDeVenta = JsonParser.getLineasDeVentaFromJson(json);

            for (Integer i=0 ; i<nroLineasDeVenta ; i++){
                Integer count          = JsonParser.getCountFromLineFromJson(json, i);
                Integer idProducto     = JsonParser.getProductIDVentaFromJson(json, i);
                Double subTotal        = JsonParser.getSubTotalFromJson(json, i);
                ProductoModel producto = productoService.getProducto(idProducto);
                venta.agregarProducto(producto, count, subTotal);
            }

            ventaService.guardarVenta(venta);

            //Pago
            ExternalPaySystem exPay = new ExternalPaySystem(payment);
            String responsePay = exPay.pay();


            //Envio de mail a Cliente
//            EmailModel email = new EmailModel(
//                    cliente.getEmail(),
//                    "Notificacion de Pago - Sitema venta Admin",
//                    ""
//            );
//            emailService.sendEmail(email,json);

            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Venta Generada exitosamente",
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al Crear la venta" + e.getMessage(),
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
