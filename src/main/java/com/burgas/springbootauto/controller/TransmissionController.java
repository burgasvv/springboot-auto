package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transmissions")
@RequiredArgsConstructor
public class TransmissionController {

    private final TransmissionService transmissionService;
    private final GearboxService gearboxService;
    private final DriveTypeService driveTypeService;
    private final EquipmentService equipmentService;
    private final PersonService personService;

    @GetMapping("/{id}")
    public String getTransmission(@PathVariable("id") Long id, Model model) {
        Transmission transmission = transmissionService.findById(id);
        model.addAttribute("transmission", transmission);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
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

    @GetMapping("/add")
    public String addTransmissionForm(Model model, @RequestParam("gearboxId") Long gearboxId) {
        Gearbox gearbox = gearboxService.findById(gearboxId);
        model.addAttribute("transmission", new Transmission());
        model.addAttribute("brands", gearbox.getBrands());
        model.addAttribute("gearbox", gearbox);
        model.addAttribute("driveTypes", driveTypeService.findAll());
        return "transmissions/add";
    }

    @PostMapping("/add")
    public String addTransmission(@ModelAttribute Transmission transmission, @ModelAttribute Gearbox gearbox) {
        Transmission newTransmission = new Transmission();
        newTransmission.setName(transmission.getName());
        newTransmission.setBrand(transmission.getBrand());
        newTransmission.setGearbox(gearbox);
        newTransmission.setDriveType(transmission.getDriveType());
        newTransmission.setImage(transmission.getImage());
        newTransmission.setDescription(transmission.getDescription());
        transmissionService.save(newTransmission);
        Long id = transmissionService.findByName(newTransmission.getName()).getId();
        return "redirect:/transmissions/" + id;
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
