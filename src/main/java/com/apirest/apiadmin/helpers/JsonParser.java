package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.models.DescuentoModel;
import com.apirest.apiadmin.models.GerenteModel;
import com.apirest.apiadmin.services.GerenteService;
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

        GerenteModel gerente = new GerenteModel(dni, nombre, apellido, email, direccion);

        return gerente;
    }

    public static JsonNode responseToJson(ApiResponse<?> response) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(response);
    }

    public static DescuentoModel descuentoFromJson(JsonNode json) {
        String descripcion         = json.get("descripcion").asText();
        String porcentajeDescuento = json.get("porcentajeDescuento").asText();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaCaducidad = null;
        try {
            fechaCaducidad = formatoFecha.parse(json.get("fechaCaducidad").asText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long idGerente = json.get("idGerente").asLong();

        GerenteService gerenteService = new GerenteService();
        gerenteService.getGerentes();

        GerenteModel gerente = gerenteService.getGerente(idGerente);

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
}
