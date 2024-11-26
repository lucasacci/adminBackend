package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.ExternalPaySystem;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.*;
import com.apirest.apiadmin.repositories.IEmailService;
import com.apirest.apiadmin.services.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private DescuentosService descuentosService;

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

            if (json.has("idDescuento") && !json.get("idDescuento").isNull()) {
                venta.setDescuento(
                        descuentosService.getDescuentoById(
                                json.get("idDescuento").asLong())
                );
            }

            ventaService.guardarVenta(venta);

            //Pago
            ExternalPaySystem exPay = new ExternalPaySystem(payment);
            String responsePay = exPay.pay();


            //Envio de mail a Cliente
            JsonNode jsonToEmail = JsonParser.ventaToEmailJson(venta);
            EmailModel email = new EmailModel(
                    cliente.getEmail(),
                    "Notificacion de Pago - Sistema venta Admin",
                    responsePay
            );
            emailService.sendEmail(
                    email,
                    jsonToEmail,
                    venta.getPayment().getPaymentMethod()
            );

            //Response
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Venta Generada exitosamente",
                    null
            );
            JsonNode jsonResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(
                    "Error al Crear la venta. Ex:" + e.getMessage(),
                    null
            );
            JsonNode jsonResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<JsonNode> getVentas(){
        List<VentaModel> ventas = ventaService.listarventas();
        List<JsonNode> ventasJson = new ArrayList<>();
        try {
            ventas.forEach( VentaModel -> {
                ventasJson.add(JsonParser.ventaToEmailJson(VentaModel));
            });

            ApiResponse<List<JsonNode>> response = new ApiResponse<>(
                    "Listado Ventas.",
                    ventasJson
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(
                    "Error al obtener Ventas: " + e.getMessage(),
                    null
            );
            JsonNode errorResponse = JsonParser.responseToJson(response);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
