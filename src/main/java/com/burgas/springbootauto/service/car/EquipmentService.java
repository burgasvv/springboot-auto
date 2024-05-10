package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.repository.car.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void update(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
}
