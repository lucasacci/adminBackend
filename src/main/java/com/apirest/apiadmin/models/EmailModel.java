package com.apirest.apiadmin.models;

public class EmailModel {
    private String mail_to;
    private String subject;
    private String body;

    public EmailModel(String mail_to, String subject, String body) {
        this.mail_to = mail_to;
        this.subject = subject;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMail_to() {
        return mail_to;
    }

    public void setMail_to(String mail_to) {
        this.mail_to = mail_to;
    }
}
