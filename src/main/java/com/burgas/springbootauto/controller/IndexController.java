package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.service.news.NewsService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PersonService personService;
    private final NewsService newsService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("news", newsService.findAll());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "redirect:/news";
    }
}
