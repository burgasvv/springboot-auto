package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        return "categories/categories";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryDao.find(id));
        return "categories/category";
    }
}
