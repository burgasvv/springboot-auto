package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", personService.findAll());
        return "users/users";
    }

    @GetMapping("/{name}")
    public String user(@PathVariable String name, Model model) {
        model.addAttribute("user", personService.findByName(name));
        return "users/user";
    }
}
