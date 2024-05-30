package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.service.car.ClassificationService;
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
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;
    private final PersonService personService;

    @GetMapping
    public String classes(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("classes", classificationService.findAll());
        return "classes/classes";
    }

    @GetMapping("/{id}")
    public String getClass(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("class", classificationService.findById(id));
        return "classes/class";
    }

    @GetMapping("/{id}/cars")
    public String cars(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Classification classification = classificationService.findById(id);
        model.addAttribute("classes",
                classificationService.findAll().stream().filter(cl -> !cl.getCars().isEmpty()).toList()
        );
        model.addAttribute("class", classification);
        model.addAttribute("cars", classification.getCars());
        return "classes/cars";
    }

    @GetMapping("/find-class")
    public String findClass(@RequestParam("id") Long id, Model model) {
        Classification classification = classificationService.findById(id);
        model.addAttribute("class", classification);
        //noinspection SpringMVCViewInspection
        return "redirect:/classes/" + classification.getId() + "/cars";
    }
}
