package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineCharacteristicsRepository extends JpaRepository<EngineCharacteristics, Long> {

    EngineCharacteristics searchEngineCharacteristicsByEngineId(Long id);
}
