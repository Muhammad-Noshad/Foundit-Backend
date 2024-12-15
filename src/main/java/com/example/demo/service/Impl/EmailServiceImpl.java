package com.example.demo.service.Impl;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;

@Service
public class EmailServiceImpl implements EmailService {
    private final TransactionalEmailsApi emailApi;

    public EmailServiceImpl(TransactionalEmailsApi emailApi) {
        this.emailApi = emailApi;
    }

    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        try {
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(new SendSmtpEmailSender().email(from));
            sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email(to)));
            sendSmtpEmail.setSubject(subject);
            sendSmtpEmail.setHtmlContent(body);

            emailApi.sendTransacEmail(sendSmtpEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
