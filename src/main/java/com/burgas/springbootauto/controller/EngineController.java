package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EnginEditionService;
import com.burgas.springbootauto.service.engine.EngineCharacteristicsService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import lombok.RequiredArgsConstructor;
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
    private final EquipmentService equipmentService;
    private final EnginEditionService enginEditionService;
    private final EngineCharacteristicsService engineCharacteristicsService;

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
        Long brandId = enginEditionService.searchEngineEditionByEngines(List.of(engine)).getBrand().getId();
        engine.removeEquipments(equipmentService.findAllByEngineId(id));
        engineService.update(engine);
        engineCharacteristicsService.deleteByEngineId(id);
        engineService.delete(id);
        //noinspection SpringMVCViewInspection
        return "redirect:/brands/" + brandId + "/editions";
    }
}
