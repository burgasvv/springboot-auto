package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;

    @GetMapping
    public String allEquipments(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("equipments", equipmentService.findAll());
        return "equipments/equipments";
    }

    @GetMapping("/{id}")
    public String equipment(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        Equipment equipment = equipmentService.findById(id);
        Person owner = equipment.getPerson();
        model.addAttribute("owner", owner);
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

    @GetMapping("/add")
    public String addEquipment(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("equipment", new Equipment());
        return "equipments/add";
    }

    @PostMapping("/add")
    public String addEquipment(@ModelAttribute("equipment") Equipment equipment) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        equipment.setAttached(false);
        equipment.setPerson(user);
        equipmentService.save(equipment);
        return "redirect:/users/" + user.getUsername();
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

    @PostMapping("/{id}/add-engine")
    public String addEngine(@PathVariable("id") Long id, @ModelAttribute("engine") Engine engine) {
        Equipment equipment = equipmentService.findById(id);
        equipment.addEngine(engine);
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }

    @DeleteMapping("/{id}/remove-engine")
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

    @DeleteMapping("/{id}/remove-transmission")
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

    @DeleteMapping("/{id}/remove-turbocharger")
    public String removeTurbocharger(@PathVariable("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        equipment.removeTurbocharger(equipment.getTurbocharger());
        equipmentService.update(equipment);
        return "redirect:/equipments/{id}";
    }
}
