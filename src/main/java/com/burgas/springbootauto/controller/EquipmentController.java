package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final TurbochargerService turbochargerService;
    private final CarService carService;
    private final PersonService personService;

    @GetMapping
    public String getAllEquipments(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        return "equipments/equipments";
    }

    @GetMapping("/{id}")
    public String getEquipment(@PathVariable("id") Long id, @RequestParam(name = "carId") Long carId, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("car", carService.findById(carId));
        model.addAttribute("equipment", equipmentService.findById(id));
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("addEngine", new Engine());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("addTransmission", new Transmission());
        model.addAttribute("turbochargers", turbochargerService.findAll());
        model.addAttribute("addTurbocharger", new Turbocharger());
        return "equipments/equipment";
    }

    @PostMapping("/{id}/add-engine")
    public String addEngine(@PathVariable("id") Long id, @ModelAttribute("engine") Engine engine) {
        System.out.println(engine.getId());
        Equipment equipment = equipmentService.findById(id);
        equipment.addEngine(engine);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @DeleteMapping("/{id}/remove-engine")
    public String removeEngine(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipment.removeEngine(equipment.getEngine());
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @PostMapping("/{id}/add-transmission")
    public String addTransmission(@PathVariable("id") Long id, @ModelAttribute("transmission") Transmission transmission) {
        Equipment equipment = equipmentService.findById(id);
        equipment.addTransmission(transmission);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @DeleteMapping("/{id}/remove-transmission")
    public String removeTransmission(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipment.removeTransmission(equipment.getTransmission());
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @PostMapping("/{id}/add-turbocharger")
    public String addTurbocharger(@PathVariable("id") Long id, @ModelAttribute("turbocharger") Turbocharger turbocharger) {
        Equipment equipment = equipmentService.findById(id);
        equipment.addTurbocharger(turbocharger);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @DeleteMapping("/{id}/remove-turbocharger")
    public String removeTurbocharger(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipment.removeTurbocharger(equipment.getTurbocharger());
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }
}
