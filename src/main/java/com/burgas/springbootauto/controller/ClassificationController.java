package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.ClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;
    private final CarService carService;

    @GetMapping
    public String classes(Model model) {
        model.addAttribute("classes", classificationService.findAll());
        return "classes/classes";
    }

    @GetMapping("/{id}")
    public String getClass(@PathVariable("id") Long id, Model model) {
        model.addAttribute("class", classificationService.findById(id));
        return "classes/class";
    }

    @GetMapping("/{id}/cars")
    public String cars(@ModelAttribute("class") Classification classification, Model model) {
        classification.setCars(carService.searchCarByClassificationId(classification.getId()));
        model.addAttribute("cars", classification.getCars());
        return "classes/cars";
    }
}
