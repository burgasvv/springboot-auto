package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.repository.turbocharging.TurbochargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurbochargerService {

    private final TurbochargerRepository turbochargerRepository;

    public List<Turbocharger> findAll() {
        return turbochargerRepository.findAll();
    }

    public Turbocharger findById(Long id) {
        return turbochargerRepository.findById(id).orElse(null);
    }

    public Turbocharger findByName(String name) {
        return turbochargerRepository.findByName(name);
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
