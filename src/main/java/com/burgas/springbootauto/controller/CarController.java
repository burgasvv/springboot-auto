package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.*;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final EquipmentService equipmentService;
    private final BrandService brandService;
    private final ClassificationService classificationService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private void getSearchLists(Model model) {
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getCars().isEmpty()).toList()
        );
        model.addAttribute("classes",
                classificationService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
    }

    @GetMapping
    public String cars(Model model) {
        model.addAttribute("cars", carService.findAll());
        getSearchLists(model);
        model.addAttribute("searchBrand", new Brand());
        model.addAttribute("searchClass", new Classification());
        model.addAttribute("searchCategory", new Category());
        return "cars/cars";
    }

    @GetMapping("/find-cars")
    public String findCars(@RequestParam("title") String brand,
                           @RequestParam("name") String classification,
                           @RequestParam("name") String category, Model model) {

        getSearchLists(model);
        int i = classification.indexOf(",");
        String categoryName = category.substring(i).substring(1);
        String classificationName = classification.substring(0, i);
        model.addAttribute("cars", carService.searchCarsByAllNames(brand + classificationName + categoryName));
        Brand brandByTitle = brandService.findBrandByTitle(brand);
        model.addAttribute("searchBrand", Objects.requireNonNullElseGet(brandByTitle, Brand::new));
        Classification classificationByName = classificationService.findClassificationByName(classificationName);
        model.addAttribute("searchClass", Objects.requireNonNullElseGet(classificationByName, Classification::new));
        Category categoryByName = categoryService.findCategoryByName(categoryName);
        model.addAttribute("searchCategory", Objects.requireNonNullElseGet(categoryByName, Category::new));
        return "cars/findCars";
    }

    @GetMapping("/{id}")
    public String car(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car",carService.findById(id));
        model.addAttribute("allTags", tagService.findAll());
        model.addAttribute("attachTag", new Tag());
        model.addAttribute("newTag", new Tag());
        model.addAttribute("equipment", new Equipment());
        return "cars/car";
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("classes", classificationService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "cars/add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/add";
        }
        carService.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.findById(id));
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("classes", classificationService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "cars/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editCar(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/edit";
        }
        car.setTags(tagService.searchTagsByCars(car));
        carService.update(car);
        return "redirect:/cars/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@ModelAttribute("car") Car car) {
        carService.delete(car.getId());
        return "redirect:/cars";
    }

    @GetMapping("/search-by-tag")
    public String searchByTag(@RequestParam("search") String search, Model model) {
        model.addAttribute("carsByTag", carService.searchCarsByTagName(search));
        return "cars/carsByTag";
    }

    @PostMapping("/{id}/attach-tag")
    public String attachTag(@PathVariable("id") Long id, @ModelAttribute("attachTag") Tag tag) {
        Car car = carService.findById(id);
        car.getTags().add(tag);
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @PutMapping("/{id}/add-tag")
    public String addTag(@ModelAttribute("newTag") Tag tag, @PathVariable("id") Long id) {
        Car car = carService.findById(id);
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        car.getTags().add(newTag);
        tagService.save(newTag);
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/add-equipment")
    public String addEquipment(@ModelAttribute("equipment") Equipment equipment, @PathVariable("id") Long id) {
        Equipment newEquipment = new Equipment();
        newEquipment.setName(equipment.getName());
        newEquipment.setEngine(equipment.getEngine());
        newEquipment.setTransmission(equipment.getTransmission());
        Car car = carService.findById(id);
        car.addEquipment(newEquipment);
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @DeleteMapping("/{id}/delete-equipment")
    public String deleteEquipment(@SuppressWarnings("unused") @PathVariable("id") Long id, @RequestParam("complId") Long complId) {
        equipmentService.delete(complId);
        return "redirect:/cars/{id}";
    }
}
