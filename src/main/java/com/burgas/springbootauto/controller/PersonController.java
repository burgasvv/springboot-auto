package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.person.RoleRepository;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String users(Model model) {
        return usersPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String usersPage(@PathVariable int page, Model model) {
        Page<Person> users = personService.findAll(page, 10);
        int totalPages = users.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("users", users.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/users";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        return searchUsersPage(1, model, search);
    }

    @GetMapping("/search/pages/{page}")
    public String searchUsersPage(@PathVariable int page, Model model, String search) {
        Page<Person> users = personService.searchAllByFirstnameAndLastnameAndUsername(search, page, 10);
        int totalPages = users.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("search", search);
        model.addAttribute("users", users.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/search";
    }

    @GetMapping("/{name}")
    public String user(@PathVariable String name, Model model) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("users",
                personService.findAll().stream()
                        .filter(person -> !person.equals(owner) && !person.getRole().equals(roleRepository.findByName("ADMIN"))).toList()
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

    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam String selectUser) {
        Person user = personService.findPersonByUsername(selectUser);
        Person admin = personService.makeAdmin(user);
        return "redirect:/users/" + admin.getUsername();
    }

    @PostMapping("/{name}/ban")
    public String ban(@PathVariable String name) {
        Person owner = personService.findPersonByUsername(name);
        Person banned = personService.ban(owner);
        return "redirect:/users/" + banned.getUsername();
    }

    @PostMapping("/{name}/unban")
    public String unban(@PathVariable String name) {
        Person owner = personService.findPersonByUsername(name);
        Person unbanned = personService.unban(owner);
        return "redirect:/users/" + unbanned.getUsername();
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
}
