package com.burgas.springbootauto.repository.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineEditionRepository extends JpaRepository<EngineEdition, Long> {

    List<EngineEdition> searchEngineEditionsByBrandId(Long id);

    EngineEdition searchEngineEditionByEngines(List<Engine> engines);

    EngineEdition findEngineEditionByName(String editionName);
}
