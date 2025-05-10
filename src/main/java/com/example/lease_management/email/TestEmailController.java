package com.example.lease_management.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {


    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test-email")
    public String sendTestEmail(){
        emailService.sendEmail("katarzyna.cakala1988@wp.pl", "testowy Email kolejny", "To jest email testowy");

        return "Email został wysłany";
    }

}
