package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.turbocharging.TurboTypeService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/turbo-types")
@RequiredArgsConstructor
public class TurboTypeController {

    private final TurboTypeService turboTypeService;
    private final BrandService brandService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;

    @GetMapping
    public String turboTypes(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getTurbochargers().isEmpty()).toList()
        );
        model.addAttribute("turboTypes", turboTypeService.findAll());
        model.addAttribute("turboTypesSelect",
                turboTypeService.findAll().stream().filter(turboType -> !turboType.getTurbochargers().isEmpty()).toList()
        );
        model.addAttribute("turbochargers",
                turbochargerService.findAll().stream().filter(turbocharger -> turbocharger.getTurboType() != null).toList()
        );
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

    @GetMapping("/secure/add")
    public String addTurboType(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turboType", new TurboType());
        model.addAttribute("brands", brandService.findAll());
        return "turbotypes/add";
    }

    @PostMapping("/secure/add")
    public String addTurboType(@ModelAttribute("turboType") TurboType turboType, HttpServletRequest request) {
        String[] turboTypeBrands = request.getParameterValues("turboTypeBrands");
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        Arrays.stream(turboTypeBrands).toList().forEach(s ->
                newTurboType.addBrand(brandService.findById(Long.parseLong(s)))
        );
        turboTypeService.save(newTurboType);
        Long id = turboTypeService.findByName(newTurboType.getName()).getId();
        return "redirect:/turbo-types/" + id;
    }

    @GetMapping("//{id}/edit")
    public String editTurboType(@PathVariable Long id, Model model) {
        model.addAttribute("turboType", turboTypeService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "turbotypes/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editTurboType(@ModelAttribute("turboType") TurboType turboType) {
        turboTypeService.update(turboType);
        return "redirect:/turbo-types/" + turboType.getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTurboType(@PathVariable Long id) {
        turboTypeService.delete(id);
        return "redirect:/turbo-types";
    }
}
