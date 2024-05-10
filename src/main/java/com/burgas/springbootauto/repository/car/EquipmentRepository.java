package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
