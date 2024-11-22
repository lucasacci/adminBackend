package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.EmailModel;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.mail.MessagingException;

public interface IEmailService {

    public void sendEmail(EmailModel email, JsonNode json) throws MessagingException;
}
