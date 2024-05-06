package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.repository.EngineEditionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnginEditionService {

    private final EngineEditionRepository engineEditionRepository;

    public EnginEditionService(EngineEditionRepository engineEditionRepository) {
        this.engineEditionRepository = engineEditionRepository;
    }

    public List<EngineEdition> findAll() {
        return engineEditionRepository.findAll();
    }

    public EngineEdition findById(Long id) {
        return engineEditionRepository.findById(id).orElse(null);
    }

    public List<EngineEdition> searchEngineEditionsByBrandId(Long id) {
        return engineEditionRepository.searchEngineEditionsByBrandId(id);
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
