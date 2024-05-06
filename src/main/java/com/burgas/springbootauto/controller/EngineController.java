package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.EngineCharacteristicsService;
import com.burgas.springbootauto.service.EngineService;
import com.burgas.springbootauto.service.FuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/engines")
public class EngineController {

    private final EngineService engineService;
    private final FuelService fuelService;
    private final EngineCharacteristicsService engineCharacteristicsService;

    public EngineController(EngineService engineService, FuelService fuelService,
                            EngineCharacteristicsService engineCharacteristicsService) {
        this.engineService = engineService;
        this.fuelService = fuelService;
        this.engineCharacteristicsService = engineCharacteristicsService;
    }

    @GetMapping("/{id}")
    public String find(@PathVariable("id") Long id, Model model) {
        Engine engine = engineService.findById(id);
        engine.setEngineCharacteristics(engineCharacteristicsService.searchEngineCharacteristicsByEngineId(id));
        model.addAttribute("engine", engine);
        return "engines/engine";
    }

    @GetMapping("/{id}/edit")
    public String editEngineForm(Model model, @PathVariable("id") Long id) {
        Engine engine = engineService.findById(id);
        model.addAttribute("engine", engine);
        model.addAttribute("characteristics", engine.getEngineCharacteristics());
        model.addAttribute("edition", engine.getEngineEdition());
        model.addAttribute("fuel", fuelService.findAll());
        return "engines/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editEngine(@ModelAttribute Engine engine,
                            @ModelAttribute EngineCharacteristics characteristics,
                            @ModelAttribute EngineEdition edition) {
        engineService.update(engine);
        engineCharacteristicsService.update(characteristics);
        return "redirect:/engines/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        engineService.delete(id);
        return "redirect:/brands/{id}/editions";
    }
}
