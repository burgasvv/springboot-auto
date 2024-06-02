package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.CategoryService;
import com.burgas.springbootauto.service.car.ClassificationService;
import com.burgas.springbootauto.service.person.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private final PersonService personService;

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
        Page<Car> cars = carService.searchCarsByClassificationAndAndCategoryNoSpaces(
                searchBrand + classification.getName() + searchCategory, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "classes/findClassCars";
    }

    private void getSearchLists(Model model) {
        model.addAttribute("brands",
                brandService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
    }
}
