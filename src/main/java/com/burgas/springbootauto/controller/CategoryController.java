package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Category;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CarService carService;

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/categories";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "categories/category";
    }

    @GetMapping("/{id}/cars")
    public String cars(@ModelAttribute("category") Category category, Model model) {
        category.setCars(carService.searchCarsByCategoryId(category.getId()));
        model.addAttribute("cars", category.getCars());
        return "categories/cars";
    }
}
