package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.repository.engine.EngineEditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EngineEditionService {

    private final EngineEditionRepository engineEditionRepository;

    public List<EngineEdition> findAll() {
        return engineEditionRepository.findAll();
    }

    public Page<EngineEdition> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page -1 , size).withSort(Sort.Direction.ASC, "name");
        return engineEditionRepository.findAll(pageRequest);
    }

    public EngineEdition findById(Long id) {
        return engineEditionRepository.findById(id).orElse(null);
    }

    public EngineEdition findByName(String name) {
        return engineEditionRepository.findEngineEditionByName(name);
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
        EngineEdition engineEdition = engineEditionRepository.findById(id).orElse(null);
        Objects.requireNonNull(engineEdition).getEngines()
                .forEach(engine -> engine.getEquipments()
                        .forEach(equipment -> equipment.setEngine(null)));
        engineEditionRepository.save(engineEdition);
        engineEditionRepository.deleteById(id);
    }
}
