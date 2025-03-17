package com.example.springboot.first.app.service;

import com.sendgrid.*;

import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Method;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendVerificationEmail(String to, String verificationCode) {
        Email from = new Email("chat83284@gmail.com"); // Email de l'expéditeur
        Email toEmail = new Email(to); // Email du destinataire
        String subject = "Vérification de votre email";
        String content = "Votre code de vérification est : " + verificationCode;

        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email envoyé avec succès : " + response.getStatusCode());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPasswordEmail(String to, String password) {
        Email from = new Email("chat83284@gmail.com"); // Email de l'expéditeur
        Email toEmail = new Email(to); // Email du destinataire
        String subject = "Votre nouveau mot de passe";
        String content = "Votre nouveau mot de passe est : " + password;

        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email envoyé avec succès : " + response.getStatusCode());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




}