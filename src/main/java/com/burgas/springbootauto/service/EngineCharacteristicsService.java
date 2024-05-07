package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.repository.EngineCharacteristicsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EngineCharacteristicsService {

    private final EngineCharacteristicsRepository engineCharacteristicsRepository;

    public EngineCharacteristicsService(EngineCharacteristicsRepository engineCharacteristicsRepository) {
        this.engineCharacteristicsRepository = engineCharacteristicsRepository;
    }

    public List<EngineCharacteristics> findAll() {
        return engineCharacteristicsRepository.findAll();
    }

    public EngineCharacteristics findById(Long id) {
        return engineCharacteristicsRepository.findById(id).orElse(null);
    }

    public EngineCharacteristics searchEngineCharacteristicsByEngineId(Long id) {
        return engineCharacteristicsRepository.searchEngineCharacteristicsByEngineId(id);
    }

    public EngineCharacteristics findByEngineId(Long id) {
        return engineCharacteristicsRepository.searchEngineCharacteristicsByEngineId(id);
    }

    @Transactional
    public void save(EngineCharacteristics engineCharacteristics) {
        engineCharacteristicsRepository.save(engineCharacteristics);
    }

    @Transactional
    public void update(EngineCharacteristics engineCharacteristics) {
        engineCharacteristicsRepository.save(engineCharacteristics);
    }

    @Transactional
    public void delete(Long id) {
        engineCharacteristicsRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEngineId(Long id) {
        engineCharacteristicsRepository.deleteByEngineId(id);
    }
}
