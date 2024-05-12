package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByTransmissionId(Long id);

    List<Equipment> findAllByEngineId(Long id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from equipment where id = ?1"
    )
    void deleteById(@NotNull Long id);
}
