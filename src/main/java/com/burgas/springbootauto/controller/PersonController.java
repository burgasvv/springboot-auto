package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.person.RoleRepository;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.EquipmentService;
import com.burgas.springbootauto.service.person.PersonService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final RoleRepository roleRepository;
    private final CarService carService;
    private final EquipmentService equipmentService;

    @GetMapping
    public String users(Model model) {
        return usersPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String usersPage(@PathVariable int page, Model model) {
        Page<Person> users = personService.findAll(page, 10);
        int totalPages = users.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("users", users.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/users";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        return searchUsersPage(1, model, search);
    }

    @GetMapping("/search/pages/{page}")
    public String searchUsersPage(@PathVariable int page, Model model, String search) {
        Page<Person> users = personService.searchAllByFirstnameAndLastnameAndUsername(search, page, 10);
        int totalPages = users.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("search", search);
        model.addAttribute("users", users.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/search";
    }

    @GetMapping("/{name}")
    public String user(@PathVariable String name, Model model) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("users",
                personService.findAll().stream()
                        .filter(person -> !person.equals(owner) && !person.getRole().equals(roleRepository.findByName("ADMIN"))).toList()
        );
        return "users/user";
    }

    @GetMapping("/{name}/edit")
    public String edit(@PathVariable String name, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("owner", personService.findPersonByUsername(name));
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/edit";
    }

    @PostMapping("/secure/edit")
    public String edit(@ModelAttribute Person owner, @RequestParam Long roleId) {
        personService.editUser(owner, roleId);
        return "forward:/logout";
    }

    @PostMapping("/{name}/change-image")
    public String changeImage(@PathVariable String name, @RequestPart MultipartFile file) {
        personService.removeImage(name);
        personService.changeImage(name, file);
        return "redirect:/users/" + name;
    }

    @PostMapping("/{name}/add-image")
    public String addImage(@PathVariable String name, @RequestPart MultipartFile file) {
        personService.changeImage(name, file);
        return "redirect:/users/" + name;
    }

    @PostMapping("{name}/remove-image")
    public String removeImage(@PathVariable String name) {
        personService.removeImage(name);
        return "redirect:/users/" + name;
    }

    @PostMapping("/secure/make-admin")
    public String makeAdmin(@RequestParam String selectUser) {
        Person admin = personService.makeAdmin(selectUser);
        return "redirect:/users/" + admin.getUsername();
    }

    @PostMapping("/{name}/ban")
    public String ban(@PathVariable String name) {
        Person banned = personService.ban(name);
        return "redirect:/users/" + banned.getUsername();
    }

    @PostMapping("/{name}/unban")
    public String unban(@PathVariable String name) {
        Person unbanned = personService.unban(name);
        return "redirect:/users/" + unbanned.getUsername();
    }

    @PostMapping("/{name}/delete")
    public String delete(@PathVariable String name) {
        personService.delete(name);
        return "forward:/logout";
    }

    @GetMapping("/{name}/cars")
    public String cars(@PathVariable String name, Model model) {
        return userCarsPage(name, model, 1);
    }

    @GetMapping("/{name}/cars/pages/{page}")
    public String userCarsPage(@PathVariable String name, Model model, @PathVariable int page) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        Page<Car> cars = carService.findCarsByPersonId(owner.getId(), page, 20);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/cars";
    }

    @GetMapping("/{name}/cars/search")
    public String userCarsSearch(@PathVariable String name, Model model, @RequestParam String search) {
        return userCarsSearchPage(name, model, search, 1);
    }

    @GetMapping("/{name}/cars/search/pages/{page}")
    public String userCarsSearchPage(@PathVariable String name, Model model,
                                     @RequestParam String search, @PathVariable int page) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        Page<Car> cars = carService.searchUserCarsByCategoryAndClassificationAndBrand(name, search, page, 20);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("search", search);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/findUserCars";
    }

    @GetMapping("/{name}/equipments")
    public String equipments(@PathVariable String name, Model model) {
        return userEquipmentsPage(name, model, 1);
    }

    @GetMapping("/{name}/equipments/pages/{page}")
    public String userEquipmentsPage(@PathVariable String name, Model model, @PathVariable int page) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        Page<Equipment> equipments = equipmentService.findAllByPersonId(owner.getId(), page, 25);
        List<Integer> pages = IntStream.rangeClosed(1, equipments.getTotalPages()).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("equipments", equipments);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/equipments";
    }

    @GetMapping("/{name}/equipments/search")
    public String userEquipmentsSearch(@PathVariable String name, Model model, @RequestParam String search) {
        return userEquipmentsSearchPage(model, name, search, 1);
    }

    @GetMapping("/{name}/equipments/search/pages/{page}")
    public String userEquipmentsSearchPage(Model model, @PathVariable String name, @RequestParam String search,
                                           @PathVariable int page) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("owner", owner);
        Page<Equipment> equipments = equipmentService.searchEquipmentsByBrandAndCar(owner.getUsername(), search, page, 25);
        List<Integer> pages = IntStream.rangeClosed(1, equipments.getTotalPages()).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("equipments", equipments);
        model.addAttribute("search", search);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/findUserEquipments";
    }

    @GetMapping("/{name}/chats")
    public String userChats(@PathVariable String name, Model model) {
        Person owner = personService.findPersonByUsername(name);
        model.addAttribute("users",
                personService.findAll().stream().filter(person -> !person.equals(owner))
                        .filter(Person::isEnabled).filter(Person::isVerified).toList()
        );
        model.addAttribute("owner", owner);
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/chats";
    }

    @GetMapping("/{name}/chats/{receiverName}")
    public String userChat(@PathVariable String name, @PathVariable String receiverName, Model model) {
        model.addAttribute("owner", personService.findPersonByUsername(name));
        model.addAttribute("receiver", personService.findPersonByUsername(receiverName));
        model.addAttribute("guest",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "users/chat";
    }
}
