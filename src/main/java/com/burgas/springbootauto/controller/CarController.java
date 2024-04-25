package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.dao.*;
import com.burgas.springbootauto.model.Car;
import com.burgas.springbootauto.model.CarTag;
import com.burgas.springbootauto.model.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarDao carDao;
    private final BrandDao brandDao;
    private final CategoryDao categoryDao;
    private final TagDao tagDao;
    private final CarTagDao carTagDao;
    private final ClassDao classDao;

    public CarController(CarDao carDao, BrandDao brandDao, CategoryDao categoryDao,
                         TagDao tagDao, CarTagDao carTagDao, ClassDao classDao) {
        this.carDao = carDao;
        this.brandDao = brandDao;
        this.categoryDao = categoryDao;
        this.tagDao = tagDao;
        this.carTagDao = carTagDao;
        this.classDao = classDao;
    }

    @GetMapping
    public String cars(Model model) {
        model.addAttribute("cars", carDao.findAll());
        model.addAttribute("brands", brandDao.findAll());
        return "cars/cars";
    }

    @GetMapping("/{id}")
    public String car(@PathVariable("id") int id, Model model) {
        Car car = carDao.find(id);
        model.addAttribute("car", car);
        model.addAttribute("brand", brandDao.find(car.getBrandId()));
        model.addAttribute("carClass", classDao.find(car.getClassId()));
        model.addAttribute("category", categoryDao.find(car.getCategoryId()));
        model.addAttribute("tags", tagDao.findByCarId(car.getId()));
        model.addAttribute("allTags", tagDao.findAll());
        model.addAttribute("carTag", new CarTag());
        model.addAttribute("newTag", new Tag());
        return "cars/car";
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("brands", brandDao.findAll());
        model.addAttribute("classes", classDao.findAll());
        model.addAttribute("categories", categoryDao.findAll());
        return "cars/add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/add";
        }
        carDao.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable("id") int id, Model model) {
        Car car = carDao.find(id);
        model.addAttribute("car", car);
        model.addAttribute("brands", brandDao.findAll());
        model.addAttribute("classes", classDao.findAll());
        model.addAttribute("categories", categoryDao.findAll());
        return "cars/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editCar(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cars/edit";
        }
        carDao.update(car);
        return "redirect:/cars/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@ModelAttribute("car") Car car) {
        carDao.delete(car);
        return "redirect:/cars";
    }

    @GetMapping("/searchByTag")
    public String searchByTag(@RequestParam("search") String search, Model model) {
        model.addAttribute("brands", brandDao.findAll());
        model.addAttribute("carsByTag", carDao.searchByTagWord(search)
                        .stream().distinct().collect(Collectors.toList())
        );
        return "cars/carsByTag";
    }

    @PostMapping("/{id}/attachTag")
    public String attachTag(@PathVariable("id") int carId, @ModelAttribute("carTag") CarTag carTag) {
        carTag.setCarId(carId);
        carTagDao.save(carTag);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/addTag")
    public String addTag(@PathVariable("id") int carId, @ModelAttribute("newTag") Tag tag) {
        tagDao.save(tag);
        carTagDao.save(new CarTag(carId, tagDao.findByName(tag.getName()).getId()));
        return "redirect:/cars/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search") String search ,Model model) {
        model.addAttribute("cars", carDao.searchFull(search)
                .stream().distinct().collect(Collectors.toList())
        );
        model.addAttribute("searchBrands", brandDao.search(search));
        model.addAttribute("search", search);
        model.addAttribute("brands", brandDao.findAll());
        return "cars/search";
    }
}
