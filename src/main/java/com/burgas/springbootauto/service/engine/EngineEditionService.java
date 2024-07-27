package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.engine.EngineEditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EngineEditionService {

    private final EngineEditionRepository engineEditionRepository;
    private final BrandRepository brandRepository;

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

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long createEdition(EngineEdition edition) {
        EngineEdition newEdition = EngineEdition.builder().name(edition.getName())
                .image(edition.getImage()).brand(edition.getBrand()).build();
        engineEditionRepository.save(edition);
       return engineEditionRepository.findEngineEditionByName(newEdition.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long addEditionToBrand(Long brandId, EngineEdition engineEdition) {
        EngineEdition edition = EngineEdition.builder().
                name(engineEdition.getName()).image(engineEdition.getImage())
                .brand(brandRepository.findById(brandId).orElse(null)).build();
        engineEditionRepository.save(edition);
        return engineEditionRepository.findEngineEditionByName(edition.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(EngineEdition engineEdition) {
        engineEditionRepository.save(engineEdition);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        EngineEdition engineEdition = engineEditionRepository.findById(id).orElse(null);
        Objects.requireNonNull(engineEdition).getEngines()
                .forEach(engine -> engine.getEquipments()
                        .forEach(equipment -> equipment.setEngine(null)));
        engineEditionRepository.save(engineEdition);
        engineEditionRepository.deleteById(id);
    }
}
