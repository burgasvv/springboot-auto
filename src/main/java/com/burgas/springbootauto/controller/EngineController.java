package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EngineCharacteristicsService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/engines")
public class EngineController {

    private final EngineService engineService;
    private final FuelService fuelService;
    private final EquipmentService equipmentService;
    private final EngineCharacteristicsService engineCharacteristicsService;

    public EngineController(EngineService engineService, FuelService fuelService, EquipmentService equipmentService,
                            EngineCharacteristicsService engineCharacteristicsService) {
        this.engineService = engineService;
        this.fuelService = fuelService;
        this.equipmentService = equipmentService;
        this.engineCharacteristicsService = engineCharacteristicsService;
    }

    @GetMapping("/{id}")
    public String findEngine(@PathVariable("id") Long id, Model model) {
        Engine engine = engineService.findById(id);
        engine.setEngineCharacteristics(engineCharacteristicsService.searchEngineCharacteristicsByEngineId(id));
        model.addAttribute("engine", engine);
        return "engines/engine";
    }

    @GetMapping("/{id}/edit")
    public String editEngineForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("engine", engineService.findById(id));
        model.addAttribute("fuel", fuelService.findAll());
        return "engines/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editEngine(@ModelAttribute("engine") Engine engine, Model model) {
        model.addAttribute("characteristics", engineCharacteristicsService.findByEngineId(engine.getId()));
        engineService.update(engine);
        return "engines/editCharacteristics";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEngine(@PathVariable("id") Long id) {
        Engine engine = engineService.findById(id);
        engine.removeEquipments(equipmentService.findAllByEngineId(id));
        engineService.update(engine);
        engineCharacteristicsService.deleteByEngineId(id);
        engineService.delete(id);
        return "redirect:/brands";
    }
}
