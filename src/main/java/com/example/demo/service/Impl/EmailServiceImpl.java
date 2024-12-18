package com.example.demo.service.Impl;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;

@Service
public class EmailServiceImpl implements EmailService {
    private final TransactionalEmailsApi emailApi;
    @Value("${sendinblue.sender.email}")
    private String senderEmail;

    public EmailServiceImpl(TransactionalEmailsApi emailApi) {
        this.emailApi = emailApi;
    }

    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        try {
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(new SendSmtpEmailSender().email(senderEmail));
            sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email(to)));
            sendSmtpEmail.setSubject(subject);
            body += "\n\n<p><strong>Note:</strong> This email was sent via FoundIt. <strong>Please do not reply directly to this email.</strong> " +
                    "If you wish to write a reply, kindly reach out to <a href='mailto:" + from + "'>" + from + "</a>" +
                    ".</p>";
            sendSmtpEmail.setHtmlContent(body);

            CreateSmtpEmail response = emailApi.sendTransacEmail(sendSmtpEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email:");
            e.printStackTrace();
        }
    }
}
