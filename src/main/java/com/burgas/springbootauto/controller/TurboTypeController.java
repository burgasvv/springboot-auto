package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.turbocharging.TurboTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turbo-types")
@RequiredArgsConstructor
public class TurboTypeController {

    private final TurboTypeService turboTypeService;
    private final BrandService brandService;
    private final PersonService personService;

    @GetMapping
    public String turboTypes(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turboTypes", turboTypeService.findAll());
        return "turbotypes/turbotypes";
    }

    @GetMapping("/{id}")
    public String turboType(@PathVariable Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turboType", turboTypeService.findById(id));
        return "turbotypes/turbotype";
    }

    @GetMapping("/add")
    public String addTurboType(Model model) {
        model.addAttribute("turboType", new TurboType());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("turboTypeBrand", new Brand());
        return "turbotypes/add";
    }

    @PostMapping("/add")
    public String addTurboType(@ModelAttribute("turboType") TurboType turboType, @ModelAttribute("turboTypeBrand") Brand brand) {
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        newTurboType.addBrand(brandService.findById(brand.getId()));
        turboTypeService.save(newTurboType);
        Long id = turboTypeService.findByName(newTurboType.getName()).getId();
        return "redirect:/turbo-types/" + id;
    }
}
