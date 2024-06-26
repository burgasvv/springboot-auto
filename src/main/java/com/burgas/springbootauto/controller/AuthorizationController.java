package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.person.CustomJavaMailSender;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.person.RestoreTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {

    private final PersonService personService;
    private final RestoreTokenService restoreTokenService;
    private final CustomJavaMailSender mailSender;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/login";
    }

    @GetMapping("/forgotPassword/{status}")
    public String forgotPassword(@PathVariable String status, Model model) {
        model.addAttribute("status", status);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordSubmit(@RequestParam String email, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Person person = personService.findPersonByEmail(email);
        if (person != null) {
            mailSender.sendSimpleMail(person);
            return "redirect:/forgotPassword/success";
        }
        return "redirect:/forgotPassword/fail";
    }

    @GetMapping("/restorePassword/{token}")
    public String restorePassword(@PathVariable String token, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("person", restoreTokenService.findByToken(token).getPerson());
        return "authorization/restorePassword";
    }

    @PostMapping("/restorePassword")
    public String restorePasswordSubmit(@RequestParam String password, @RequestParam Long id) {
        Person person = personService.findById(id);
        personService.restorePassword(person, password);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Person person, @RequestPart MultipartFile file) {
        personService.createUser(person, file);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
