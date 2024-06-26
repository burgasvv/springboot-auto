package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EngineEditionService;
import com.burgas.springbootauto.service.engine.EngineCharacteristicsService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import com.burgas.springbootauto.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;
    private final FuelService fuelService;
    private final BrandService brandService;
    private final EquipmentService equipmentService;
    private final EngineEditionService engineEditionService;
    private final EngineCharacteristicsService engineCharacteristicsService;
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
    public String findEngines(Model model, @RequestParam("searchBrand") String brand,
                              @RequestParam("searchEdition") String edition,
                              @RequestParam("searchEngine") String engine, @RequestParam("searchFuel") String fuel) {
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
        model.addAttribute("findEngines",
                engineService.searchEnginesByEngineBrandEditionCarNoSpaces(brand + edition + engine + fuel)
        );
        model.addAttribute("searchBrand", brand);
        model.addAttribute("searchEdition", edition);
        model.addAttribute("searchEngine", engine);
        model.addAttribute("searchFuel", fuel);
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

        Engine newEngine = new Engine();
        newEngine.setEngineEdition(edition);
        newEngine.setName(engine.getName());
        newEngine.setImage(engine.getImage());
        newEngine.setDescription(engine.getDescription());
        newEngine.setFuel(engine.getFuel());

        EngineCharacteristics engineCharacteristics = new EngineCharacteristics();
        engineCharacteristics.setRpm(characteristics.getRpm());
        engineCharacteristics.setVolume(characteristics.getVolume());
        engineCharacteristics.setTorque(characteristics.getTorque());
        engineCharacteristics.setCylinders(characteristics.getCylinders());

        newEngine.addEngineCharacteristics(engineCharacteristics);
        engineService.save(newEngine);
        Long id = engineService.findByName(newEngine.getName()).getId();

        return "redirect:/engines/" + id;
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
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("characteristics", engineCharacteristicsService.findByEngineId(engine.getId()));
        engineService.update(engine);
        return "engines/editCharacteristics";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEngine(@PathVariable("id") Long id) {
        Engine engine = engineService.findById(id);
        Long brandId = engineEditionService.searchEngineEditionByEngines(List.of(engine)).getBrand().getId();
        engine.removeEquipments(equipmentService.findAllByEngineId(id));
        engineService.update(engine);
        engineCharacteristicsService.deleteByEngineId(id);
        engineService.delete(id);
//        noinspection SpringMVCViewInspection
        return "redirect:/brands/" + brandId + "/editions";
    }
}
