package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.turbocharging.TurboTypeService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turbochargers")
@RequiredArgsConstructor
public class TurbochargerController {

    private final TurbochargerService turbochargerService;
    private final EquipmentService equipmentService;
    private final TurboTypeService turboTypeService;
    private final BrandService brandService;
    private final PersonService personService;

    @GetMapping("/{id}")
    public String getTurbocharger(@PathVariable("id") Long id, Model model) {
        Turbocharger turbocharger = turbochargerService.findById(id);
        model.addAttribute("turbocharger", turbocharger);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "turbochargers/turbocharger";
    }

    @GetMapping("/find-turbochargers")
    public String findTurbochargers(Model model, HttpServletRequest request) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getTurbochargers().isEmpty()).toList()
        );
        model.addAttribute("turboTypes", turboTypeService.findAll());
        model.addAttribute("turboTypesSelect",
                turboTypeService.findAll().stream().filter(turboType -> !turboType.getTurbochargers().isEmpty()).toList()
        );
        model.addAttribute("turbochargers",
                turbochargerService.findAll().stream().filter(turbocharger -> turbocharger.getTurboType() != null).toList()
        );
        String searchBrand = request.getParameter("searchBrand");
        String searchTurboType = request.getParameter("searchTurboType");
        String searchTurbocharger = request.getParameter("searchTurbocharger");
        model.addAttribute("findTurbochargers",
                turbochargerService.searchTurbochargersByNeighbourNamesNoSpaces(searchBrand + searchTurboType + searchTurbocharger)
        );
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchTurboType", searchTurboType);
        model.addAttribute("searchTurbocharger", searchTurbocharger);
        return "turbochargers/findTurbochargers";
    }

    @GetMapping("/secure/add")
    public String addTurbochargerForm(Model model, @RequestParam("turbotypeId") Long turbotypeId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        TurboType turboType = turboTypeService.findById(turbotypeId);
        model.addAttribute("turbocharger", new Turbocharger());
        model.addAttribute("turboType", turboType);
        model.addAttribute("brands", turboType.getBrands());
        return "turbochargers/add";
    }

    @PostMapping("/secure/add")
    public String addTurbocharger(@ModelAttribute Turbocharger turbocharger, @ModelAttribute TurboType turboType) {
        Turbocharger newTurbocharger = new Turbocharger();
        newTurbocharger.setName(turbocharger.getName());
        newTurbocharger.setBrand(turbocharger.getBrand());
        newTurbocharger.setTurboType(turboType);
        newTurbocharger.setImage(turbocharger.getImage());
        newTurbocharger.setRpm(turbocharger.getRpm());
        newTurbocharger.setTorque(turbocharger.getTorque());
        newTurbocharger.setDescription(turbocharger.getDescription());
        turbochargerService.save(newTurbocharger);
        Long id = turbochargerService.findByName(newTurbocharger.getName()).getId();
        return "redirect:/turbochargers/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editTurbochargerForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turbocharger", turbochargerService.findById(id));
        return "turbochargers/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editTurbocharger(@ModelAttribute("turbocharger") Turbocharger turbocharger) {
        turbochargerService.update(turbocharger);
        return "redirect:/turbochargers/{id}?brandId=" + turbocharger.getBrand().getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTurbocharger(@PathVariable("id") Long turbochargerId) {
        Turbocharger turbocharger = turbochargerService.findById(turbochargerId);
        turbocharger.removeEquipments(equipmentService.findAllByTurbochargerId(turbochargerId));
        turbochargerService.update(turbocharger);
        turbochargerService.delete(turbochargerId);
        //noinspection SpringMVCViewInspection
        return "redirect:/brands/" + turbocharger.getBrand().getId() + "/turbo-types";
    }
}
