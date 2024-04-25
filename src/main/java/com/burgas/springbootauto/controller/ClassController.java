package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.dao.ClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classes")
public class ClassController {

    private final ClassDao classDao;

    @Autowired
    public ClassController(ClassDao classDao) {
        this.classDao = classDao;
    }

    @GetMapping
    public String classes(Model model) {
        model.addAttribute("classes", classDao.findAll());
        return "classes/classes";
    }

    @GetMapping("/{id}")
    public String getClass(@PathVariable("id") int id, Model model) {
        model.addAttribute("class", classDao.find(id));
        return "classes/class";
    }
}
