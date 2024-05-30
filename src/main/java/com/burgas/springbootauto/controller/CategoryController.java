package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Category;
import com.burgas.springbootauto.service.car.CategoryService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final PersonService personService;

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("categories", categoryService.findAll());
        return "categories/categories";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("category", categoryService.findById(id));
        return "categories/category";
    }

    @GetMapping("/{id}/cars")
    public String cars(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Category category = categoryService.findById(id);
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(cat -> !cat.getCars().isEmpty()).toList()
        );
        model.addAttribute("category", category);
        model.addAttribute("cars", category.getCars());
        return "categories/cars";
    }

    @GetMapping("/find-category")
    public String findCategory(@RequestParam("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        //noinspection SpringMVCViewInspection
        return "redirect:/categories/" + category.getId() + "/cars";
    }
}
