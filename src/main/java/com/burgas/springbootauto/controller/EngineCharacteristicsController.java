package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.service.engine.EngineCharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/engine-characteristics")
@RequiredArgsConstructor
public class EngineCharacteristicsController {

    private final EngineCharacteristicsService engineCharacteristicsService;

    @PatchMapping("/{id}/edit")
    public String edit(@ModelAttribute("characteristics") EngineCharacteristics characteristics, Model model) {
        engineCharacteristicsService.update(characteristics);
        model.addAttribute("id", characteristics.getEngine().getId());
        return "redirect:/engines/{id}";
    }
}
