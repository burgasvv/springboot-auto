package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.repository.engine.EngineEditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnginEditionService {

    private final EngineEditionRepository engineEditionRepository;

    public List<EngineEdition> findAll() {
        return engineEditionRepository.findAll();
    }

    public EngineEdition findById(Long id) {
        return engineEditionRepository.findById(id).orElse(null);
    }

    public List<EngineEdition> searchEngineEditionsByBrandId(Long id) {
        return engineEditionRepository.searchEngineEditionsByBrandId(id);
    }

    public EngineEdition searchEngineEditionByEngines(List<Engine> engines) {
        return engineEditionRepository.searchEngineEditionByEngines(engines);
    }

    @Transactional
    public void save(EngineEdition engineEdition) {
        engineEditionRepository.save(engineEdition);
    }

    @Transactional
    public void update(EngineEdition engineEdition) {
        engineEditionRepository.save(engineEdition);
    }

    @Transactional
    public void delete(Long id) {
        engineEditionRepository.deleteById(id);
    }
}
