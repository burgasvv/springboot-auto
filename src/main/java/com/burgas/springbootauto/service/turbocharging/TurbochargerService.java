package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.repository.turbocharging.TurbochargerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurbochargerService {

    private final TurbochargerRepository turbochargerRepository;

    public TurbochargerService(TurbochargerRepository turbochargerRepository) {
        this.turbochargerRepository = turbochargerRepository;
    }

    public List<Turbocharger> findAll() {
        return turbochargerRepository.findAll();
    }

    public Turbocharger findById(Long id) {
        return turbochargerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Turbocharger turbocharger) {
        turbochargerRepository.save(turbocharger);
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
