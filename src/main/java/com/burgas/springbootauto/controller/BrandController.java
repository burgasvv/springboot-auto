package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;
    private final EngineService engineService;
    private final EnginEditionService enginEditionService;
    private final EngineCharacteristicsService engineCharacteristicsService;
    private final FuelService fuelService;

    public BrandController(BrandService brandService, EngineService engineService, EnginEditionService enginEditionService,
                           EngineCharacteristicsService engineCharacteristicsService, FuelService fuelService) {
        this.brandService = brandService;
        this.engineService = engineService;
        this.enginEditionService = enginEditionService;
        this.engineCharacteristicsService = engineCharacteristicsService;
        this.fuelService = fuelService;
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

    @GetMapping("/{id}/editions")
    public String editions(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id);
        brand.setEngineEditions(enginEditionService.searchEngineEditionsByBrandId(brand.getId()));
        brand.getEngineEditions().forEach(engineEdition -> {
            engineEdition.setEngines(engineService.searchEnginesByEngineEditionId(engineEdition.getId()));
            engineEdition.getEngines().forEach(engine ->
                engine.setEngineCharacteristics(engineCharacteristicsService.searchEngineCharacteristicsByEngineId(engine.getId()))
            );
        });
        brandService.update(brand);
        model.addAttribute("brand", brand);
        return "brands/editions";
    }

    @GetMapping("/{id}/add-edition")
    public String addEditionForm(Model model, @PathVariable("id") Long brandId) {
        model.addAttribute("edition", new EngineEdition());
        model.addAttribute("brand", brandService.findById(brandId));
        return "editions/add";
    }

    @PostMapping("/{id}/add-edition")
    public String addEdition(@ModelAttribute("edition") EngineEdition engineEdition, @PathVariable("id") Long id) {
        EngineEdition edition = new EngineEdition();
        edition.setName(engineEdition.getName());
        edition.setImage(engineEdition.getImage());
        edition.setBrand(brandService.findById(id));
        enginEditionService.save(edition);
        return "redirect:/brands/{id}/editions";
    }

    @GetMapping("/{id}/add-engine")
    public String addEngineForm(Model model, @PathVariable("id") Long brandId, @RequestParam("editionId") Long editionId) {
        model.addAttribute("engine", new Engine());
        model.addAttribute("characteristics", new EngineCharacteristics());
        model.addAttribute("brand", brandService.findById(brandId));
        model.addAttribute("edition", enginEditionService.findById(editionId));
        model.addAttribute("fuel", fuelService.findAll());
        return "engines/add";
    }

    @PostMapping("/{id}/add-engine")
    public String addEngine(@ModelAttribute Engine engine,
                            @ModelAttribute EngineCharacteristics characteristics,
                            @ModelAttribute EngineEdition edition) {

        Engine newEngine = new Engine();
        newEngine.setEngineEdition(edition);
        newEngine.setName(engine.getName());
        newEngine.setImage(engine.getImage());
        newEngine.setDescription(engine.getDescription());
        newEngine.setFuel(engine.getFuel());

        EngineCharacteristics engineCharacteristics = new EngineCharacteristics();
        engineCharacteristics.setVolume(characteristics.getVolume());
        engineCharacteristics.setTorque(characteristics.getTorque());
        engineCharacteristics.setPower(characteristics.getPower());
        engineCharacteristics.setPiston(characteristics.getPiston());
        engineCharacteristics.setCylinders(characteristics.getCylinders());
        engineCharacteristics.setCompression(characteristics.getCompression());
        engineCharacteristics.setEngine(newEngine);
        engineService.save(newEngine);
        engineCharacteristicsService.save(engineCharacteristics);
        return "redirect:/brands/{id}/editions";
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
