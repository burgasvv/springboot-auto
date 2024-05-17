package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.repository.turbocharging.TurboTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        turboTypeRepository.deleteById(id);
    }
}
