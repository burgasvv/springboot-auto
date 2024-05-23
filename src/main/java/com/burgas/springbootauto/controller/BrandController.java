package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.engine.EnginEditionService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.engine.FuelService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurboTypeService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final EngineService engineService;
    private final EnginEditionService enginEditionService;
    private final FuelService fuelService;
    private final GearboxService gearboxService;
    private final DriveTypeService driveTypeService;
    private final TransmissionService transmissionService;
    private final TurboTypeService turboTypeService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;

    @GetMapping
    public String brands(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands", brandService.findAll());
        return "brands/brands";
    }

    @GetMapping("/{id}")
    public String brand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "brands/brand";
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

    @GetMapping("/{id}/cars")
    public String brandCars(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        model.addAttribute("brands",
                brandService.findAll().stream().filter(b -> !b.getCars().isEmpty()).toList());
        model.addAttribute("cars", brand.getCars());
        return "brands/cars";
    }

    @GetMapping("/search-brand-cars")
    public String searchBrandCars(@RequestParam("id") Long id, Model model) {
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        //noinspection SpringMVCViewInspection
        return "redirect:/brands/" + brand.getId() + "/cars";
    }

    @GetMapping("/{id}/editions")
    public String editions(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "editions/editions";
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

        newEngine.addEngineCharacteristics(engineCharacteristics);
        engineService.save(newEngine);

        return "redirect:/brands/{id}/editions";
    }

    @GetMapping("{id}/gearboxes")
    public String gearboxes(@PathVariable("id")Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "gearboxes/gearboxes";
    }

    @GetMapping("/{id}/add-gearbox")
    public String addGearboxForm(Model model, @PathVariable("id") Long brandId) {
        model.addAttribute("gearbox", new Gearbox());
        model.addAttribute("brand", brandService.findById(brandId));
        return "gearboxes/add";
    }

    @PostMapping("/{id}/add-gearbox")
    public String addGearbox(@ModelAttribute("gearbox") Gearbox gearbox, @PathVariable("id") Long id) {
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        newGearbox.addBrand(brandService.findById(id));
        gearboxService.save(newGearbox);
        return "redirect:/brands/{id}/gearboxes";
    }

    @GetMapping("/{id}/add-transmission")
    public String addTransmissionForm(Model model, @PathVariable("id") Long id, @RequestParam("gearboxId") Long gearboxId) {
        model.addAttribute("transmission", new Transmission());
        model.addAttribute("gearbox", gearboxService.findById(gearboxId));
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("driveTypes", driveTypeService.findAll());
        return "transmissions/add";
    }

    @PostMapping("/{id}/add-transmission")
    public String addTransmission(@ModelAttribute Transmission transmission, @ModelAttribute Gearbox gearbox) {
        Transmission newTransmission = new Transmission();
        newTransmission.setName(transmission.getName());
        newTransmission.setBrand(transmission.getBrand());
        newTransmission.setGearbox(gearbox);
        newTransmission.setDriveType(transmission.getDriveType());
        newTransmission.setImage(transmission.getImage());
        newTransmission.setDescription(transmission.getDescription());
        transmissionService.save(newTransmission);
        return "redirect:/brands/{id}/gearboxes";
    }

    @GetMapping("/{id}/turbo-types")
    public String turboTypes(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "turbotypes/turbotypes";
    }

    @GetMapping("/{id}/add-turbo-type")
    public String addTurboTypeForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("turboType", new TurboType());
        model.addAttribute("brand", brandService.findById(id));
        return "turbotypes/add";
    }

    @PostMapping("/{id}/add-turbo-type")
    public String addTurboType(@ModelAttribute("turboType") TurboType turboType, @PathVariable("id") Long id) {
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        newTurboType.addBrand(brandService.findById(id));
        turboTypeService.save(newTurboType);
        return "redirect:/brands/{id}/turbo-types";
    }

    @GetMapping("/{id}/add-turbocharger")
    public String addTurbochargerForm(Model model, @PathVariable("id") Long id, @RequestParam("turbotypeId") Long turbotypeId) {
        model.addAttribute("turbocharger", new Turbocharger());
        model.addAttribute("turboType", turboTypeService.findById(turbotypeId));
        model.addAttribute("brand", brandService.findById(id));
        return "turbochargers/add";
    }

    @PostMapping("/{id}/add-turbocharger")
    public String addTurbocharger(@ModelAttribute Turbocharger turbocharger, @ModelAttribute TurboType turboType) {
        Turbocharger newTurbocharger = new Turbocharger();
        newTurbocharger.setName(turbocharger.getName());
        newTurbocharger.setBrand(turbocharger.getBrand());
        newTurbocharger.setTurboType(turboType);
        newTurbocharger.setImage(turbocharger.getImage());
        newTurbocharger.setPowerIntake(turbocharger.getPowerIntake());
        newTurbocharger.setPowerGeneration(turbocharger.getPowerGeneration());
        newTurbocharger.setDescription(turbocharger.getDescription());
        turbochargerService.save(newTurbocharger);
        return "redirect:/brands/{id}/turbo-types";
    }
}
