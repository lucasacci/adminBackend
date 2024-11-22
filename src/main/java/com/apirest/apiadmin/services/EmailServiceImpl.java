package com.apirest.apiadmin.services;


import com.apirest.apiadmin.models.EmailModel;
import com.apirest.apiadmin.repositories.IEmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(EmailModel email, JsonNode venta) throws MessagingException {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage,
                    true, "UTF-8");

            /*Aqui Armamos el correo para enviarlo*/
            helper.setTo(email.getMail_to());
            helper.setSubject(email.getSubject());

            Context context = new Context();
        /*
        body es la variable definida en nuestro Template HTML dentro de la etiqueta
        */

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> ventaMap = objectMapper.convertValue(venta, Map.class);
            context.setVariable("venta", ventaMap);


            String contenidoHtml = templateEngine.process("email_template", context);

            helper.setText(contenidoHtml, true);
            mailSender.send(mimeMailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electronico: " + e.getMessage(), e);
        }

    }


}
