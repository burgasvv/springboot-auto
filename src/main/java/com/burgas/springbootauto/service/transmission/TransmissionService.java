package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.repository.transmission.TransmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransmissionService {

    private final TransmissionRepository transmissionRepository;

    public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }

    public Transmission findById(Long id) {
        return transmissionRepository.findById(id).orElse(null);
    }

    public Transmission findByName(String name) {
        return transmissionRepository.findTransmissionByName(name);
    }

    @Transactional
    public void save(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    @Transactional
    public void update(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    @Transactional
    public void delete(Long id) {
        transmissionRepository.deleteById(id);
    }
}
