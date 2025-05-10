

package com.example.lease_management.email;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class EmailService {


    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public  void sendEmail( String toEmail, String subject, String body){

        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("katarzyna.bogdan@buziaczek.pl");

        mailSender.send(message);



    }




}
