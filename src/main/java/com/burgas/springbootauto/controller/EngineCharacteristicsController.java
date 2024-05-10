package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.service.engine.EngineCharacteristicsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/engine-characteristics")
public class EngineCharacteristicsController {

    private final EngineCharacteristicsService engineCharacteristicsService;

    public EngineCharacteristicsController(EngineCharacteristicsService engineCharacteristicsService) {
        this.engineCharacteristicsService = engineCharacteristicsService;
    }

    @PatchMapping("/{id}/edit")
    public String edit(@ModelAttribute("characteristics") EngineCharacteristics characteristics) {
        engineCharacteristicsService.update(characteristics);
        return "brands/brands";
    }
}
