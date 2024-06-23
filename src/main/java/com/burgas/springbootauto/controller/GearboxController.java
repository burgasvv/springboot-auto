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

import java.util.Arrays;

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
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getTransmissions().isEmpty()).toList()
        );
        model.addAttribute("gearboxes", gearboxService.findAll());
        model.addAttribute("gearboxesSelect",
                gearboxService.findAll().stream().filter(gearbox -> !gearbox.getTransmissions().isEmpty()).toList()
        );
        model.addAttribute("transmissions",
                transmissionService.findAll().stream().filter(transmission -> transmission.getGearbox() != null).toList()
        );
        model.addAttribute("driveTypes", driveTypeService.findAll()
                .stream().filter(driveType -> !driveType.getTransmissions().isEmpty()).toList()
        );
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
        String[] selectedBrands = servletRequest.getParameterValues("selectedBrands");
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        Arrays.stream(selectedBrands).toList().iterator().forEachRemaining(s ->
                newGearbox.addBrand(brandService.findById(Long.valueOf(s)))
        );
        gearboxService.save(newGearbox);
        Long id = gearboxService.findByName(newGearbox.getName()).getId();
        return "redirect:/gearboxes/" + id;
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
