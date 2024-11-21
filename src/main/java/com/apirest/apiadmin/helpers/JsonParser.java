package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.models.ClientModel;
import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.models.VendedorModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static GerenteModel gerenteFromJson(JsonNode json) {

        String dni       = json.get("dni").asText();
        String nombre    = json.get("nombre").asText();
        String apellido  = json.get("apellido").asText();
        String email     = json.get("email").asText();
        String direccion = json.get("direccion").asText();

        return new GerenteModel(dni, nombre, apellido, email, direccion);
    }

    public static JsonNode responseToJson(ApiResponse<?> response) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(response);
    }

    public static DescuentoModel descuentoFromJson(JsonNode json, GerenteModel gerente) {
        String descripcion         = json.get("descripcion").asText();
        String porcentajeDescuento = json.get("porcentajeDescuento").asText();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fechaCaducidad = null;
        try {
            fechaCaducidad = formatoFecha.parse(json.get("fechaCaducidad").asText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DescuentoModel descuento = new DescuentoModel(
                descripcion,
                porcentajeDescuento,
                fechaCaducidad,
                gerente
        );

        return descuento;
    }

    public static JsonNode descuentosToJson(DescuentoModel descuentoModel) {
        ObjectNode json = mapper.createObjectNode();

        json.put("idDescuento", descuentoModel.getIdDescuento());
        json.put("descripcion", descuentoModel.getDescripcion());
        json.put("porcentajeDescuento", descuentoModel.getPorcentajeDescuento());
        json.put("fechaCaducidad", descuentoModel.getFechaCaducidad().toString());
        json.put("idGerente", descuentoModel.getGerente().getId_gerente());

        return json;
    }

    public static Date DateHeaderVentaFromJson(JsonNode json) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fechaVenta = null;
        try {
            fechaVenta = formatoFecha.parse(json.get("fechaVenta").asText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaVenta;
    }

    public static Double montoTotalHeaderVentaFromJson(JsonNode json) {
        return json.get("montoTotal").asDouble();
    }

    public static Long vendorIdHeaderVentaFromJson(JsonNode json) {
        return json.get("idVendedor").asLong();
    }

    public static Long clientIdHeaderVentaFromJson(JsonNode json) {
        return json.get("idCliente").asLong();
    }

    public static int getLineasDeVentaFromJson(JsonNode json) {
       return json.get("lineasDeVenta").size();
    }

    public static Integer getProductIDVentaFromJson(JsonNode json, Integer i) {
        return json.get("lineasDeVenta").get(i).get("idProducto").asInt();
    }

    public static ClientModel getClientFromJson(JsonNode json, VendedorModel vendedor) {
        int dni          = json.get("dni").asInt();
        String nombre    = json.get("nombre").asText();
        String apellido  = json.get("apellido").asText();
        String email     = json.get("email").asText();
        String direccion = json.get("direccion").asText();

        return new ClientModel(dni, nombre, apellido, email, direccion, vendedor);
    }
}
