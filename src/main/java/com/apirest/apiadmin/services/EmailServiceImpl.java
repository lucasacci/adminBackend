package com.apirest.apiadmin.services;


import com.apirest.apiadmin.models.EmailModel;
import com.apirest.apiadmin.repositories.IEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(EmailModel email) throws MessagingException{
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
            context.setVariable("body", email.getBody());


            String contenidoHtml = templateEngine.process("email_template", context);

            helper.setText(contenidoHtml, true);
            mailSender.send(mimeMailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electronico: " + e.getMessage(), e);
        }

    }


}
