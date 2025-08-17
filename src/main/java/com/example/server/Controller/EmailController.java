package com.example.server.Controller;

import com.example.server.Models.Email;
import com.example.server.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send")
    public void sendMail(@RequestBody Email email){

        String subject="Otp for password change";
        String otp = String.valueOf(new java.util.Random().nextInt(900000) + 100000);
        emailService.sendEmail(email.getEmail(),subject,otp);
    }
}
