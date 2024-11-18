package com.apirest.apiadmin.controllers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.helpers.JsonParser;
import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.ProductoModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.apirest.apiadmin.models.VentaModel;
import com.apirest.apiadmin.services.ClientService;
import com.apirest.apiadmin.services.ProductoService;
import com.apirest.apiadmin.services.VendedorService;
import com.apirest.apiadmin.services.VentaService;
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

    @PostMapping("/create")
    public ResponseEntity<JsonNode> crearVenta(@RequestBody JsonNode json) {
        try{
            VendedorModel vendedor = vendedorService.getVendedor(JsonParser.vendorIdHeaderVentaFromJson(json));
            ClientModel cliente = clientService.getCLiente(JsonParser.clientIdHeaderVentaFromJson(json));

            VentaModel venta = new VentaModel(
                    JsonParser.DateHeaderVentaFromJson(json),
                    JsonParser.montoTotalHeaderVentaFromJson(json),
                    vendedor,
                    cliente
            );

            Integer nroLineasDeVenta = JsonParser.getLineasDeVentaFromJson(json);

            for (Integer i=0 ; i<nroLineasDeVenta ; i++){
                Integer idProducto = JsonParser.getProductIDVentaFromJson(json,i);
                ProductoModel producto = productoService.getProducto(idProducto);
                venta.agregarProducto(producto);
            }

            ventaService.guardarVenta(venta);

            ApiResponse<JsonNode> response = new ApiResponse<>(HttpStatus.CREATED.value(),
                    "Venta Generada exitosamente",
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JsonNode> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "Error al Crear la venta" + e.getMessage(),
                    null
            );

            JsonNode jsonResponse = JsonParser.responseToJson(response);

            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
