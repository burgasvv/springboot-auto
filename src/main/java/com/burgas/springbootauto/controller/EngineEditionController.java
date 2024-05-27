package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.engine.EngineEditionService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editions")
@RequiredArgsConstructor
public class EngineEditionController {

    private final EngineEditionService editionService;
    private final EngineService engineService;
    private final BrandService brandService;
    private final FuelService fuelService;
    private final PersonService personService;

    @GetMapping
    public String editions(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getEngineEditions().isEmpty()).toList()
        );
        model.addAttribute("editions", editionService.findAll());
        model.addAttribute("engines",
                engineService.findAll().stream().filter(engine -> engine.getEngineEdition() != null).toList()
        );
        model.addAttribute("fuelTypes", fuelService.findAll());
        return "editions/editions";
    }

    @GetMapping("/{id}")
    public String edition(@PathVariable Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("edition", editionService.findById(id));
        return "editions/edition";
    }

    @GetMapping("/add")
    public String addEditionForm(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("edition", new EngineEdition());
        return "editions/add";
    }

    @PostMapping("/add")
    public String addEdition(@ModelAttribute("edition") EngineEdition edition) {
        EngineEdition newEdition = new EngineEdition();
        newEdition.setBrand(edition.getBrand());
        newEdition.setName(edition.getName());
        newEdition.setImage(edition.getImage());
        editionService.save(edition);
        Long id = editionService.findByName(newEdition.getName()).getId();
        return "redirect:/editions/" + id;
    }
}
