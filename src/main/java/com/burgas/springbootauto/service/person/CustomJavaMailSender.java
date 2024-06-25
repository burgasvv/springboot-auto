package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomJavaMailSender {

    private final JavaMailSender mailSender;
    private final RestoreTokenService restoreTokenService;

    public void sendSimpleMail(Person person) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(person.getEmail());
        message.setFrom("admin@admin.com");
        message.setSubject("Forgot Password? Create the new one!");
        message.setText("Follow this link " + getRestoreTokenLink(person) + " to create a new password");
        mailSender.send(message);
    }

    private String getRestoreTokenLink(Person person) {
        UUID uuid = UUID.randomUUID();
        RestoreToken token = RestoreToken.builder().token(uuid.toString()).person(person).build();
        token = restoreTokenService.save(token);
        return "http://localhost:8080/restorePassword/" + token.getToken();
    }
}
