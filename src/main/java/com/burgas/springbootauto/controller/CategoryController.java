package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Category;
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

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final BrandService brandService;
    private final CarService carService;
    private final ClassificationService classificationService;
    private final CategoryService categoryService;
    private final DriveUnitService driveUnitService;
    private final PersonService personService;

    private void getSearchLists(Model model) {
        model.addAttribute("brands",
                brandService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
        model.addAttribute("classes",
                classificationService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
        model.addAttribute("drives",
                driveUnitService.findAll().stream().filter(drive -> !drive.getCars().isEmpty()).toList()
        );
    }

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("categories", categoryService.findAll());
        return "categories/categories";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("category", categoryService.findById(id));
        return "categories/category";
    }

    @GetMapping("/secure/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "categories/add";
    }

    @PostMapping("/secure/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "categories/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editCategory(@ModelAttribute Category category) {
        categoryService.update(category);
        return "redirect:/categories/" + category.getId();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/cars")
    public String cars(@PathVariable("id") Long id, Model model) {
        return categoryCarsPage(id,model,1);
    }

    @GetMapping("/{id}/cars/pages/{page}")
    public String categoryCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page) {
        getSearchLists(model);
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        Page<Car> cars = carService.findCarsByCategoryId(id, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "categories/cars";
    }

    @GetMapping("/{id}/find-category-cars")
    public String findCategoryCars(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        return findCategoryCarsPage(id,model,1,request);
    }

    @GetMapping("/{id}/find-category-cars/pages/{page}")
    public String findCategoryCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page, HttpServletRequest request) {
        getSearchLists(model);
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        String searchBrand = request.getParameter("searchBrand");
        String searchClass = request.getParameter("searchClass");
        String searchDrive = request.getParameter("searchDrive");
        Page<Car> cars = carService.searchCategoryCarsByBrandAndClassificationNoSpaces(
                category.getName(), searchBrand + searchClass + searchDrive, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchClass", searchClass);
        model.addAttribute("searchDrive", searchDrive);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "categories/findCategoryCars";
    }
}
