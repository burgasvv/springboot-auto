package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gearboxes")
@RequiredArgsConstructor
public class GearboxController {

    private final GearboxService gearboxService;
    private final PersonService personService;
    private final BrandService brandService;
    private final TransmissionService transmissionService;
    private final DriveTypeService driveTypeService;

    @GetMapping
    public String gearboxes(Model model) {
        TransmissionController.getSearchLists(model, personService, brandService, gearboxService, transmissionService, driveTypeService);
        return "gearboxes/gearboxes";
    }

    @GetMapping("/{id}")
    public String gearbox(Model model, @PathVariable Long id) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("gearbox", gearboxService.findById(id));
        return "gearboxes/gearbox";
    }

    @GetMapping("/secure/add")
    public String addGearboxForm(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("gearbox", new Gearbox());
        model.addAttribute("brands", brandService.findAll());
        return "gearboxes/add";
    }

    @PostMapping("/secure/add")
    public String addGearbox(@ModelAttribute Gearbox gearbox, HttpServletRequest servletRequest) {
        return "redirect:/gearboxes/" + gearboxService.createGearbox(gearbox, servletRequest);
    }

    @GetMapping("/{id}/edit")
    public String editGearboxForm(@PathVariable Long id, Model model) {
        model.addAttribute("gearbox", gearboxService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "gearboxes/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editGearbox(@ModelAttribute Gearbox gearbox, @PathVariable Long id) {
        gearboxService.update(gearbox);
        return "redirect:/gearboxes/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteGearbox(@PathVariable Long id) {
        gearboxService.delete(id);
        return "redirect:/gearboxes";
    }
}
