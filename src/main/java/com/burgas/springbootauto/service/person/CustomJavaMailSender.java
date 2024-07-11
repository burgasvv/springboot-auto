package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

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
        String tokenLinkForAccount = getRestoreTokenLinkForAccount(person);
        RestoreToken token = restoreTokenService.findTokenByPerson(person);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(person.getEmail());
        message.setFrom("admin");
        message.setSubject("Activate Account");
        message.setText("Follow this link "
                + tokenLinkForAccount +
                " to activate your account and input activation code " + token.getCode());
        mailSender.send(message);
    }

    private String getRestoreTokenLinkForAccount(Person person) {
        clearToken(person);
        UUID uuid = UUID.randomUUID();
        List<Integer>integers = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            integers.add(new Random().nextInt(0,9));
            stringBuilder.append(integers.get(i));
        }
        RestoreToken token = RestoreToken.builder()
                .token(uuid.toString()).code(Integer.valueOf(stringBuilder.toString())).person(person).build();
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
