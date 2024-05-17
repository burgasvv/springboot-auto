package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turbochargers")
@RequiredArgsConstructor
public class TurbochargerController {

    private final BrandService brandService;
    private final TurbochargerService turbochargerService;
    private final EquipmentService equipmentService;

    @GetMapping("/{id}")
    public String getTurbocharger(@PathVariable("id") Long id, Model model, @RequestParam("brandId") Long brandId) {
        Turbocharger turbocharger = turbochargerService.findById(id);
        turbocharger.setBrand(brandService.findById(brandId));
        model.addAttribute("turbocharger", turbocharger);
        return "turbochargers/turbocharger";
    }

    @GetMapping("/{id}/edit")
    public String editTurbochargerForm(Model model, @PathVariable("id") Long id) {
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
