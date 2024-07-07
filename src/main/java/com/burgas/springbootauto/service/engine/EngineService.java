package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.repository.engine.EngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EngineService {

    private final EngineRepository engineRepository;

    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    public Engine findById(Long id) {
        return engineRepository.findById(id).orElse(null);
    }

    public Engine findByName(String name) {
        return engineRepository.findEngineByName(name);
    }

    public List<Engine> searchEnginesByEngineBrandEditionCar(String search) {
        return engineRepository.searchEnginesByEngineBrandEditionCar(search).stream().distinct().toList();
    }

    public Page<Engine> searchEnginesByEngineBrandEditionCarNoSpaces(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.Direction.ASC, "name");
        return engineRepository.searchEnginesByEngineBrandEditionCarNoSpaces(search, pageRequest);
    }

    @Transactional
    public void save(Engine engine) {
        engineRepository.save(engine);
    }

    @Transactional
    public void update(Engine engine) {
        engineRepository.save(engine);
    }

    @Transactional
    public void delete(Long id) {
        engineRepository.deleteById(id);
    }
}
