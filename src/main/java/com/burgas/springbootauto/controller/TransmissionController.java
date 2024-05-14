package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transmissions")
public class TransmissionController {

    private final BrandService brandService;
    private final TransmissionService transmissionService;
    private final GearboxService gearboxService;
    private final DriveTypeService driveTypeService;
    private final EquipmentService equipmentService;

    public TransmissionController(BrandService brandService, TransmissionService transmissionService,
                                  GearboxService gearboxService, DriveTypeService driveTypeService,
                                  EquipmentService equipmentService) {
        this.brandService = brandService;
        this.transmissionService = transmissionService;
        this.gearboxService = gearboxService;
        this.driveTypeService = driveTypeService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/{id}")
    public String getTransmission(@PathVariable("id") Long id, Model model, @RequestParam("brandId") Long brandId) {
        Transmission transmission = transmissionService.findById(id);
        transmission.setBrand(brandService.findById(brandId));
        model.addAttribute("transmission", transmission);
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
        return "redirect:/transmissions/{id}?brandId=" + transmission.getBrand().getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTransmission(@PathVariable("id") Long transmissionId) {
        Transmission transmission = transmissionService.findById(transmissionId);
        transmission.removeEquipments(equipmentService.findAllByTransmissionId(transmissionId));
        transmissionService.update(transmission);
        transmissionService.delete(transmissionId);
        //noinspection SpringMVCViewInspection
        return "redirect:/brands/" + transmission.getBrand().getId() + "/gearboxes";
    }
}
