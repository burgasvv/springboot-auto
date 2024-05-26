package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.GearboxService;
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

    @GetMapping
    public String gearboxes(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("gearboxes", gearboxService.findAll());
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

    @GetMapping("/add")
    public String addGearboxForm(Model model) {
        model.addAttribute("gearbox", new Gearbox());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("gearboxBrand", new Brand());
        return "gearboxes/add";
    }

    @PostMapping("/add")
    public String addGearbox(@ModelAttribute Gearbox gearbox, @ModelAttribute("gearboxBrand") Brand temp) {
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        newGearbox.addBrand(brandService.findById(temp.getId()));
        gearboxService.save(newGearbox);
        Long id = gearboxService.findByName(newGearbox.getName()).getId();
        return "redirect:/gearboxes/" + id;
    }
}
