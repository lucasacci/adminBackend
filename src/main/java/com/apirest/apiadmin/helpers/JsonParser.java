package com.apirest.apiadmin.helpers;

import com.apirest.apiadmin.DTO.ApiResponse;
import com.apirest.apiadmin.models.GerenteModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
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
}
