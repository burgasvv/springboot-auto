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
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final EquipmentService equipmentService;
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

    private static void paginate(Model model, Page<Car> pageCars) {
        int totalPages = pageCars.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
        Map<String, Image>imageMap = new HashMap<>();
        pageCars.getContent().forEach(car ->
            imageMap.put(car.getTitle(), car.getImages().stream().filter(Image::isPreview).findFirst().orElse(null))
        );
        model.addAttribute("imageMap", imageMap);
        model.addAttribute("pages", pageNumbers);
        model.addAttribute("cars", pageCars.getContent());
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
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchClass", searchClass);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchDrive", searchDrive);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "cars/findCars";
    }

    @GetMapping("/{id}")
    public String car(@PathVariable("id") Long id, Model model) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Car car = carService.findById(id);
        List<Equipment>equipments = new ArrayList<>();
        if (user != null) {
            List<Equipment> temp = user.getEquipments().stream().filter(equipment -> !equipment.isAttached()).toList();
            equipments.addAll(temp);
        }
        model.addAttribute("user", user);
        model.addAttribute("car",car);
        model.addAttribute("allTags", new HashSet<>(tagService.findAll()));
        model.addAttribute("attachTag", new Tag());
        model.addAttribute("newTag", new Tag());
        model.addAttribute("userEquipments", equipments);
        model.addAttribute("addingEquipment", new Equipment());
        return "cars/car";
    }

    @GetMapping("/secure/add")
    public String addCarForm(Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("car", new Car());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("classes", classificationService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("drives", driveUnitService.findAll());
        return "cars/add";
    }

    @SneakyThrows
    @PostMapping("/secure/add")
    public String addCar(@ModelAttribute Car car, @RequestPart MultipartFile file) {
        car.setPerson(personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        carService.create(car, file);
        return "redirect:/users/" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/{id}/edit")
    public String editCarForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("users", personService.findAll());
        model.addAttribute("car", carService.findById(id));
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("classes", classificationService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("drives", driveUnitService.findAll());
        return "cars/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editCar(@ModelAttribute("car") @Valid Car car,  BindingResult bindingResult) {
        Car temp = carService.findById(car.getId());
        if (bindingResult.hasErrors()) {
            return "cars/edit";
        }
        car.setPerson(temp.getPerson());
        car.setTags(tagService.searchTagsByCars(car));
        carService.update(car);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/add-preview-image")
    public String addCarPreviewImage(@PathVariable Long id, @RequestPart MultipartFile file) {
        Car car = carService.findById(id);
        carService.changePreviewImage(car, file);
        return "redirect:/cars/" + id;
    }

    @PostMapping("/{id}/remove-preview-image")
    public String removeCarPreviewImage(@PathVariable Long id) {
        Car car = carService.findById(id);
        carService.removePreviewImage(car);
        return "redirect:/cars/" + id;
    }

    @PostMapping("/{id}/add-images")
    public String addCarImages(@PathVariable Long id, @RequestPart("file") MultipartFile[] files) {
        Car car = carService.findById(id);
        carService.addImages(car, files);
        return "redirect:/cars/" + id;
    }

    @GetMapping("/{id}/images")
    public String carImages(@PathVariable Long id, Model model) {
        return carImagesPage(id, 1, model);
    }

    @GetMapping("/{id}/images/pages/{page}")
    public String carImagesPage(@PathVariable Long id, @PathVariable int page, Model model) {
        model.addAttribute("car", carService.findById(id));
        Page<Image> images = imageService.findImagesByCarId(id, page, 50);
        model.addAttribute("carImages", images.getContent());
        model.addAttribute("pages", IntStream.rangeClosed(1, images.getTotalPages()).boxed().toList());
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "cars/images";
    }

    @PostMapping("/{id}/add-images-at-images-page")
    public String addCarImagesAtImagesPage(@PathVariable Long id, @RequestPart("file") MultipartFile[] files) {
        Car car = carService.findById(id);
        carService.addImages(car, files);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @DeleteMapping("/{id}/images/delete-image")
    public String deleteCarImage(@PathVariable Long id, @RequestParam Long imageId) {
        imageService.deletePreview(carService.findById(id), imageService.findById(imageId));
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @PostMapping("/{id}/set-preview/{imageId}")
    public String setCarPreview(@PathVariable Long id, @PathVariable Long imageId) {
        carService.setPreviewImage(carService.findById(id), imageService.findById(imageId));
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @PostMapping("/{id}/images/remove-preview-from-images")
    public String removeCarPreviewFromImages(@PathVariable Long id) {
        Car car = carService.findById(id);
        carService.removePreviewImage(car);
        //noinspection SpringMVCViewInspection
        return "redirect:/cars/" + id + "/images";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Car car = carService.findById(id);
        car.removeEquipments(car.getEquipments());
        carService.update(car);
        carService.delete(car.getId());
        return "redirect:/users/" + car.getPerson().getUsername();
    }

    @GetMapping("/{id}/handover")
    public String handOver(@PathVariable("id") Long id, Model model) {
        Person user = personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        model.addAttribute("car", carService.findById(id));
        model.addAttribute("users",
                personService.findAll().stream().filter(person -> !person.equals(user)).toList()
        );
        return "cars/handover";
    }

    @PostMapping("/{id}/handover-done")
    public String handOver(@ModelAttribute("car") Car car) {
        Car handoverCar = carService.findById(car.getId());
        List<Equipment> handoverEquipments = equipmentService.findAllByCarId(car.getId());
        Person handoverPerson = car.getPerson();
        handoverCar.setPerson(handoverPerson);
        handoverCar.removeEquipments(handoverEquipments);
        carService.update(handoverCar);

        List<Equipment>newEquipments = new ArrayList<>();
        for (Equipment equipment : handoverEquipments) {
            Equipment newEquipment = getEquipment(equipment, handoverPerson);
            newEquipments.add(newEquipment);
        }
        equipmentService.saveAll(newEquipments);

        handoverCar.addEquipments(newEquipments);
        carService.update(handoverCar);

        return "redirect:/users/" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private static @NotNull Equipment getEquipment(Equipment equipment, Person handoverPerson) {
        Equipment newEquipment = new Equipment();
        newEquipment.setCar(equipment.getCar());
        newEquipment.setAttached(equipment.isAttached());
        newEquipment.setImage(equipment.getImage());
        newEquipment.setName(equipment.getName());
        newEquipment.setPerson(handoverPerson);
        newEquipment.setEngine(equipment.getEngine());
        newEquipment.setTransmission(equipment.getTransmission());
        newEquipment.setTurbocharger(equipment.getTurbocharger());
        return newEquipment;
    }

    @GetMapping("/search-by-tag")
    public String searchByTag(@RequestParam("tag") String tag, @RequestParam int carId, Model model) {
        return searchByTagPage(1,carId, model, tag);
    }

    @GetMapping("/search-by-tag/pages/{page}")
    public String searchByTagPage(@PathVariable("page") int page, @RequestParam int carId, Model model, @RequestParam("tag") String tag) {
        getSearchLists(model);
        model.addAttribute("carId", carId);
        model.addAttribute("tag", tag);
        Page<Car> cars = carService.searchCarsByTagName(tag, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("carsByTag", cars.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
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
        model.addAttribute("carId", carId);
        model.addAttribute("tag", tag);
        String searchBrand = request.getParameter("searchBrand");
        String searchClass = request.getParameter("searchClass");
        String searchCategory = request.getParameter("searchCategory");
        String searchDrive = request.getParameter("searchDrive");
        Page<Car> cars = carService.searchTagCarsByClassificationAndAndCategoryNoSpaces(
                tag, searchBrand + searchClass + searchCategory + searchDrive, page, 25);
        int totalPages = cars.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().toList();
        model.addAttribute("carsByTag", cars.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("searchBrand", searchBrand);
        model.addAttribute("searchClass", searchClass);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchDrive", searchDrive);
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        return "cars/carsByTagAndSelectors";
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
        car.getTags().add(newTag);
        tagService.save(newTag);
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/attach-equipment")
    public String attachEquipment(@ModelAttribute("addingEquipment") Equipment equipment, @PathVariable("id") Long id) {
        Car car = carService.findById(id);
        car.addEquipment(equipmentService.findById(equipment.getId()));
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/remove-equipment-from-car")
    public String removeEquipment(@PathVariable("id") Long id, @RequestParam Long complId) {
        Car car = carService.findById(id);
        car.removeEquipment(equipmentService.findById(complId));
        carService.save(car);
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/remove-equipment-from-car-in-form")
    public String removeEquipmentInForm(@PathVariable("id") Long id, @RequestParam("complId") Long complId) {
        Car car = carService.findById(id);
        car.removeEquipment(equipmentService.findById(complId));
        carService.save(car);
        return "redirect:/cars/{id}/handover";
    }
}
