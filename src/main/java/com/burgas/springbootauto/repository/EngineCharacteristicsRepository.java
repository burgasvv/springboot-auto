package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineCharacteristicsRepository extends JpaRepository<EngineCharacteristics, Long> {

    EngineCharacteristics searchEngineCharacteristicsByEngineId(Long id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from engine_characteristics ec where ec.engine_id = ?1"
    )
    void deleteByEngineId(Long id);

    EngineCharacteristics findEngineCharacteristicsByEngineId(Long id);
}
