package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public String users(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("users", personService.findAll());
        return "users/users";
    }

    @GetMapping("/{name}")
    public String user(@PathVariable String name, Model model) {
        model.addAttribute("owner", personService.findPersonByUsername(name));
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/user";
    }

    @GetMapping("/{name}/edit")
    public String edit(@PathVariable String name, Model model) {
        model.addAttribute("owner", personService.findPersonByUsername(name));
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/edit";
    }

    @PatchMapping("/edit")
    public String edit(@ModelAttribute Person owner) {
        Person person = personService.findById(owner.getId());
        personService.update(owner);
        if (owner.getUsername().equals(person.getUsername()) && owner.getPassword().equals(person.getPassword())) {
            return "redirect:/users/" + owner.getUsername();
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication.setAuthenticated(false);
            return "redirect:/login";
        }
    }

    @DeleteMapping("/{name}/delete")
    public String delete(@PathVariable String name) {
        personService.delete(personService.findPersonByUsername(name));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        return "redirect:/login";
    }

    @GetMapping("/{name}/cars")
    public String cars(@PathVariable String name, Model model) {
        model.addAttribute("owner", personService.findPersonByUsername(name));
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/cars";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("search", search);
        model.addAttribute("users", personService.searchAllByFirstnameAndLastnameAndUsername(search));
        return "users/search";
    }
}
