package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.engine.EngineEditionService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

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
        return editionsPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String editionsPage(@PathVariable int page, Model model) {
        Page<EngineEdition> editions = editionService.findAll(page, 20);
        model.addAttribute("pages", IntStream.rangeClosed(1, editions.getTotalPages()).boxed().toList());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getEngineEditions().isEmpty()).toList()
        );
        model.addAttribute("editions", editions.getContent());
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

    @GetMapping("/secure/add")
    public String addEditionForm(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("edition", new EngineEdition());
        return "editions/add";
    }

    @PostMapping("/secure/add")
    public String addEdition(@ModelAttribute("edition") EngineEdition edition) {
        EngineEdition newEdition = new EngineEdition();
        newEdition.setBrand(edition.getBrand());
        newEdition.setName(edition.getName());
        newEdition.setImage(edition.getImage());
        editionService.save(edition);
        Long id = editionService.findByName(newEdition.getName()).getId();
        return "redirect:/editions/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editEditionForm(@PathVariable Long id, Model model) {
        model.addAttribute("edition", editionService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "editions/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editEdition(@ModelAttribute("edition") EngineEdition edition) {
        editionService.update(edition);
        return "redirect:/editions/" + edition.getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEdition(@PathVariable Long id) {
        editionService.delete(id);
        return "redirect:/editions";
    }
}
