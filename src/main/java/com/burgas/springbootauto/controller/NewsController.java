package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.service.news.NewsService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final PersonService personService;

    @GetMapping
    public String news(Model model) {
        return newsPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String newsPage(@PathVariable int page, Model model) {
        Page<News> news = newsService.findAll(page, 20);
        model.addAttribute("pages", IntStream.rangeClosed(1, news.getTotalPages()).boxed().toList());
        model.addAttribute("news", news.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "index";
    }

    @GetMapping("/search")
    public String newsSearch(Model model, @RequestParam String search) {
        return newsSearchPage(1, search, model);
    }

    @GetMapping("/search/pages/{page}")
    public String newsSearchPage(@PathVariable int page, @RequestParam String search, Model model) {
        Page<News> news = newsService.searchNewsByKeyword(search, page, 20);
        model.addAttribute("pages", IntStream.rangeClosed(1, news.getTotalPages()).boxed().toList());
        model.addAttribute("news", news.getContent());
        model.addAttribute("search", search);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "news/findNews";
    }

    @GetMapping("/{id}")
    public String article(@PathVariable Long id, Model model) {
        model.addAttribute("article", newsService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "news/article";
    }
}
