package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.car.CarRepository;
import com.burgas.springbootauto.repository.car.EquipmentRepository;
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ImageRepository imageRepository;
    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Page<Equipment> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "name"));
        return equipmentRepository.findAll(pageRequest);
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    public List<Equipment> findAllByCarId(@NotNull Long carId) {
        return equipmentRepository.findAllByCarId(carId);
    }

    public Page<Equipment>findAllByPersonId(Long id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "name"));
        return equipmentRepository.findAllByPersonId(id, pageRequest);
    }

    public Page<Equipment> searchEquipmentsByCarAndPerson(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "name"));
        return equipmentRepository.searchEquipmentsByCarAndPerson(search, pageRequest);
    }

    public Page<Equipment> searchEquipmentsByBrandAndCar(String username, String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "name"));
        return equipmentRepository.searchEquipmentsByBrandAndCar(username, search, pageRequest);
    }

    public List<Equipment>findAllByEngineId(Long id) {
        return equipmentRepository.findAllByEngineId(id);
    }

    public List<Equipment>findAllByTransmissionId(Long id) {
        return equipmentRepository.findAllByTransmissionId(id);
    }

    public List<Equipment> findAllByTurbochargerId(Long id) {
        return equipmentRepository.findAllByTurbochargerId(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void save(Long equipmentId, Long userId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        Person person = personRepository.findById(userId).orElse(null);
        equipmentRepository.save(
                Equipment.builder().person(person)
                .name(Objects.requireNonNull(equipment).getName())
                .car(null).engine(equipment.getEngine())
                .transmission(equipment.getTransmission()).turbocharger(equipment.getTurbocharger())
                .attached(false).image(saveNewImage(equipment.getImage())).build()
        );
    }

    @SneakyThrows
    @Transactional
    public void saveMultipart(Equipment equipment, MultipartFile file) {
        if (file.getSize() != 0) {
            Image image = Image.builder().isPreview(true)
                    .name(file.getOriginalFilename())
                    .data(file.getBytes())
                    .build();
            imageRepository.save(image);
            equipment.setImage(image);
        }
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void saveAll(List<Equipment> equipments) {
        equipmentRepository.saveAll(equipments);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public String deleteRedirectingToUser(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElse(null);
        equipmentRepository.deleteById(id);
        return Objects.requireNonNull(equipment).getPerson().getUsername();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void attachToCar(Long equipmentId, Long carId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        Car car = carRepository.findById(carId).orElse(null);
        Objects.requireNonNull(equipment).setCar(car);
        equipment.setAttached(true);
        equipmentRepository.save(equipment);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void detachFromCar(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        Objects.requireNonNull(equipment).setCar(null);
        equipment.setAttached(false);
        equipmentRepository.save(equipment);
    }

    @Transactional
    public Image saveNewImage(Image image) {
        Image newImage = Image.builder().isPreview(true).name(image.getName() + UUID.randomUUID())
                .data(image.getData()).build();
        return imageRepository.save(newImage);
    }

    @Transactional
    public void removeImage(Equipment equipment) {
        Image image = equipment.getImage();
        equipment.setImage(null);
        equipmentRepository.save(equipment);
        imageRepository.delete(image);
    }
}
