package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.models.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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
        Instant fechaCaducidad = Instant.ofEpochSecond(json.get("fechaCaducidad").asLong());

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
        json.put("fechaCaducidad", descuentoModel.getFechaCaducidad().toEpochMilli());
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
        String categoria   = json.get("categoria").asText();
        String nombre      = json.get("nombre").asText();
        Double precio      = json.get("precio").asDouble();
        String comentarios = json.get("comentarios").asText();
        int stock = 0;
        if (json.has("stock")){
            stock = json.get("stock").asInt();
        }

        return new ProductoModel(nombre, precio, stock, categoria, comentarios, gerente);
    }

    public static VendedorModel getVendedorFromJson(JsonNode json, GerenteModel gerente) {
        int dni          = json.get("dni").asInt();
        String nombre    = json.get("nombre").asText();
        String apellido  = json.get("apellido").asText();
        String correo    = json.get("correo").asText();
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

        if(paymentMethod.equals("Tarjeta")) {
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

        if(paymentMethod.equals("Efectivo")) {
            Double amount = paymentJson.get("cashPaymentDetails").get("amount").asDouble();
            Double returned = paymentJson.get("cashPaymentDetails").get("returned").asDouble();

            return new PaymentModel(
                    paymentMethod,
                    amount, returned,
                    null, null, null, null
            );
        }

        return new PaymentModel();
    }

    public static JsonNode ventaToEmailJson(VentaModel venta) {
        //Lines
        ArrayNode jsonArray  = mapper.createArrayNode();
        venta.getLineasDeVenta().forEach(lineVentaModel -> {
            jsonArray.add(lineVentaToJson(lineVentaModel));
        });

        //Card Details
        ObjectNode cardDetails = mapper.createObjectNode();
        if(venta.getPayment().getPaymentMethod().equals("Tarjeta")) {
            cardDetails.put("number", venta.getPayment().getCardDetailsModel().getNumber());
            cardDetails.put("name", venta.getPayment().getCardDetailsModel().getName());
            cardDetails.put("expirationDate", venta.getPayment().getCardDetailsModel().getExpirationDate());
        } else {
            cardDetails.nullNode();
        }

        //Cash Details
        ObjectNode cashDetails = mapper.createObjectNode();
        if (venta.getPayment().getPaymentMethod().equals("Efectivo")) {
            cashDetails.put("amount", venta.getPayment().getCashPaymentDetailsModel().getAmount().toString());
            cashDetails.put("returned", venta.getPayment().getCashPaymentDetailsModel().getReturned().toString());
        } else {
            cashDetails.nullNode();
        }

        //Payment
        ObjectNode payment = mapper.createObjectNode();
        payment.put("paymentMethod", venta.getPayment().getPaymentMethod());
        payment.set("cardDetails", cardDetails);
        payment.set("cashPaymentDetails", cashDetails);

        //Header
        ObjectNode json = mapper.createObjectNode();
        json.put("id", venta.getIdVenta());
        json.put("date", venta.getFechaVenta().toString());
        json.put("sellerName", venta.getVendedor().getApellido() + ", " +
                venta.getVendedor().getNombre());
        json.put("clientName", venta.getCliente().getApellido() + ", " +
                venta.getCliente().getNombre());
        json.put("total", venta.getMontoTotal().toString());
        json.set("sellLines", jsonArray);
        json.set("paymentDetails", payment);

        return json;
    }

    public static JsonNode addVentaNodeOnTop(JsonNode json) {
        //VentaCompleta
        ObjectNode jsonVenta = mapper.createObjectNode();
        jsonVenta.set("venta", json);

        return jsonVenta;
    }

    private static JsonNode lineVentaToJson(LineVentaModel lineVentaModel) {
        ObjectNode json = mapper.createObjectNode();

        json.put("productName", lineVentaModel.getProducto().getNombre());
        json.put("count", lineVentaModel.getCount().toString());
        json.put("productPrecio", lineVentaModel.getProducto().getPrecio().toString());
        json.put("subtotal", lineVentaModel.getSubTotal().toString());

        return json;
    }

    public static JsonNode clientToJson(ClientModel clientModel) {
        ObjectNode json = mapper.createObjectNode();

        json.put("id_cliente", clientModel.getId_cliente());
        json.put("nombre", clientModel.getNombre());
        json.put("apellido", clientModel.getApellido());
        json.put("email", clientModel.getEmail());
        json.put("dni", clientModel.getDni());
        json.put("id_vendedor", clientModel.getVendedor().getId_vendedor());

        return json;
    }

    private static JsonNode ventasToJson(List<VentaModel> ventas) {
        ArrayNode ventasArray  = mapper.createArrayNode();
        ObjectNode ventasJson = mapper.createObjectNode();

        ArrayNode linesArray  = mapper.createArrayNode();
        ObjectNode linesJson = mapper.createObjectNode();

        ventas.forEach(ventaModel -> {
            ventasJson.put("idVenta", ventaModel.getIdVenta());
            ventasJson.put("fechaVenta", ventaModel.getFechaVenta().toString());
            ventasJson.put("montoTotal", ventaModel.getMontoTotal());
            ventasJson.put("idVendedor", ventaModel.getVendedor().getId_vendedor());
            ventasJson.put("paymentType", ventaModel.getPayment().getPaymentMethod());

            ventaModel.getLineasDeVenta().forEach(lineVentaModel -> {
                linesJson.put("productName", lineVentaModel.getProducto().getNombre());
                linesJson.put("productPrice", lineVentaModel.getPrecioVenta());
                linesJson.put("count", lineVentaModel.getCount());
                linesJson.put("subtotal", lineVentaModel.getSubTotal());

                linesArray.add(linesJson);
            });
            ventasJson.set("sellLines", linesArray);

            ventasArray.add(ventasJson);
        });

        return ventasArray;
    }
}
