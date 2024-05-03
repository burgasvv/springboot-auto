package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.Brand;
import com.burgas.springbootauto.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String brands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brands/brands";
    }

    @GetMapping("/{id}")
    public String brand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "brands/brand";
    }

    @GetMapping("/{id}/cars")
    public String brandCars(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        model.addAttribute("cars", brand.getCars());
        return "brands/cars";
    }

    @GetMapping("/add")
    public String addBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "brands/add";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute("brand") @Valid Brand brand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "brands/add";
        }
        brandService.save(brand);
        return "redirect:/brands";
    }

    @GetMapping("/{id}/edit")
    public String editBrandForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "brands/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editBrand(@ModelAttribute("brand") @Valid Brand brand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "brands/edit";
        }
        brandService.update(brand);
        return "redirect:/brands/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBrand(@ModelAttribute("brand") Brand brand) {
        brandService.delete(brand.getId());
        return "redirect:/brands";
    }
}
