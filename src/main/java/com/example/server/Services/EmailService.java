package com.example.server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String toMail,String subject,String otp){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("allatrivikram@gmail.com");
            message.setTo(toMail);
            message.setSubject(subject);
            message.setText(otp);
            javaMailSender.send(message);
            System.out.println("OTP successfully sent to to "+toMail);
        }catch (MailException e){
            System.out.println("Mail invalid please once check your mail");
        }
    }
}
