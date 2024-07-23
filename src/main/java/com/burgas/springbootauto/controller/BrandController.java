package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.car.CategoryService;
import com.burgas.springbootauto.service.car.ClassificationService;
import com.burgas.springbootauto.service.car.DriveUnitService;
import com.burgas.springbootauto.service.engine.EngineEditionService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.DriveTypeService;
import com.burgas.springbootauto.service.transmission.GearboxService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurboTypeService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
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
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final CarService carService;
    private final EngineService engineService;
    private final EngineEditionService engineEditionService;
    private final GearboxService gearboxService;
    private final DriveTypeService driveTypeService;
    private final TransmissionService transmissionService;
    private final TurboTypeService turboTypeService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;
    private final ClassificationService classificationService;
    private final CategoryService categoryService;
    private final DriveUnitService driveUnitService;

    private static void paginate(Model model, Page<Brand> brandPage) {
        int totalPages = brandPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pages", pageNumbers);
        }
        model.addAttribute("brands", brandPage.getContent());
    }

    @GetMapping
    public String brands(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return page(1, model);
    }

    @GetMapping("/pages/{page}")
    public String page(@PathVariable int page, Model model) {
        paginate(model, brandService.findPage(page, 15));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/brands";
    }

    @GetMapping("/search")
    public String searchBrand(Model model, @RequestParam(required = false) String brandName) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return searchBrandPage(1, model, brandName);
    }

    @GetMapping("/search/pages/{page}")
    public String searchBrandPage(@PathVariable int page, Model model, @RequestParam(required = false) String brandName) {
        paginate(model, brandService.findBrandByBrandName(brandName,page, 15));
        model.addAttribute("brandName", brandName);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/findBrands";
    }

    @GetMapping("/{id}")
    public String brand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/brand";
    }

    @GetMapping("/secure/add")
    public String addBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/add";
    }

    @PostMapping("/secure/add")
    public String addBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/brands/" + brandService.findBrandByTitle(brand.getTitle()).getId();
    }

    @GetMapping("/{id}/edit")
    public String editBrandForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "brands/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editBrand(@ModelAttribute Brand brand) {
        brandService.update(brand);
        return "redirect:/brands/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBrand(@PathVariable Long id) {
        brandService.delete(brandService.findById(id));
        return "redirect:/brands";
    }

    private void getSearchLists(Model model) {
        getSearchLists(model, classificationService, categoryService, driveUnitService);
    }

    static void getSearchLists(Model model, ClassificationService classificationService,
                               CategoryService categoryService, DriveUnitService driveUnitService) {
        model.addAttribute("classes",
                classificationService.findAll().stream().filter(classification -> !classification.getCars().isEmpty()).toList()
        );
        model.addAttribute("categories",
                categoryService.findAll().stream().filter(category -> !category.getCars().isEmpty()).toList()
        );
        model.addAttribute("drives",
                driveUnitService.findAll().stream().filter(drive -> !drive.getCars().isEmpty()).toList()
        );
    }

    @GetMapping("/{id}/cars")
    public String brandCars(@PathVariable("id") Long id, Model model) {
        return brandCarsPage(id,model,1);
    }

    @GetMapping("/{id}/cars/pages/{page}")
    public String brandCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page) {
        getSearchLists(model);
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        Page<Car> cars = carService.findCarsByBrandId(id, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/cars";
    }

    @GetMapping("/{id}/search-brand-cars")
    public String searchBrandCars(Model model, HttpServletRequest request , @PathVariable Long id) {
        return searchBrandCarsPage(id,model,1,request);
    }

    @GetMapping("/{id}/search-brand-cars/pages/{page}")
    public String searchBrandCarsPage(@PathVariable("id") Long id, Model model, @PathVariable int page, HttpServletRequest request) {
        getSearchLists(model);
        Brand brand = brandService.findById(id);
        String searchClass = request.getParameter("searchClass");
        String searchCategory = request.getParameter("searchCategory");
        String searchDrive = request.getParameter("searchDrive");
        Page<Car> cars = carService.searchCarsByClassificationAndAndCategoryNoSpaces(
                brand.getTitle() + searchClass + searchCategory + searchDrive, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("pages", pages);
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("searchClass", searchClass);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchDrive", searchDrive);
        model.addAttribute("brand", brand);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "brands/findBrandCars";
    }

    @GetMapping("/{id}/editions")
    public String brandEditions(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("engines", engineService.findAll());
        return "brands/editions";
    }

    @GetMapping("/{id}/add-edition")
    public String addEditionForm(Model model, @PathVariable("id") Long brandId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("edition", new EngineEdition());
        model.addAttribute("brand", brandService.findById(brandId));
        return "brands/addBrandEdition";
    }

    @PostMapping("/{id}/add-edition")
    public String addEdition(@ModelAttribute("edition") EngineEdition engineEdition, @PathVariable("id") Long id) {
        EngineEdition edition = new EngineEdition();
        edition.setName(engineEdition.getName());
        edition.setImage(engineEdition.getImage());
        edition.setBrand(brandService.findById(id));
        engineEditionService.save(edition);
        Long editionId = engineEditionService.findByName(edition.getName()).getId();
        return "redirect:/editions/" + editionId;
    }

    @GetMapping("{id}/gearboxes")
    public String brandGearboxes(@PathVariable("id")Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "brands/gearboxes";
    }

    @GetMapping("/{id}/add-gearbox")
    public String addGearboxForm(Model model, @PathVariable("id") Long brandId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("gearbox", new Gearbox());
        model.addAttribute("brand", brandService.findById(brandId));
        return "brands/addBrandGearbox";
    }

    @PostMapping("/{id}/add-gearbox")
    public String addGearbox(@ModelAttribute("gearbox") Gearbox gearbox, @PathVariable("id") Long id) {
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        newGearbox.addBrand(brandService.findById(id));
        gearboxService.save(newGearbox);
        Long gearboxId = gearboxService.findByName(newGearbox.getName()).getId();
        return "redirect:/gearboxes/" + gearboxId;
    }

    @GetMapping("/{id}/add-transmission")
    public String addTransmissionForm(Model model, @PathVariable("id") Long id, @RequestParam("gearboxId") Long gearboxId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("transmission", new Transmission());
        model.addAttribute("gearbox", gearboxService.findById(gearboxId));
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("driveTypes", driveTypeService.findAll());
        return "brands/addBrandTransmission";
    }

    @PostMapping("/{id}/add-transmission")
    public String addTransmission(@PathVariable("id") Long id, @ModelAttribute Transmission transmission, @ModelAttribute Gearbox gearbox) {
        Transmission newTransmission = new Transmission();
        newTransmission.setName(transmission.getName());
        newTransmission.setBrand(brandService.findById(id));
        newTransmission.setGearbox(gearbox);
        newTransmission.setRatio(transmission.getRatio());
        newTransmission.setFinalRatio(transmission.getFinalRatio());
        newTransmission.setDriveType(transmission.getDriveType());
        newTransmission.setImage(transmission.getImage());
        newTransmission.setDescription(transmission.getDescription());
        transmissionService.save(newTransmission);
        Long transmissionId = transmissionService.findByName(newTransmission.getName()).getId();
        return "redirect:/transmissions/" + transmissionId;
    }

    @GetMapping("/{id}/turbo-types")
    public String turboTypes(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brand", brandService.findById(id));
        return "brands/turbotypes";
    }

    @GetMapping("/{id}/add-turbo-type")
    public String addTurboTypeForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turboType", new TurboType());
        model.addAttribute("brand", brandService.findById(id));
        return "brands/addBrandTurboType";
    }

    @PostMapping("/{id}/add-turbo-type")
    public String addTurboType(@ModelAttribute("turboType") TurboType turboType, @PathVariable("id") Long id) {
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        newTurboType.addBrand(brandService.findById(id));
        turboTypeService.save(newTurboType);
        Long turboTypeId = turboTypeService.findByName(newTurboType.getName()).getId();
        return "redirect:/turbo-types/" + turboTypeId;
    }

    @GetMapping("/{id}/add-turbocharger")
    public String addTurbochargerForm(Model model, @PathVariable("id") Long id, @RequestParam("turbotypeId") Long turbotypeId) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("turbocharger", new Turbocharger());
        model.addAttribute("turboType", turboTypeService.findById(turbotypeId));
        model.addAttribute("brand", brandService.findById(id));
        return "brands/addBrandTurbocharger";
    }

    @PostMapping("/{id}/add-turbocharger")
    public String addTurbocharger(@PathVariable("id") Long id, @ModelAttribute Turbocharger turbocharger, @ModelAttribute TurboType turboType) {
        Turbocharger newTurbocharger = new Turbocharger();
        newTurbocharger.setName(turbocharger.getName());
        newTurbocharger.setBrand(brandService.findById(id));
        newTurbocharger.setTurboType(turboType);
        newTurbocharger.setImage(turbocharger.getImage());
        newTurbocharger.setRpm(turbocharger.getRpm());
        newTurbocharger.setTorque(turbocharger.getTorque());
        newTurbocharger.setDescription(turbocharger.getDescription());
        turbochargerService.save(newTurbocharger);
        Long turbochargerId = turbochargerService.findByName(newTurbocharger.getName()).getId();
        return "redirect:/turbochargers/" + turbochargerId;
    }
}
