package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.repository.transmission.GearboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GearboxService {

    private final GearboxRepository gearboxRepository;

    public List<Gearbox> findAll() {
        return gearboxRepository.findAll();
    }

    public Gearbox findById(Long id) {
        return gearboxRepository.findById(id).orElse(null);
    }

    public Gearbox findByName(String name) {
        return gearboxRepository.findGearboxByName(name);
    }

    @Transactional
    public void save(Gearbox gearbox) {
        gearboxRepository.save(gearbox);
    }

    @Transactional
    public void update(Gearbox gearbox) {
        gearboxRepository.save(gearbox);
    }

    @Transactional
    public void delete(Long id) {
        Gearbox gearbox = gearboxRepository.findById(id).orElse(null);
        Objects.requireNonNull(gearbox).getBrands().forEach(brand -> brand.getGearboxes().remove(gearbox));
        Objects.requireNonNull(gearbox).getTransmissions()
                        .forEach(transmission -> transmission.getEquipments()
                                .forEach(equipment -> equipment.setTransmission(null)));
        gearboxRepository.save(gearbox);
        gearboxRepository.deleteById(id);
    }
}
