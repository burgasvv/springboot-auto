package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.CategoryService;
import com.burgas.springbootauto.service.car.ClassificationService;
import com.burgas.springbootauto.service.car.DriveUnitService;
import com.burgas.springbootauto.service.person.PersonService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassificationController {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final CarService carService;
    private final ClassificationService classificationService;
    private final DriveUnitService driveUnitService;
    private final PersonService personService;

    private void getSearchLists(Model model) {
        model.addAttribute("brands",
                brandService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
        model.addAttribute("drives",
                driveUnitService.findAll().stream().filter(drive -> !drive.getCars().isEmpty()).toList()
        );
    }

    @GetMapping
    public String classes(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("classes", classificationService.findAll());
        return "classes/classes";
    }

    @GetMapping("/{id}")
    public String getClass(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("class", classificationService.findById(id));
        return "classes/class";
    }

    @GetMapping("/secure/add")
    public String addClassForm(Model model) {
        model.addAttribute("class", new Classification());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "classes/add";
    }

    @PostMapping("/secure/add")
    public String addClass(@ModelAttribute("class") Classification classification, @RequestPart MultipartFile file) {
        classificationService.save(classification, file);
        return "redirect:/classes";
    }

    @GetMapping("/{id}/edit")
    public String editClassForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("class", classificationService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "classes/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editClass(@ModelAttribute("class") Classification classification) {
        classificationService.update(classification);
        return "redirect:/classes/" + classification.getId();
    }

    @PostMapping("/{id}/change-image")
    public String changeImage(@PathVariable("id") Long id, @RequestPart("file") MultipartFile file) {
        Classification classification = classificationService.findById(id);
        classificationService.save(classification, file);
        return "redirect:/classes/" + id;
    }

    @PostMapping("/{id}/remove-image")
    public String removeImage(@PathVariable Long id) {
        Classification classification = classificationService.findById(id);
        classificationService.removeImage(classification);
        return "redirect:/classes/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteClass(@PathVariable("id") Long id) {
        classificationService.delete(id);
        return "redirect:/classes";
    }

    @GetMapping("/{id}/cars")
    public String cars(@PathVariable("id") Long id, Model model) {
        return classCarsPage(id,model,1);
    }

    @GetMapping("/{id}/cars/pages/{page}")
    public String classCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page) {
        getSearchLists(model);
        Classification classification = classificationService.findById(id);
        model.addAttribute("class", classification);
        Page<Car> cars = carService.findCarsByClassificationId(id, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "classes/cars";
    }

    @GetMapping("/{id}/find-class-cars")
    public String findClassCars(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        return findClassCarsPage(id,model,1,request);
    }

    @GetMapping("/{id}/find-class-cars/pages/{page}")
    public String findClassCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page, HttpServletRequest request) {
        getSearchLists(model);
        Classification classification = classificationService.findById(id);
        model.addAttribute("class", classification);
        String searchBrand = request.getParameter("searchBrand");
        String searchCategory = request.getParameter("searchCategory");
        String searchDrive = request.getParameter("searchDrive");
        Page<Car> cars = carService.searchClassificationCarsByBrandAndCategory(
                classification.getName(), searchBrand + searchCategory + searchDrive, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchDrive", searchDrive);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "classes/findClassCars";
    }
}
