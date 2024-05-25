package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.repository.car.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    public List<Equipment> findAllByCarId(@NotNull Long carId) {
        return equipmentRepository.findAllByCarId(carId);
    }

    public List<Equipment> findAllByPersonId(@NotNull Long personId) {
        return equipmentRepository.findAllByPersonId(personId);
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
}
