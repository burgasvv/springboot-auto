package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.repository.car.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

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

    @Transactional
    public void save(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void saveAll(List<Equipment> equipments) {
        equipmentRepository.saveAll(equipments);
    }

    @Transactional
    public void update(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Transactional
    public void attachToCar(Equipment equipment, Car car) {
        equipment.setCar(car);
        equipment.setAttached(true);
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void detachFromCar(Equipment equipment, Car car) {
        equipment.setCar(null);
        equipment.setAttached(false);
        equipmentRepository.save(equipment);
    }
}
