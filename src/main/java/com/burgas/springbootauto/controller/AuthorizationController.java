package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
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

    @PostMapping("/login-status")
    public String loginStatus(@RequestParam("username") String username) {
        Person person = personService.findPersonByUsername(username);
        personService.connectUser(person);
        return "redirect:/users/" + person.getUsername();
    }

    @GetMapping("/baned")
    public String baned(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/baned";
    }

    @GetMapping("/login-wrong")
    public String loginWrong(Model model) {
        model.addAttribute("error", "Логин или пароль не существует");
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "authorization/loginWrong";
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
            mailSender.sendMailToRestorePassword(person);
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
    public String registration(@ModelAttribute Person person, @RequestPart MultipartFile file, Model model, @ModelAttribute Person user) {
        model.addAttribute("user", user);
        Person newUser = personService.createUser(person, file);
        mailSender.sendMailToActivateAccount(newUser);
        model.addAttribute("activation", "off");
        return "authorization/activateAccount";
    }

    @GetMapping("/activateAccount/{token}")
    public String activateAccount(@PathVariable String token, Model model) {
        model.addAttribute("restoreToken", restoreTokenService.findByToken(token));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("activation", "activate");
        return "authorization/activateAccount";
    }

    @GetMapping("/activateAccount/getCode")
    public String activateAccountGetCode(@RequestParam String token, @RequestParam Integer code, Model model) {
        RestoreToken restoreToken = restoreTokenService.findByToken(token);
        if (code.equals(restoreToken.getCode())) {
            Person person = restoreTokenService.findByToken(token).getPerson();
            personService.activateAccount(person);
            model.addAttribute("user",
                    personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
            );
            model.addAttribute("activation", "on");
        } else {
            model.addAttribute("user",
                    personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
            );
            model.addAttribute("restoreToken", restoreTokenService.findByToken(token));
            model.addAttribute("code", code);
            model.addAttribute("activation", "negative");
        }
        return "authorization/activateAccount";
    }

    @PostMapping("/logout-status")
    public String logoutStatus() {
        Person user = personService.findPersonByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        personService.disconnectUser(user);
        return "forward:/logout";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
