package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final BrandService brandService;
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

    @GetMapping("/find-transmissions")
    public String findTransmissions(Model model, HttpServletRequest request) {
        getSearchLists(model, personService, brandService, gearboxService, transmissionService, driveTypeService);
        String searchBrand = request.getParameter("searchBrand");
        String searchGearbox = request.getParameter("searchGearbox");
        String searchTransmission = request.getParameter("searchTransmission");
        String searchDriveType = request.getParameter("searchDriveType");
        model.addAttribute("findTransmissions",
                transmissionService.searchTransmissionsByNeighbourNamesNoSpaces(
                        searchBrand + searchGearbox + searchTransmission + searchDriveType
                )
        );
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchGearbox", searchGearbox);
        model.addAttribute("searchTransmission", searchTransmission);
        model.addAttribute("searchDriveType", searchDriveType);
        return "transmissions/findTransmissions";
    }

    static void getSearchLists(Model model, PersonService personService, BrandService brandService,
                               GearboxService gearboxService, TransmissionService transmissionService, DriveTypeService driveTypeService) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getTransmissions().isEmpty()).toList()
        );
        model.addAttribute("gearboxes", gearboxService.findAll());
        model.addAttribute("gearboxesSelect",
                gearboxService.findAll().stream().filter(gearbox -> !gearbox.getTransmissions().isEmpty()).toList()
        );
        model.addAttribute("transmissions",
                transmissionService.findAll().stream().filter(transmission -> transmission.getGearbox() != null).toList()
        );
        model.addAttribute("driveTypes", driveTypeService.findAll()
                .stream().filter(driveType -> !driveType.getTransmissions().isEmpty()).toList()
        );
    }

    @GetMapping("/secure/add")
    public String addTransmissionForm(Model model, @RequestParam("gearboxId") Long gearboxId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Gearbox gearbox = gearboxService.findById(gearboxId);
        model.addAttribute("transmission", new Transmission());
        model.addAttribute("brands", gearbox.getBrands());
        model.addAttribute("gearbox", gearbox);
        model.addAttribute("driveTypes", driveTypeService.findAll());
        return "transmissions/add";
    }

    @PostMapping("/secure/add")
    public String addTransmission(@ModelAttribute Transmission transmission, @ModelAttribute Gearbox gearbox) {
        Transmission newTransmission = new Transmission();
        newTransmission.setName(transmission.getName());
        newTransmission.setBrand(transmission.getBrand());
        newTransmission.setGearbox(gearbox);
        newTransmission.setFinalRatio(transmission.getFinalRatio());
        newTransmission.setRatio(transmission.getRatio());
        newTransmission.setDriveType(transmission.getDriveType());
        newTransmission.setImage(transmission.getImage());
        newTransmission.setDescription(transmission.getDescription());
        transmissionService.save(newTransmission);
        Long id = transmissionService.findByName(newTransmission.getName()).getId();
        return "redirect:/transmissions/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editTransmissionForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
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
