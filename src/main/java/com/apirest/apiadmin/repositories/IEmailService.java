package com.apirest.apiadmin.repositories;

import com.apirest.apiadmin.models.EmailModel;
import jakarta.mail.MessagingException;

public interface IEmailService {

    public void sendEmail(EmailModel email) throws MessagingException;
}
