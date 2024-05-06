package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.engine.EngineEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineEditionRepository extends JpaRepository<EngineEdition, Long> {

    List<EngineEdition> searchEngineEditionsByBrandId(Long id);
}
