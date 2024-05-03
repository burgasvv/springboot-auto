package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.Car;
import com.burgas.springbootauto.entity.Tag;
import com.burgas.springbootauto.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final BrandService brandService;
    private final ClassificationService classificationService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Autowired
    public CarController(CarService carService, BrandService brandService, ClassificationService classificationService,
                         CategoryService categoryService, TagService tagService) {
        this.carService = carService;
        this.brandService = brandService;
        this.classificationService = classificationService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping
    public String cars(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "cars/cars";
    }

    @GetMapping("/{id}")
    public String car(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car",carService.findById(id));
        model.addAttribute("allTags", tagService.findAll());
        model.addAttribute("attachTag", new Tag());
        model.addAttribute("newTag", new Tag());
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

    @PostMapping("/{id}/add-tag")
    public String addTag(@ModelAttribute("newTag") Tag tag, @PathVariable("id") Long id) {
        Car car = carService.findById(id);
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        tagService.save(newTag);
        car.getTags().add(newTag);
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String search ,Model model) {
        model.addAttribute("cars", carService.searchCarsByAllNames(search));
        model.addAttribute("searchBrands", brandService.searchBrandByTitle(search));
        model.addAttribute("search", search);
        return "cars/search";
    }
}
