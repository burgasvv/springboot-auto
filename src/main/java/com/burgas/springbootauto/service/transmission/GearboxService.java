package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.repository.transmission.GearboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Gearbox searchGearboxByTransmissions(List<Transmission> transmissions) {
        return gearboxRepository.searchGearboxByTransmissions(transmissions);
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
        gearboxRepository.deleteById(id);
    }
}