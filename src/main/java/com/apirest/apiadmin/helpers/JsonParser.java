package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.models.*;
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
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
        Date fechaVenta = null;
        try {
            fechaVenta = formatoFecha.parse(
                    json.get("date")
                            .asText()
                            .replace("Z"," ")
                            .replace("T"," ")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaVenta;
    }

    public static Double montoTotalHeaderVentaFromJson(JsonNode json) {
        return json.get("total").asDouble();
    }

    public static Long vendorIdHeaderVentaFromJson(JsonNode json) {
        return json.get("idVendedor").asLong();
    }

    public static Long clientIdHeaderVentaFromJson(JsonNode json) {
        return json.get("idCliente").asLong();
    }

    public static int getLineasDeVentaFromJson(JsonNode json) {
       return json.get("sellLines").size();
    }

    public static Integer getCountFromLineFromJson(JsonNode json, Integer i) {
        return json.get("sellLines").get(i).get("count").asInt();
    }

    public static Integer getProductIDVentaFromJson(JsonNode json, Integer i) {
        String idString = json.get("sellLines").get(i).get("product").get("id").asText();
        return Integer.parseInt(idString);
    }

    public static Double getSubTotalFromJson(JsonNode json, Integer i) {
        return json.get("sellLines").get(i).get("subtotal").asDouble();
    }

    public static ClientModel getClientFromJson(JsonNode json, VendedorModel vendedor) {
        int dni          = json.get("dni").asInt();
        String nombre    = json.get("nombre").asText();
        String apellido  = json.get("apellido").asText();
        String email     = json.get("email").asText();
        String direccion = json.get("direccion").asText();

        return new ClientModel(dni, nombre, apellido, email, direccion, vendedor);
    }

    public static ProductoModel getProductFromJson(JsonNode json, GerenteModel gerente) {
        String categoria = json.get("categoria").asText();
        String nombre    = json.get("nombre").asText();
        Double precio    = json.get("precio").asDouble();
        String comentarios = json.get("comentarios").asText();
        int stock = 0;
        if (json.has("stock")){
            stock = json.get("stock").asInt();
        }

        return new ProductoModel(nombre, precio, stock, categoria, comentarios, gerente);
    }

    public static VendedorModel getVendedorFromJson(JsonNode json, GerenteModel gerente) {
        int dni = json.get("dni").asInt();
        String nombre = json.get("nombre").asText();
        String apellido = json.get("apellido").asText();
        String correo   = json.get("correo").asText();
        String direccion = json.get("direccion").asText();

        return new VendedorModel(nombre, apellido, correo, dni, direccion, gerente);
    }

    public static JsonNode productToJson(ProductoModel productoModel) {
        ObjectNode json = mapper.createObjectNode();

        json.put("id_producto", productoModel.getId_producto());
        json.put("nombre", productoModel.getNombre());
        json.put("precio", productoModel.getPrecio()).asDouble();
        json.put("categoria", productoModel.getCategoria());
        json.put("comentarios", productoModel.getComentarios());
        json.put("id_gerente", productoModel.getGerente().getId_gerente());

        return json;
    }

    public static JsonNode vendedorToJson(VendedorModel vendedorModel) {
        ObjectNode json = mapper.createObjectNode();

        json.put("id_vendedor", vendedorModel.getId_vendedor());
        json.put("nombre", vendedorModel.getNombre());
        json.put("apellido", vendedorModel.getApellido());
        json.put("correo", vendedorModel.getCorreo());
        json.put("dni", vendedorModel.getDni()).asInt();
        json.put("direccion", vendedorModel.getDireccion());
        json.put("id_gerente", vendedorModel.getId_gerente().getId_gerente());

        return json;
    }

    public static PaymentModel paymentFromJson(JsonNode json) {
        JsonNode paymentJson = json.get("paymentDetails");

        String paymentMethod = paymentJson.get("paymentMethod").asText();

        if(!paymentJson.get("cashPaymentDetails").isNull()){
            Double amount = paymentJson.get("cashPaymentDetails").get("amount").asDouble();
            Double returned = paymentJson.get("cashPaymentDetails").get("returned").asDouble();

            return new PaymentModel(
                    paymentMethod,
                    amount, returned,
                    null, null, null, null
            );
        }

        if(!paymentJson.get("cardDetails").isNull()){
            String number = paymentJson.get("cardDetails").get("number").asText();
            String name = paymentJson.get("cardDetails").get("name").asText();
            String securityCode = paymentJson.get("cardDetails").get("securityCode").asText();
            String expirationDate = paymentJson.get("cardDetails").get("expirationDate").asText();

            return new PaymentModel(
                    paymentMethod,
                    null, null,
                    number, name, securityCode, expirationDate
            );
        }
        return new PaymentModel();
    }
}
