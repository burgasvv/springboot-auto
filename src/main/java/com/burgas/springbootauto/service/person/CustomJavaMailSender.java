package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomJavaMailSender {

    private final JavaMailSender mailSender;
    private final RestoreTokenService restoreTokenService;
    private final PersonService personService;

    public void sendMailToRestorePassword(Person person) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(person.getEmail());
        message.setFrom("admin");
        message.setSubject("Forgot Password? Create the new one!");
        message.setText("Follow this link " + getRestoreTokenLinkForPassword(person) + " to create a new password");
        mailSender.send(message);
    }

    private String getRestoreTokenLinkForPassword(Person person) {
        clearToken(person);
        UUID uuid = UUID.randomUUID();
        RestoreToken token = RestoreToken.builder().token(uuid.toString()).person(person).build();
        token = restoreTokenService.save(token);
        return "http://localhost:8080/restorePassword/" + token.getToken();
    }

    public void sendMailToActivateAccount(Person person) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(person.getEmail());
        message.setFrom("admin");
        message.setSubject("Activate Account");
        message.setText("Follow this link " + getRestoreTokenLinkForAccount(person) + " to activate your account");
        mailSender.send(message);
    }

    private String getRestoreTokenLinkForAccount(Person person) {
        clearToken(person);
        UUID uuid = UUID.randomUUID();
        RestoreToken token = RestoreToken.builder().token(uuid.toString()).person(person).build();
        token = restoreTokenService.save(token);
        return "http://localhost:8080/activateAccount/" + token.getToken();
    }

    private void clearToken(Person person) {
        if (restoreTokenService.existsRestoreTokenByPerson(person)) {
            RestoreToken token = restoreTokenService.findTokenByPerson(person);
            person.setToken(null);
            personService.update(person);
            restoreTokenService.delete(token);
        }
    }
}
