package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.communication.cheer.CheerService;
import com.burgas.springbootauto.service.news.NewsService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CheerController {

    private final CheerService cheerService;
    private final NewsService newsService;
    private final PersonService personService;

    @PostMapping("/news/{id}/news-cheer-plus")
    public String newsCheerPlus(@PathVariable Long id, @RequestParam String username) {
        Person sender = personService.findPersonByUsername(username);
        News article = newsService.findById(id);
        cheerService.newsCheerPlus(article, sender);
        return "redirect:/news/{id}";
    }

    @PostMapping("/news/{id}/news-cheer-minus")
    public String newsCheerMinus(@RequestParam String username, @PathVariable Long id) {
        Person sender = personService.findPersonByUsername(username);
        News article = newsService.findById(id);
        cheerService.newsCheerMinus(article, sender);
        return "redirect:/news/{id}";
    }
}
