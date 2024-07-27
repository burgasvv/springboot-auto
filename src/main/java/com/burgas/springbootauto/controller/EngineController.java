package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.engine.EngineEditionService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import com.burgas.springbootauto.service.person.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;
    private final FuelService fuelService;
    private final BrandService brandService;
    private final EngineEditionService engineEditionService;
    private final PersonService personService;

    @GetMapping("/{id}")
    public String engine(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Engine engine = engineService.findById(id);
        model.addAttribute("engine", engine);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "engines/engine";
    }

    @GetMapping("/find-engines")
    public String findEngines(Model model, HttpServletRequest request) {
        return findEnginePages(1, model, request);
    }

    @GetMapping("/find-engines/pages/{page}")
    public String findEnginePages(@PathVariable("page") Integer page, Model model, HttpServletRequest request) {
        String searchBrand = request.getParameter("searchBrand");
        String searchEdition = request.getParameter("searchEdition");
        String searchEngine = request.getParameter("searchEngine");
        String searchFuel = request.getParameter("searchFuel");
        Page<Engine> engines = engineService.searchEnginesByEngineBrandEditionCarNoSpaces(
                searchBrand + searchEdition + searchEngine + searchFuel, page, 25);
        model.addAttribute("pages", IntStream.rangeClosed(1, engines.getTotalPages()).boxed().toList());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(b -> !b.getEngineEditions().isEmpty()).toList()
        );
        model.addAttribute("editions", engineEditionService.findAll());
        model.addAttribute("engines",
                engineService.findAll().stream().filter(en -> en.getEngineEdition() != null).toList()
        );
        model.addAttribute("fuelTypes", fuelService.findAll());
        model.addAttribute("findEngines", engines.getContent());
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchEdition", searchEdition);
        model.addAttribute("searchEngine", searchEngine);
        model.addAttribute("searchFuel", searchFuel);
        return "engines/findEngines";
    }

    @GetMapping("/secure/add")
    public String addEngineForm(Model model, @RequestParam("editionId") Long editionId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("engine", new Engine());
        model.addAttribute("characteristics", new EngineCharacteristics());
        model.addAttribute("edition", engineEditionService.findById(editionId));
        model.addAttribute("fuel", fuelService.findAll());
        return "engines/add";
    }

    @PostMapping("/secure/add")
    public String addEngine(@ModelAttribute Engine engine,
                            @ModelAttribute EngineCharacteristics characteristics,
                            @ModelAttribute EngineEdition edition) {
        return "redirect:/engines/" + engineService.createEngine(engine,characteristics,edition);
    }

    @GetMapping("/{id}/edit")
    public String editEngineForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("engine", engineService.findById(id));
        model.addAttribute("fuel", fuelService.findAll());
        return "engines/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editEngine(@ModelAttribute("engine") Engine engine, Model model) {
        engineService.update(engine, model);
        return "engines/editCharacteristics";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEngine(@PathVariable("id") Long id) {
        //noinspection SpringMVCViewInspection
        return "redirect:/brands/" + engineService.delete(id) + "/editions";
    }
}
