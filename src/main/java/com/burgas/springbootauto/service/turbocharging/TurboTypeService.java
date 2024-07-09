package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.repository.turbocharging.TurboTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TurboTypeService {

    private final TurboTypeRepository turboTypeRepository;

    public List<TurboType> findAll() {
        return turboTypeRepository.findAll();
    }

    public TurboType findById(Long id) {
        return turboTypeRepository.findById(id).orElse(null);
    }

    public TurboType findByName(String name) {
        return turboTypeRepository.findByName(name);
    }

    @Transactional
    public void save(TurboType turboType) {
        turboTypeRepository.save(turboType);
    }

    @Transactional
    public void update(TurboType turboType) {
        turboTypeRepository.save(turboType);
    }

    @Transactional
    public void delete(Long id) {
        TurboType turboType = turboTypeRepository.findById(id).orElse(null);
        Objects.requireNonNull(turboType).getBrands()
                        .forEach(brand -> brand.getTurboTypes().remove(turboType));
        turboType.getTurbochargers().forEach(turbocharger -> turbocharger.getEquipments()
                .forEach(equipment -> equipment.setTurbocharger(null)));
        turboTypeRepository.deleteById(id);
    }
}
