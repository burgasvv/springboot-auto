package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transmissions")
public class TransmissionController {

    private final TransmissionService transmissionService;
    private final DriveTypeService driveTypeService;
    private final EquipmentService equipmentService;

    public TransmissionController(TransmissionService transmissionService, DriveTypeService driveTypeService,
                                  EquipmentService equipmentService) {
        this.transmissionService = transmissionService;
        this.driveTypeService = driveTypeService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/{id}")
    public String getTransmission(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transmission", transmissionService.findById(id));
        return "transmissions/transmission";
    }

    @GetMapping("/{id}/edit")
    public String editTransmissionForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("transmission", transmissionService.findById(id));
        model.addAttribute("driveTypes", driveTypeService.findAll());
        return "transmissions/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editTransmission(@ModelAttribute("transmission") Transmission transmission) {
        transmissionService.update(transmission);
        return "redirect:/brands";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTransmission(@PathVariable("id") Long id) {
        Transmission transmission = transmissionService.findById(id);
        transmission.removeEquipments(equipmentService.findAllByTransmissionId(id));
        transmissionService.update(transmission);
        transmissionService.delete(id);
        return "redirect:/brands";
    }
}
