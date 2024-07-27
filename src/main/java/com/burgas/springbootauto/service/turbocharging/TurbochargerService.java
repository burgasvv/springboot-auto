package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.turbocharging.TurbochargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurbochargerService {

    private final TurbochargerRepository turbochargerRepository;
    private final BrandRepository brandRepository;

    public List<Turbocharger> findAll() {
        return turbochargerRepository.findAll();
    }

    public Turbocharger findById(Long id) {
        return turbochargerRepository.findById(id).orElse(null);
    }

    public Turbocharger findByName(String name) {
        return turbochargerRepository.findByName(name);
    }

    public List<Turbocharger>searchTurbochargersByNeighbourNames(String search) {
        return turbochargerRepository.searchTurbochargersByNeighbourNames(search).stream().distinct().toList();
    }

    public List<Turbocharger>searchTurbochargersByNeighbourNamesNoSpaces(String search) {
        return turbochargerRepository.searchTurbochargersByNeighbourNamesNoSpaces(search).stream().distinct().toList();
    }

    @Transactional
    public void save(Turbocharger turbocharger) {
        turbochargerRepository.save(turbocharger);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long addTurbochargerToBrand(Long brandId, Turbocharger turbocharger, TurboType turboType) {
        Turbocharger newTurbocharger = Turbocharger.builder()
                .name(turbocharger.getName()).brand(brandRepository.findById(brandId).orElse(null))
                .turboType(turboType).image(turbocharger.getImage())
                .rpm(turbocharger.getRpm()).torque(turbocharger.getTorque()).description(turbocharger.getDescription())
                .build();
        turbochargerRepository.save(newTurbocharger);
        return turbochargerRepository.findByName(newTurbocharger.getName()).getId();
    }

    @Transactional
    public void update(Turbocharger turbocharger) {
        turbochargerRepository.save(turbocharger);
    }

    @Transactional
    public void delete(Long id) {
        turbochargerRepository.deleteById(id);
    }
}
