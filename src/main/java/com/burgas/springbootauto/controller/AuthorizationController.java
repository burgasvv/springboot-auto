package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {

    private final PersonService personService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/registration";
    }

    @PostMapping("/registration")
    public String registration(Person person,  @RequestPart MultipartFile file) {
        personService.createUser(person, file);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
