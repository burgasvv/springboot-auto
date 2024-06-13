package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.DriveUnit;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/drive-units")
@RequiredArgsConstructor
public class DriveUnitController {

    private final DriveUnitService driveUnitService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ClassificationService classificationService;
    private final CarService carService;
    private final PersonService personService;

    private void getSelectorsLists(Model model) {
        model.addAttribute("brands", brandService.findAll().stream().filter(brand -> !brand.getCars().isEmpty()).toList());
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
        model.addAttribute("classes",
                classificationService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
    }

    @GetMapping("/{id}/cars")
    public String cars(@PathVariable Long id, Model model) {
        return carsPages(id, 1, model);
    }

    @GetMapping("/{id}/cars/pages/{page}")
    public String carsPages(@PathVariable Long id, @PathVariable int page, Model model) {
        getSelectorsLists(model);
        model.addAttribute("driveUnit", driveUnitService.findById(id));
        Page<Car> cars = carService.findCarsByDriveUnitId(id, page, 25);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("pages", IntStream.rangeClosed(1, cars.getTotalPages()).boxed().toList());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "driveunits/cars";
    }

    @GetMapping("/{id}/cars/find-cars")
    public String findCars(@PathVariable Long id, Model model, HttpServletRequest req) {
        return findCarsPages(id, 1, model, req);
    }

    @GetMapping("/{id}/cars/find-cars/pages/{page}")
    public String findCarsPages(@PathVariable Long id, @PathVariable int page, Model model, HttpServletRequest request) {
        getSelectorsLists(model);
        DriveUnit driveUnit = driveUnitService.findById(id);
        model.addAttribute("driveUnit", driveUnit);
        String searchBrand = request.getParameter("searchBrand");
        String searchClass = request.getParameter("searchClass");
        String searchCategory = request.getParameter("searchCategory");
        Page<Car> cars = carService.searchDriveUnitCarsByBrandAndClassificationAndCategory(
                driveUnit.getName(), searchBrand + searchClass + searchCategory, page, 25
        );
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("pages", IntStream.rangeClosed(1, cars.getTotalPages()).boxed().toList());
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchClass", searchClass);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "driveunits/findCars";
    }
}
