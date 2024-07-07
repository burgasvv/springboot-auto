package com.burgas.springbootauto.repository.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineEditionRepository extends JpaRepository<EngineEdition, Long> {

    @NotNull
    Page<EngineEdition>findAll(@NotNull Pageable pageable);

    EngineEdition searchEngineEditionByEngines(List<Engine> engines);

    EngineEdition findEngineEditionByName(String editionName);
}
