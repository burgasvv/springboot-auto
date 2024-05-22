package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "users/registration";
    }

    @PostMapping("/registration")
    public String registration(Person person) {
        personService.createUser(person);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
