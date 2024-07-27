package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.car.*;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.*;
import com.burgas.springbootauto.service.image.ImageService;
import com.burgas.springbootauto.service.person.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final BrandService brandService;
    private final ClassificationService classificationService;
    private final CategoryService categoryService;
    private final DriveUnitService driveUnitService;
    private final ImageService imageService;
    private final TagService tagService;
    private final PersonService personService;

    private void getSearchLists(Model model) {
        model.addAttribute("brands",
                brandService.findAll().stream().filter(brand -> !brand.getCars().isEmpty()).toList()
        );
        BrandController.getSearchLists(model, classificationService, categoryService, driveUnitService);
    }

    private static void paginate(Model model, Page<Car> pageCars) {
        Map<String, Image>imageMap = new HashMap<>();
        pageCars.getContent().forEach(car ->
            imageMap.put(car.getTitle(), car.getImages().stream().filter(Image::isPreview).findFirst().orElse(null))
        );
        model.addAllAttributes(
                Map.of(
                        "imageMap", imageMap,
                        "pages", IntStream.rangeClosed(1, pageCars.getTotalPages()).boxed().toList(),
                        "cars", pageCars.getContent()
                )
        );
    }

    @GetMapping
    public String cars(Model model) {
        return carsPage(1, model);
    }

    @GetMapping("/pages/{page}")
    public String carsPage(@PathVariable int page, Model model) {
        getSearchLists(model);
        paginate(model, carService.findPage(page, 25));
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "cars/cars";
    }

    @GetMapping("/find-cars")
    public String findCars(Model model, HttpServletRequest request) {
        return findCarsPage(1, model, request);
    }

    @GetMapping("/find-cars/pages/{page}")
    public String findCarsPage(@PathVariable int page, Model model, HttpServletRequest request) {
        getSearchLists(model);
        String searchBrand = request.getParameter("searchBrand");
        String searchClass = request.getParameter("searchClass");
        String searchCategory = request.getParameter("searchCategory");
        String searchDrive = request.getParameter("searchDrive");
        paginate(model, carService.searchCarsByKeyword(
                searchBrand + searchClass + searchCategory + searchDrive, page, 25));
        model.addAllAttributes(
                Map.of(
                        "searchBrand", searchBrand,
                        "searchClass", searchClass,
                        "searchCategory", searchCategory,
                        "searchDrive", searchDrive,
                        "user",
                        personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/findCars";
    }

    @GetMapping("/{id}")
    public String car(@PathVariable("id") Long id, Model model) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAllAttributes(
                Map.of("user", user,
                        "car", carService.findById(id),
                        "allTags", new HashSet<>(tagService.findAll()),
                        "attachTag", new Tag(),
                        "newTag", new Tag(),
                        "userEquipments", user.getEquipments().stream()
                                .filter(equipment -> !equipment.isAttached()).toList(),
                        "addingEquipment", new Equipment())
        );
        return "cars/car";
    }

    @GetMapping("/secure/add")
    public String addCarForm(Model model) {
        model.addAllAttributes(
                Map.of(
                        "car", new Car(),
                        "brands", brandService.findAll(),
                        "classes", classificationService.findAll(),
                        "categories", categoryService.findAll(),
                        "drives", driveUnitService.findAll(),
                        "user",
                        personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/add";
    }

    @PostMapping("/secure/add")
    public String addCar(@ModelAttribute Car car, @RequestPart MultipartFile file) {
        carService.create(car, file);
        return "redirect:/users/" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable("id") Long id, Model model) {
        model.addAllAttributes(
                Map.of(
                        "users", personService.findAll(),
                        "car", carService.findById(id),
                        "brands", brandService.findAll(),
                        "classes", classificationService.findAll(),
                        "categories", categoryService.findAll(),
                        "drives", driveUnitService.findAll(),
                        "user",
                        personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editCar(@ModelAttribute("car") @Valid Car car,  @RequestParam String username) {
        carService.editCar(car, username);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/add-preview-image")
    public String addCarPreviewImage(@PathVariable Long id, @RequestPart MultipartFile file) {
        carService.changePreviewImage(id, file);
        return "redirect:/cars/" + id;
    }

    @PostMapping("/{id}/remove-preview-image")
    public String removeCarPreviewImage(@PathVariable Long id) {
        carService.removePreviewImage(id);
        return "redirect:/cars/" + id;
    }

    @PostMapping("/{id}/add-images")
    public String addCarImages(@PathVariable Long id, @RequestPart("file") MultipartFile[] files) {
        carService.addImages(id, files);
        return "redirect:/cars/" + id;
    }

    @GetMapping("/{id}/images")
    public String carImages(@PathVariable Long id, Model model) {
        return carImagesPage(id, 1, model);
    }

    @GetMapping("/{id}/images/pages/{page}")
    public String carImagesPage(@PathVariable Long id, @PathVariable int page, Model model) {
        Page<Image> images = imageService.findImagesByCarId(id, page, 50);
        model.addAllAttributes(
                Map.of(
                        "car", carService.findById(id),
                        "carImages", images.getContent(),
                        "pages", IntStream.rangeClosed(1, images.getTotalPages()).boxed().toList(),
                        "user",
                        personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/images";
    }

    @PostMapping("/{id}/add-images-at-images-page")
    public String addCarImagesAtImagesPage(@PathVariable Long id, @RequestPart("file") MultipartFile[] files) {
        carService.addImages(id, files);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @DeleteMapping("/{id}/images/delete-image")
    public String deleteCarImage(@PathVariable Long id, @RequestParam Long imageId) {
        imageService.deletePreview(id, imageId);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @PostMapping("/{id}/set-preview/{imageId}")
    public String setCarPreview(@PathVariable Long id, @PathVariable Long imageId) {
        carService.setPreviewImage(id, imageId);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @PostMapping("/{id}/images/remove-preview-from-images")
    public String removeCarPreviewFromImages(@PathVariable Long id) {
        carService.removePreviewImage(id);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        return "redirect:/users/" + carService.updateAndDelete(id);
    }

    @GetMapping("/{id}/handover")
    public String handOver(@PathVariable("id") Long id, Model model) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAllAttributes(
                Map.of(
                        "user", user,
                        "car", carService.findById(id),
                        "users", personService.findAll().stream().filter(person -> !person.equals(user)).toList()
                )
        );
        return "cars/handover";
    }

    @PostMapping("/{id}/handover-done")
    public String handOver(@ModelAttribute("car") Car car) {
        carService.handover(car);
        return "redirect:/users/" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/search-by-tag")
    public String searchByTag(@RequestParam("tag") String tag, @RequestParam int carId, Model model) {
        return searchByTagPage(1,carId, model, tag);
    }

    @GetMapping("/search-by-tag/pages/{page}")
    public String searchByTagPage(@PathVariable("page") int page, @RequestParam int carId, Model model, @RequestParam("tag") String tag) {
        getSearchLists(model);
        Page<Car> cars = carService.searchCarsByTagName(tag, page, 25);
        List<Integer> pages = IntStream.rangeClosed(1, cars.getTotalPages()).boxed().toList();
        model.addAllAttributes(
                Map.of(
                        "carId", carId,
                        "tag", tag,
                        "carsByTag", cars.getContent(),
                        "pages", pages,
                        "user",
                        personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/carsByTag";
    }

    @GetMapping("/search-by-tag/search")
    public String searchByTagAndSelectors(@RequestParam("tag") String tag, @RequestParam int carId, Model model, HttpServletRequest request) {
        return searchByTagAndSelectorsPage(1, carId, model, tag, request);
    }

    @GetMapping("/search-by-tag/search/pages/{page}")
    public String searchByTagAndSelectorsPage(@PathVariable("page") int page, @RequestParam int carId, Model model,
                                              String tag, HttpServletRequest request) {
        getSearchLists(model);
        String searchBrand = request.getParameter("searchBrand");
        String searchClass = request.getParameter("searchClass");
        String searchCategory = request.getParameter("searchCategory");
        String searchDrive = request.getParameter("searchDrive");
        Page<Car> cars = carService.searchTagCarsByClassificationAndAndCategoryNoSpaces(
                tag, searchBrand + searchClass + searchCategory + searchDrive, page, 25);
        List<Integer> pages = IntStream.rangeClosed(1, cars.getTotalPages()).boxed().toList();
        model.addAllAttributes(
                Map.of(
                        "carId", carId,
                        "tag", tag,
                        "carsByTag", cars.getContent(),
                        "pages", pages,
                        "searchBrand", searchBrand,
                        "searchClass", searchClass,
                        "searchCategory", searchCategory,
                        "searchDrive", searchDrive,
                        "user", personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                )
        );
        return "cars/carsByTagAndSelectors";
    }

    @PostMapping("/{id}/attach-tag")
    public String attachTag(@PathVariable("id") Long id, @ModelAttribute("attachTag") Tag tag) {
        carService.attachTag(id, tag);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/add-tag")
    public String addTag(@ModelAttribute("newTag") Tag tag, @PathVariable("id") Long id) {
        carService.addTag(tag, id);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/attach-equipment")
    public String attachEquipment(@ModelAttribute("addingEquipment") Equipment equipment, @PathVariable("id") Long id) {
        carService.attachEquipment(equipment, id);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/remove-equipment-from-car")
    public String removeEquipment(@PathVariable("id") Long id, @RequestParam Long complId) {
        carService.removeEquipment(id, complId);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/remove-equipment-from-car-in-form")
    public String removeEquipmentInForm(@PathVariable("id") Long id, @RequestParam("complId") Long complId) {
        carService.removeEquipment(id, complId);
        return "redirect:/cars/{id}/handover";
    }
}
