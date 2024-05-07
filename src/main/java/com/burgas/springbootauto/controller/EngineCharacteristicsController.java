package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.service.EngineCharacteristicsService;
import com.burgas.springbootauto.service.EngineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/engine-characteristics")
public class EngineCharacteristicsController {

    private final EngineCharacteristicsService engineCharacteristicsService;
    private final EngineService engineService;

    public EngineCharacteristicsController(EngineCharacteristicsService engineCharacteristicsService, EngineService engineService) {
        this.engineCharacteristicsService = engineCharacteristicsService;
        this.engineService = engineService;
    }

    @PatchMapping("/{id}/edit")
    public String edit(@ModelAttribute("characteristics") EngineCharacteristics characteristics) {
        engineCharacteristicsService.update(characteristics);
        return "brands/brands";
    }
}
