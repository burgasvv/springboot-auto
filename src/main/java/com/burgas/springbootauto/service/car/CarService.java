package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.car.Tag;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.car.CarRepository;
import com.burgas.springbootauto.repository.car.TagRepository;
import com.burgas.springbootauto.repository.image.ImageRepository;
import com.burgas.springbootauto.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final TagRepository tagRepository;
    private final EquipmentService equipmentService;
    private final ImageRepository imageRepository;

    private static @NotNull PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
    }

    public Page<Car> findPage(int page, int size) {
        PageRequest pageable = getPageRequest(page, size);
        return carRepository.findAll(pageable);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Page<Car>findCarsByBrandId(@NotNull Long brandId, int page, int size) {
        return carRepository.findCarsByBrandId(brandId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByClassificationId(@NotNull Long classId, int page, int size) {
        return carRepository.findCarsByClassificationId(classId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByCategoryId(@NotNull Long categoryId, int page, int size) {
        return carRepository.findCarsByCategoryId(categoryId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByPersonId(Long personId, int page, int size) {
        return carRepository.findCarsByPersonId(personId, getPageRequest(page, size));
    }

    public Page<Car>searchUserCarsByCategoryAndClassificationAndBrand(
            String username, String search, int page, int size) {
        return carRepository.searchUserCarsByCategoryAndClassificationAndBrand(username, search, getPageRequest(page, size));
    }

    public Page<Car> searchCarsByTagName(String tagName, int page, int size) {
        return carRepository.searchCarsByTagName(tagName, getPageRequest(page, size));
    }

    public Page<Car> searchTagCarsByClassificationAndAndCategoryNoSpaces(String tag, String search, int page, int size) {
        PageRequest pageRequest = getPageRequest(page, size);
        return carRepository.searchTagCarsByClassificationAndAndCategoryNoSpaces(tag, search, pageRequest);
    }

    public Page<Car> searchCarsByClassificationAndAndCategoryNoSpaces(String search, int page, int size) {
        return carRepository.searchCarsByClassificationAndAndCategoryNoSpaces(search, getPageRequest(page, size));
    }

    public Page<Car> searchCategoryCarsByBrandAndClassificationNoSpaces(
            String category, String search, int page, int size) {
        return carRepository.searchCategoryCarsByBrandAndClassificationNoSpaces(category, search, getPageRequest(page, size));
    }

    public Page<Car> searchClassificationCarsByBrandAndCategory(String classification, String search,
                                                                int page, int size) {
        return carRepository.searchClassificationCarsByBrandAndCategory(classification, search, getPageRequest(page, size));
    }

    public Page<Car>findCarsByDriveUnitId(@NotNull Long driveUnitId, int page, int size) {
        return carRepository.findCarsByDriveUnitId(driveUnitId, getPageRequest(page, size));
    }

    public Page<Car> searchDriveUnitCarsByBrandAndClassificationAndCategory(String driveUnit, String search,
                                                                            int page, int size) {
        return carRepository.searchDriveUnitCarsByBrandAndClassificationAndCategory(driveUnit, search, getPageRequest(page, size));
    }

    public Page<Car> searchCarsByKeyword(String keyword, int page, int size) {
        return carRepository.searchCarsWithNoSpaces(keyword, getPageRequest(page, size));
    }

    public List<Car> searchCarsByAllNames(String search) {
        return carRepository.searchCarsByAllNames(search).stream().distinct().toList();
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void create(Car car,  MultipartFile multipartFile) {
        car.setHasPreview(false);
        if (multipartFile.getSize() != 0) {
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename() + UUID.randomUUID());
            image.setPreview(true);
            image.setData(multipartFile.getBytes());
            car.addImage(image);
            car.setHasPreview(true);
        }
        carRepository.save(car);
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void changePreviewImage(Long carId, MultipartFile multipartFile) {
        Car car = carRepository.findById(carId).orElse(null);
        if (multipartFile.getSize() != 0) {
            Objects.requireNonNull(car).getImages().stream().filter(Image::isPreview).forEach(image -> image.setPreview(false));
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename() + UUID.randomUUID());
            image.setPreview(true);
            image.setData(multipartFile.getBytes());
            car.addImage(image);
            car.setHasPreview(true);
            carRepository.save(car);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removePreviewImage(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        Objects.requireNonNull(car).getImages().stream().filter(Image::isPreview).forEach(image -> image.setPreview(false));
        car.setHasPreview(false);
        carRepository.save(car);
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void addImages(Long carId, MultipartFile[] files) {
        Car car = carRepository.findById(carId).orElse(null);
        for (MultipartFile file : files) {
            Image image = new Image();
            image.setName(file.getOriginalFilename() + UUID.randomUUID());
            image.setPreview(false);
            image.setData(file.getBytes());
            Objects.requireNonNull(car).addImage(image);
            carRepository.save(car);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removeEquipment(Long carId, Long equipmentId) {
        Car car = carRepository.findById(carId).orElse(null);
        Objects.requireNonNull(car).removeEquipment(equipmentService.findById(equipmentId));
        carRepository.save(car);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void attachEquipment(Equipment equipment, Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        Objects.requireNonNull(car).addEquipment(equipmentService.findById(equipment.getId()));
        carRepository.save(car);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void addTag(Tag tag, Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        Objects.requireNonNull(car).getTags().add(newTag);
        tagRepository.save(newTag);
        carRepository.save(car);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void attachTag(Long carId, Tag tag) {
        Car car = carRepository.findById(carId).orElse(null);
        Objects.requireNonNull(car).getTags().add(tag);
        carRepository.save(car);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void handover(Car car) {
        Car handoverCar = carRepository.findById(car.getId()).orElse(null);
        List<Equipment> handoverEquipments = equipmentService.findAllByCarId(car.getId());
        Person handoverPerson = car.getPerson();
        Objects.requireNonNull(handoverCar).setPerson(handoverPerson);
        handoverCar.removeEquipments(handoverEquipments);
        carRepository.save(handoverCar);

        List<Equipment>newEquipments = new ArrayList<>();
        for (Equipment equipment : handoverEquipments) {
            newEquipments.add(Equipment.builder().car(equipment.getCar())
                    .attached(equipment.isAttached()).image(equipmentService.saveNewImage(equipment.getImage()))
                    .name(equipment.getName()).person(handoverPerson).engine(equipment.getEngine())
                    .transmission(equipment.getTransmission()).turbocharger(equipment.getTurbocharger()).build());
        }
        equipmentService.saveAll(newEquipments);

        handoverCar.addEquipments(newEquipments);
        carRepository.save(handoverCar);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public String updateAndDelete(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        Person person = Objects.requireNonNull(car).getPerson();
        Objects.requireNonNull(car).removeEquipments(car.getEquipments());
        carRepository.save(car);
        carRepository.delete(car);
        return person.getUsername();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void editCar(Car car, String username) {
        car.setPerson(personRepository.findPersonByUsername(username));
        car.setHasPreview(true);
        car.setTags(tagRepository.searchTagsByCars(List.of(car)));
        carRepository.save(car);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void setPreviewImage(Long carId, Long imageId) {
        Car car = carRepository.findById(carId).orElse(null);
        Image image = imageRepository.findById(imageId).orElse(null);
        if (!Objects.requireNonNull(car).isHasPreview()){
            car.setHasPreview(true);
        }
        car.getImages().stream().filter(Image::isPreview).forEach(im -> im.setPreview(false));
        car.getImages().stream().filter(im -> im.equals(image)).forEach(im -> im.setPreview(true));
        carRepository.save(car);
    }
}
