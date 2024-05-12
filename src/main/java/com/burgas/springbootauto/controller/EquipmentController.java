package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.service.car.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/{id}")
    public String getEquipment(@PathVariable("id") Long id, Model model) {
        model.addAttribute("equipment", equipmentService.findById(id));
        return "equipments/equipment";
    }
}
