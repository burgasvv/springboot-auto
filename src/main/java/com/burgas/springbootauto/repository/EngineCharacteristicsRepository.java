package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineCharacteristicsRepository extends JpaRepository<EngineCharacteristics, Long> {

    EngineCharacteristics searchEngineCharacteristicsByEngineId(Long id);

    void deleteByEngineId(Long id);

    EngineCharacteristics findEngineCharacteristicsByEngineId(Long id);
}
