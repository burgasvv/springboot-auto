package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final CarService carService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;

    @GetMapping
    public String allEquipments(Model model) {
        return equipmentsPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String equipmentsPage(@PathVariable int page, Model model) {
        Page<Equipment> equipments = equipmentService.findAll(page, 50);
        int totalPages = equipments.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("equipments", equipments.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "equipments/equipments";
    }

    @GetMapping("/search")
    public String equipmentsSearch(Model model, @RequestParam String search) {
        return equipmentsSearchPage(1, model, search);
    }

    @GetMapping("/search/pages/{page}")
    public String equipmentsSearchPage(@PathVariable int page, Model model, @RequestParam String search) {
        Page<Equipment> equipments = equipmentService.searchEquipmentsByCarAndPerson(search, page, 50);
        int totalPages = equipments.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("equipments", equipments.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("search", search);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "equipments/findEquipments";
    }

    @GetMapping("/{id}")
    public String equipment(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Equipment equipment = equipmentService.findById(id);
        Person owner = equipment.getPerson();
        model.addAttribute("owner", owner);
        model.addAttribute("ownerCars",
                owner.getCars().stream().filter(car -> car.getEquipments().size() < 5).toList()
        );
        model.addAttribute("equipment", equipment);
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("addEngine", new Engine());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("addTransmission", new Transmission());
        model.addAttribute("turbochargers", turbochargerService.findAll());
        model.addAttribute("addTurbocharger", new Turbocharger());
        model.addAttribute("users",
                personService.findAll().stream().filter(person -> !person.equals(owner)).toList()
        );
        model.addAttribute("userForShare", new Person());
        return "equipments/equipment";
    }

    @GetMapping("/secure/add")
    public String addEquipment(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("equipment", new Equipment());
        return "equipments/add";
    }

    @PostMapping("/secure/add")
    public String addEquipment(@ModelAttribute("equipment") Equipment equipment, @RequestPart MultipartFile file) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        equipment.setAttached(false);
        equipment.setPerson(user);
        equipmentService.saveMultipart(equipment, file);
        return "redirect:/users/" + user.getUsername();
    }

    @PostMapping("/{id}/change-image")
    public String changeImage(@PathVariable("id") Long id, @RequestPart("file") MultipartFile file) {
        Equipment equipment = equipmentService.findById(id);
        equipmentService.saveMultipart(equipment, file);
        return "redirect:/equipments/" + id;
    }

    @PostMapping("/{id}/remove-image")
    public String removeImage(@PathVariable Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipmentService.removeImage(equipment);
        return "redirect:/equipments/" + id;
    }

    @GetMapping("/{id}/edit-equipment")
    public String editEquipment(@PathVariable("id") Long id,  Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Equipment equipment = equipmentService.findById(id);
        model.addAttribute("equipment", equipment);
        return "equipments/edit";
    }

    @PostMapping("/{id}/edit-equipment")
    public String editEquipment(@ModelAttribute Equipment equipment, Model model) {
        Equipment temp = equipmentService.findById(equipment.getId());
        equipment.setAttached(temp.isAttached());
        equipment.setPerson(temp.getPerson());
        equipment.setCar(temp.getCar());
        equipment.setEngine(temp.getEngine());
        equipment.setTransmission(temp.getTransmission());
        equipment.setTurbocharger(temp.getTurbocharger());
        model.addAttribute("equipment", equipment);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @DeleteMapping("/{id}/delete-equipment")
    public String deleteEquipment(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipmentService.delete(equipment.getId());
        return "redirect:/users/" + equipment.getPerson().getUsername();
    }

    @PostMapping("/{id}/share-equipment")
    public String shareEquipment(@PathVariable("id") Long id, @ModelAttribute("userForShare") Person userForShare) {
        Equipment equipment = equipmentService.findById(id);
        Person person = personService.findById(userForShare.getId());
        Equipment newEquipment = new Equipment();
        newEquipment.setPerson(person);
        newEquipment.setName(equipment.getName());
        newEquipment.setCar(null);
        newEquipment.setEngine(equipment.getEngine());
        newEquipment.setTransmission(equipment.getTransmission());
        newEquipment.setTurbocharger(equipment.getTurbocharger());
        newEquipment.setAttached(false);
        newEquipment.setImage(equipment.getImage());
        equipmentService.save(newEquipment);
        return "redirect:/equipments/{id}";
    }

    @PostMapping("/{id}/attach-to-car")
    public String attachToCar(@PathVariable("id") Long id, @RequestParam Long carId) {
        equipmentService.attachToCar(equipmentService.findById(id), carService.findById(carId));
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{id}/detach-from-car")
    public String detachFromCar(@PathVariable("id") Long id, @RequestParam Long carId) {
        equipmentService.detachFromCar(equipmentService.findById(id));
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/{id}/add-engine")
    public String addEngine(@PathVariable("id") Long id, @ModelAttribute("engine") Engine engine) {
        Equipment equipment = equipmentService.findById(id);
        equipment.addEngine(engine);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @PostMapping("/{id}/remove-engine")
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

    @PostMapping("/{id}/remove-transmission")
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

    @PostMapping("/{id}/remove-turbocharger")
    public String removeTurbocharger(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipment.removeTurbocharger(equipment.getTurbocharger());
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }
}
