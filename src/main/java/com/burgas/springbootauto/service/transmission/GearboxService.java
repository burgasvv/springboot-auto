package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.repository.transmission.GearboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GearboxService {

    private final GearboxRepository gearboxRepository;

    public GearboxService(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    public List<Gearbox> findAll() {
        return gearboxRepository.findAll();
    }

    public Gearbox findById(Long id) {
        return gearboxRepository.findById(id).orElse(null);
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
