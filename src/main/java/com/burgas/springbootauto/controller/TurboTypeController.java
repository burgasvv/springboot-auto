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
        TurbochargerController.getSearchLists(model, personService, brandService, turboTypeService, turbochargerService);
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
        return "redirect:/turbo-types/" + turboTypeService.save(turboType, request);
    }

    @GetMapping("/{id}/edit")
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
